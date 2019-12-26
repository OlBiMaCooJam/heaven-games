<template>
    <v-container class="mafia-app" fluid>
        <v-row>
            <v-col cols="5">
                <v-row align='center' justify='center'>
                    <MafiaCitizen id="list" class="white" :name="name" :key="name.id" v-for="name in names"/>
                </v-row>
            </v-col>
            <v-col cols="5">
                <div id="message-area"></div>
                <div id="message-footer">
                    <input type="text" id="message-content">
                    <input type="submit" id="send-message" value="전송">
                </div>
            </v-col>
            <v-col cols="1">
                <v-row align='center' justify='center'>
                    <Timer v-if="renderComponent" :date="date" :roomId=roomId :client="client" :day="day" :occupation="occupation" :dialog="dialog"></Timer>
                </v-row>
                <v-row>
                    <Vote :selected="selected" :dialog="dialog" :citizens="citizens" :vote_msg="vote_message" :client="client" :roomId=roomId :day="day" :occupation="occupation"></Vote>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>


<script>
    import SockJS from "sockjs-client";
    import Stomp from "webstomp-client";
    import Timer from "../components/Timer";
    import Vote from "../components/Vote";
    import {eventBus} from "../main";
    import MafiaCitizen from "../components/MafiaCitizen";

    export default {
        name: 'Mafia',
        components: {
            MafiaCitizen,
            Vote,
            Timer
        },
        methods: {
            refresh() {
                this.renderComponent = false;
                this.$nextTick(() => {
                    this.renderComponent = true;
                });
            },
            alerts(message) {
                return `<div id="message" style="text-align: center; color: red">
                            <span>${message}</span>
                    </div>`;
            },
            messageTemplate(mafiaChatMessage) {
                if(mafiaChatMessage.userId === this.userId){
                    return `<div id="message" style="text-align: right">
                            <span>나: ${mafiaChatMessage.message}</span>
                    </div>`;
                }
                return `<div id="message" style="text-align: left";>
                            <span>${mafiaChatMessage.name}: ${mafiaChatMessage.message}</span>
                    </div>`
            },
          sendMessage: function() {
              const game = this;
              const content = document.getElementById("message-content").value;
              if(this.day){
                  game.client.send('/app/rooms/' + this.roomId + '/mafia/chat', content);
              }else{
                  game.client.send('/app/rooms/' + this.roomId + '/mafia/chat/mafiaOnly', content);
              }
              if (content.length != 0) {
                  document.getElementById("message-content").value = "";
              }
          },
          addMessage: function(mafiaChatMessage) {
                const messageArea = document.getElementById("message-area");
                messageArea.insertAdjacentHTML("beforeend", this.messageTemplate(mafiaChatMessage));
                messageArea.scrollTop = messageArea.scrollHeight;
              },
            alertMessage: function(message) {
                const messageArea = document.getElementById("message-area");
                    messageArea.insertAdjacentHTML("beforeend", this.alerts(message));
                    messageArea.scrollTop = messageArea.scrollHeight;
            },
            newArray(i, name){
                let newArray = [...this.citizens];
                newArray.splice(i, 1, name);
                return newArray;
            },
        },
        data() {
            return {
                dialog: false,
                date: 120, // 테스트 데이터
                vote_message: "선택해주세요",
                roomId: this.$route.params.id,
                client: {},
                userId: '',
                selected: '',
                day: true,
                occupation: '',
                flag: '',
                citizens: [],
                names: [],
                renderComponent: true
            }
        },
        created() {
            this.client = Stomp.over(new SockJS('/websocket'));
            const game = this;
            game.client.connect({}, function() {
                window.console.log(game.roomId);
                game.client.send('/app/rooms/' + game.roomId + '/mafia' );
                game.client.subscribe('/user/queue/rooms/' + game.roomId + '/mafia/occupation', function (response) {
                    const mafiaOccupationMessage = JSON.parse(response.body);
                    game.userId = mafiaOccupationMessage.userId;
                    game.occupation = mafiaOccupationMessage.occupation;

                    game.date = 120;
                    game.refresh();
                    game.alertMessage('당신은 ' + game.occupation + ' 입니다.');
                    game.alertMessage('마피아를 찾아보세요.\n' + game.date + '초 후에 투표합니다.');

                    if(game.occupation === 'MAFIA') {
                        game.client.subscribe('/topic/rooms/' + game.roomId + '/mafia/chat/mafiaOnly', function (response) {
                            const mafiaChatMessage = JSON.parse(response.body);
                            game.addMessage(mafiaChatMessage);
                        });
                    }
                    if(game.occupation === 'POLICE' || game.occupation === 'DETECTIVE') {
                        game.client.subscribe('/user/queue/rooms/' + game.roomId + '/' + game.occupation, function (response) {
                            game.alertMessage(response.body);
                        });
                    }
                });
                game.client.subscribe('/topic/rooms/' + game.roomId + '/mafia/chat', function (response) {
                    const mafiaChatMessage = JSON.parse(response.body);
                    game.addMessage(mafiaChatMessage);
                });
                game.client.subscribe('/user/queue/rooms/' + game.roomId + '/vote', function(response){
                    const responseCitizens = JSON.parse(response.body);
                    for(let i=0;i<responseCitizens.length;i++){
                        game.citizens = game.newArray(i, responseCitizens[i].name);
                        game.dialog = true;
                    }
                });
                game.client.subscribe('/topic/rooms/' + game.roomId + '/dayResult', function (response) {
                    eventBus.$emit('initTime');
                    const resultMessage = response.body;
                    game.alertMessage(resultMessage);

                    if(resultMessage.includes("종료")) {
                        alert(resultMessage);
                        game.$router.push("/");
                        return;
                    }
                    game.day = false;

                    game.date = 30;
                    game.refresh();
                    game.alertMessage(game.date + '초 후에 각 직업의 능력이 발동합니다.\n마피아만 채팅을 할 수 있습니다.');//
                });
                game.client.subscribe('/topic/rooms/' + game.roomId + '/nightResult', function (response) {
                    eventBus.$emit('initTime');
                    const resultMessage = response.body;
                    game.alertMessage(resultMessage);

                    game.client.send('/app/rooms/' + game.roomId + '/list' );

                    if(resultMessage.includes("종료")) {
                        alert(resultMessage);
                        game.$router.push("/");
                        return;
                    }
                    game.day = true;

                    game.date = 120;
                    game.refresh();
                    game.alertMessage('마피아를 찾아보세요.\n' + game.date  +'초 후에 투표합니다.');
                });
                game.client.subscribe('/topic/rooms/' + game.roomId + '/list', function (response) {
                    const mafiaParticipantInfos = JSON.parse(response.body);
                    const arr = [];
                    for (let i = 0; i < mafiaParticipantInfos.length; i++) {
                        arr.push(mafiaParticipantInfos[i].name);
                    }
                    game.names = arr;
                });
                game.client.send('/app/rooms/' + game.roomId + '/list' );
            });
        },

        mounted() {
            const game = this;
            document.querySelector('#send-message').addEventListener("click", game.sendMessage);
            document.querySelector('#message-content').addEventListener('keyup',function(e){
                if (e.keyCode === 13) {
                    game.sendMessage();
                }
            });

            eventBus.$on(`closeModal`, () => this.dialog = false);
        },
        destroyed() {
            this.client.disconnect();
            eventBus.$off(`Timeout`);
            eventBus.$emit('closeModal');

            //TODO: 마피아 참가자 나가는 경우
        }
    }
</script>

<style scoped>
    #message {
        display: inline-block;
    }

    #message-area {
        font-family: monospace;
        border: groove white;
        overflow: scroll;
        height: 75%;
        width: 550px;
        background-color: black;
        opacity: 0.7;
        margin-top: 5%;
        color:white;
        max-width: 150%;
        border-radius: 15px;
    }

    #message-footer {
        font-family: monospace;
        height: 10%;
        border: groove white;
        width: 550px;
        padding: 0;
        border-radius: 15px;
    }

    #message-content {
        width: 90%;
        height: 100%;
        background-color: #7f7f7f;
        padding: 0;
        border: 0;
        border-radius: 15px;
        color: white;
        display: inline-block;

    }

    #send-message {
        width: 10%;
        height: 100%;
        background-color: #333333;
        padding: 0;
        border: 0;
        color: white;
        border-radius: 15px;
    }

    .mafia-app {
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        height: 100%;
        background: url('../assets/mafia-bg.jpg') no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
</style>