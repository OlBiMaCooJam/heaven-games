<template>
    <v-container>
        <div class="board-con">
            <YutnoriUser v-for='[key, val] in yutnoriUsers' :color="key" :key="key"
                         :name="val.name" :standby="val.standby" :selectedPoint="sourcePoint"
                         :turnColor="turnColor" @chooseSrcPoint="chooseSrcPoint"></YutnoriUser>
        </div>
        <div class="board-con right-margin-100">
            <Board :turnColor="turnColor" :pieces="pieces" @chooseSrcPoint="chooseSrcPoint"
                   :selectedPoint="sourcePoint"></Board>
            <YutThrow :yut="recentYut" @throwYut="throwYut" :disabled="!turn.canThrow"></YutThrow>
            <ThrownYuts :yuts="turn.thrownYuts.yuts" @chooseYut="chooseYut" :turn="turn"
                        :chooseYutIndex="chooseYutIndex"></ThrownYuts>
        </div>
        <v-btn @click="requestMove" color="primary" :disabled="turn.canThrow" large>말 움직이기</v-btn>
        <div class="space"></div>
    </v-container>
</template>

<script>
    import SockJS from "sockjs-client";
    import Stomp from "webstomp-client";
    import Board from "../components/yutnori/Board"
    import YutnoriUser from "../components/yutnori/YutnoriUser"
    import YutThrow from "../components/yutnori/YutThrow"
    import ThrownYuts from "../components/yutnori/ThrownYuts"

    export default {
        components: {Board, YutnoriUser, YutThrow, ThrownYuts},
        name: "Yutnori",
        data: function () {
            return {
                roomId: this.$route.params.id,
                stompClient: Object,
                yutnoriUsers: Map,
                recentYut: "",
                chooseYutIndex: -1,
                turn: Object,

                sourcePoint: String,

                pieces: {} //{pointName, count, color}
            }
        },
        computed: {
            turnColor() {
                return this.turn.yutnoriParticipant.color
            }
        },
        created() {
            this.initializeGame();
            const YUTNORI = this;
            YUTNORI.stompClient = Stomp.over(new SockJS('/websocket'));
            YUTNORI.stompClient.connect({}, function () {
                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId + "/yut-throw", function (response) { //윷 던지기
                    const yutResponse = JSON.parse(response.body)

                    YUTNORI.recentYut = yutResponse.yut;
                    YUTNORI.yut = YUTNORI.recentYut
                    YUTNORI.turn = yutResponse.turnResponse
                })

                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId + "/playing", function (response) { //말 움직이기
                    const moveResponse = JSON.parse(response.body)

                    YUTNORI.applyMoveResults(moveResponse.moveResultsDto)
                    if (moveResponse.finish) {
                        alert("성공!")
                    }
                    YUTNORI.chooseYutIndex = -1;
                    YUTNORI.recentYut = "";
                    YUTNORI.sourcePoint = ""
                    YUTNORI.yut = ""
                    YUTNORI.turn = moveResponse.turnResponse
                })
            })
        },

        methods: {
            initializeGame() {
                const YUTNORI = this;
                const stompClient = Stomp.over(new SockJS('/websocket'));
                stompClient.connect({}, function () {
                    stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId, function (response) {  //판 초기화
                        const yutnoriStateResponse = JSON.parse(response.body)
                        const pieces = yutnoriStateResponse.boardResponse.pieces
                        YUTNORI.turn = yutnoriStateResponse.turnResponse

                        YUTNORI.yutnoriUsers = YUTNORI.getYutnoriUsers(yutnoriStateResponse.yutnoriParticipants, pieces)
                        YUTNORI.pieces = YUTNORI.initializePieces(pieces)

                        YUTNORI.pieces['test'] = {color: 'red', count: 3}

                        stompClient.disconnect()
                    })

                    stompClient.send('/app/yutnori/' + YUTNORI.roomId)
                })
            },
            getYutnoriUsers(yutnoriParticipants, pieces) { //list to map
                let yutnoriUsers = new Map();
                for (let i = 0; i < yutnoriParticipants.length; i++) {
                    const yutnoriParticipant = yutnoriParticipants[i];
                    yutnoriUsers.set(yutnoriParticipant.color, {
                        name: yutnoriParticipant.participant.name,
                        standby: 0
                    })
                }

                pieces.forEach(piece => {
                    if (piece.pointName == 'STANDBY') {
                        yutnoriUsers.get(piece.color).standby++;
                    }
                })
                return yutnoriUsers
            },
            initializePieces(pieces) {
                return pieces.reduce(function (obj, piece) {
                    const pointName = piece.pointName

                    if (pointName == 'STANDBY') return obj;

                    if (!obj.hasOwnProperty(pointName)) {
                        obj[pointName] = {
                            count: 0,
                            color: ""
                        };
                    }
                    obj[pointName].count++;
                    obj[pointName].color = piece.color;

                    return obj;
                }, {});
            },
            throwYut() {
                this.stompClient.send("/app/yutnori/" + this.roomId + "/yut-throw");
            },
            chooseSrcPoint(pointName, canClick) {
                if (canClick) {
                    this.sourcePoint = pointName;
                }
            },
            chooseYut(yut, chooseYutIndex, canChoose) {

                if (canChoose) {
                    this.chooseYutIndex = chooseYutIndex;
                    this.yut = yut;
                }
            },
            requestMove() {
                if (this.yut == "") alert("윷 선택 하세요")
                else if (this.sourcePoint == "") alert("말 선택 하세요")
                else {
                    const moveRequest = {
                        sourcePoint: this.sourcePoint,
                        yut: this.yut
                    }

                    this.stompClient.send("/app/yutnori/" + this.roomId + "/move-piece", JSON.stringify(moveRequest));
                }
            },
            applyMoveResults(moveResultsDto) {
                const YUTNORI = this;
                const copyPieces = JSON.parse(JSON.stringify(YUTNORI.pieces))

                moveResultsDto.moveResults.forEach((moveResult) => {
                    const color = moveResult.color;
                    const route = moveResult.route;
                    const srcPoint = route[0];
                    const destPoint = route[route.length - 1];

                    if (srcPoint == 'STANDBY') {
                        YUTNORI.yutnoriUsers.get(color).standby--;
                    } else {
                        copyPieces[srcPoint].count--;
                    }
                    if (destPoint == 'STANDBY') {
                        YUTNORI.yutnoriUsers.get(color).standby++;
                    } else {
                        if (!copyPieces.hasOwnProperty(destPoint)) {
                            copyPieces[destPoint] = {
                                count: 0, color: ""
                            };
                        }
                        copyPieces[destPoint].color = color;
                        copyPieces[destPoint].count++;
                    }
                })
                YUTNORI.pieces = copyPieces;
            }
        }
    }
</script>

<style scoped>
    .board-con {
        display: flex;
        align-items: center;
        justify-content: space-evenly;
        margin-bottom: 30px;
    }

    .right-margin-100 {
        margin-right: 100px;
    }

    .space {
        height: 100px;
    }
</style>