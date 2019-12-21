<template>
    <v-container id="mafia-app" fluid>
        <v-row>
            <v-col cols="6">
                <v-row align='center' justify='center'>
                    <MafiaCitizen :citizen="citizen" :key="citizen.name" v-for='citizen in citizens'/>
                </v-row>
            </v-col>
            <div>
                <div id="message-area">
                </div>
                <div id="message-footer">
                    <input type="text" id="message-content">
                    <input type="submit" id="send-message" value="전송">
                </div>
            </div>
        </v-row>
    </v-container>
</template>


<script>
    import MafiaCitizen from "../components/MafiaCitizen";
    import SockJS from "sockjs-client";
    import Stomp from "webstomp-client";

    export default {
        name: 'Mafia',
        components: {
            MafiaCitizen,
        },
        methods: {
            messageTemplate(mafiaChatMessage) {
                if(mafiaChatMessage.userId === this.userId){
                    return `<div id="message" style="text-align: right">
                            <span">나: ${mafiaChatMessage.message}</span>
                    </div>`;
                }
                return `<div id="message" style="text-align: left";>
                            <span>${mafiaChatMessage.name}: ${mafiaChatMessage.message}</span>
                    </div>`
            },
          sendMessage: function() {
              const game = this;
              const content = document.getElementById("message-content").value;
              game.client.send('/app/rooms/' + this.roomId + '/mafia/chat', content);
              if (content.length != 0) {
                  document.getElementById("message-content").value = "";
              }
          },
          addMessage: function(mafiaChatMessage) {
                const messageArea = document.getElementById("message-area");
                messageArea.insertAdjacentHTML("beforeend", this.messageTemplate(mafiaChatMessage));
                messageArea.scrollTop = messageArea.scrollHeight;
              }
        },
        data() {
            return {
                citizens: [{name: '비모', occupation: '마피아', src: require('../assets/p1.png')},
                    {name: '쿠기', occupation: '마피아', src: require('../assets/p2.png')},
                    {name: '제이엠', occupation: '마피아', src: require('../assets/p3.png')},
                    {name: '올라프', occupation: '마피아', src: require('../assets/p4.png')},
                    {name: '코맥', occupation: '마피아', src: require('../assets/p5.png')},
                    {name: '맥코', occupation: '마피아', src: require('../assets/p6.png')},
                    {name: '코맥코', occupation: '마피아', src: require('../assets/p7.png')},
                    {name: '맥코맥', occupation: '마피아', src: require('../assets/p8.png')},
                    {name: '쿠기쿠', occupation: '마피아', src: require('../assets/p9.png')},
                    {name: '반', occupation: '마피아', src: require('../assets/p10.png')}, // 테스트 데이터
                ],
                roomId: this.$route.params.id,
                client: {},
                userId: ''
            }
        },
        created() {
            this.client = Stomp.over(new SockJS('/websocket'));
            const game = this;
            game.client.connect({}, function() {
                game.client.send('/app/rooms/' + game.roomId + '/mafia' );
                game.client.subscribe('/user/queue/rooms/' + game.roomId + '/mafia/occupation', function (response) {
                    const mafiaOccupationMessage = JSON.parse(response.body);
                    game.userId = mafiaOccupationMessage.userId;
                    alert(mafiaOccupationMessage.occupation);
                });
                game.client.subscribe('/topic/rooms/' + game.roomId + '/mafia/chat', function (response) {
                    const mafiaChatMessage = JSON.parse(response.body);

                    window.console.log(mafiaChatMessage);
                    game.addMessage(mafiaChatMessage);
                });
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
        },
        destroyed() {
            this.client.disconnect();
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
        height: 85%;
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
</style>