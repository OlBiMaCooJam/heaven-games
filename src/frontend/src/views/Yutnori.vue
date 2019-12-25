<template>
    <v-container>
        <div class="board-con">
            <YutnoriUser :color="key" :key="key"
                         :name="val.name" :standby="val.standby" @chooseSrcPoint="chooseSrcPoint"
                         v-for='[key, val] in yutnoriUsers'></YutnoriUser>
        </div>
        <div class="board-con right-margin-100">
            <Board :movingResults="movingResults" @chooseSrcPoint="chooseSrcPoint"></Board>
            <YutThrow :yut="yut" @throwYut="throwYut"></YutThrow>
            <ThrownYuts :yuts="yuts" @chooseYut="chooseYut"></ThrownYuts>
        </div>
        <v-btn @click="requestMove" color="primary" large>말 움직이기</v-btn>
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
                yut: "",
                yuts: [],
                srcPoint: "",
                moveYutCon: "",

                movingResults: []
            }
        },

        created() {
            this.initializeGame();
            const YUTNORI = this;
            YUTNORI.stompClient = Stomp.over(new SockJS('/websocket'));
            YUTNORI.stompClient.connect({}, function () {
                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId + "/yut-throw", function (response) { //윷 던지기
                    const yutResponse = JSON.parse(response.body)

                    YUTNORI.yut = yutResponse.yut;
                    YUTNORI.yuts.push(YUTNORI.yut);
                })

                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId + "/playing", function (response) { //말 움직이기
                    const moveResultDtos = JSON.parse(response.body)
                    YUTNORI.applyMoveResults(moveResultDtos.moverResultDtos)

                    YUTNORI.moveYutCon.parentNode.removeChild(YUTNORI.moveYutCon)
                    YUTNORI.moveYutCon = ""
                    YUTNORI.srcPoint = ""
                })
            })
        },

        methods: {
            initializeGame() {
                const YUTNORI = this;
                const stompClient = Stomp.over(new SockJS('/websocket'));
                stompClient.connect({}, function () {
                    stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId, function (response) {  //판 초기화
                        const gameStartResponseDtos = JSON.parse(response.body)
                        YUTNORI.loadYutnoriUsers(gameStartResponseDtos.gameStartResponseDtos)
                        stompClient.disconnect()
                    })

                    stompClient.send('/app/yutnori/' + YUTNORI.roomId)
                })
            },
            loadYutnoriUsers(gameStartResponseDtos) {
                this.yutnoriUsers = new Map();
                for (let i = 0; i < gameStartResponseDtos.length; i++) {
                    const gameStartResponseDto = gameStartResponseDtos[i];
                    this.yutnoriUsers.set(gameStartResponseDto.color, {
                        name: gameStartResponseDto.userName,
                        standby: this.pointCount(gameStartResponseDto.pieceLocations, 'STANDBY')
                    })
                }
            },
            pointCount(points, position) {
                let cnt = 0;

                points.forEach((point) => {
                    if (point == position) cnt++;
                });

                return cnt;
            },
            throwYut() {
                this.stompClient.send("/app/yutnori/" + this.roomId + "/yut-throw");
            },
            chooseSrcPoint(pointName) {
                this.srcPoint = pointName;
            },
            chooseYut(yutCon) {
                this.moveYutCon = yutCon;
            },
            requestMove() {
                if (this.moveYutCon == "") alert("윷 선택 하세요")
                else if (this.srcPoint == "") alert("말 선택 하세요")
                else {
                    const moveRequest = {
                        pointName: this.srcPoint,
                        yut: this.moveYutCon.innerText
                    };
                    this.stompClient.send("/app/yutnori/" + this.roomId + "/move-piece", JSON.stringify(moveRequest));

                    alert(this.moveYutCon.innerText + " ,  " + this.srcPoint)
                }
            },
            applyMoveResults(moveResultDtos) {
                let movingResults = [];
                const YUTNORI = this;
                moveResultDtos.forEach((moveResultDto) => {
                    const color = moveResultDto.color;
                    const route = moveResultDto.route;
                    const srcPoint = route[0];
                    const destPoint = route[route.length - 1];

                    movingResults.push({
                        color: color, srcPoint: srcPoint, destPoint: destPoint
                    })

                    if (srcPoint == 'STANDBY') YUTNORI.yutnoriUsers.get(color).standby--;
                    if (destPoint == 'STANDBY') YUTNORI.yutnoriUsers.get(color).standby++;
                })
                this.movingResults = movingResults;
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