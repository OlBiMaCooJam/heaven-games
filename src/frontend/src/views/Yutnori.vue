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
        <v-btn @click="testApplyRoute" color="primary" large>routes test</v-btn>
        <v-btn @click="requestMove" color="primary" large>request move</v-btn>
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
            const YUTNORI = this;
            window.console.log("roomId = " + YUTNORI.roomId)
            YUTNORI.stompClient = Stomp.over(new SockJS('/websocket'));
            YUTNORI.stompClient.connect({}, function () {

                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId, function (response) {  //판 초기화
                    const gameStartResponseDtos = JSON.parse(response.body)
                    YUTNORI.yutnoriUsers = YUTNORI.getYutnoriUsers(gameStartResponseDtos)
                })

                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId + "/yut-throw", function (response) { //윷 던지기
                    const yutResponse = JSON.parse(response.body)

                    YUTNORI.yut = yutResponse.yut;
                    YUTNORI.yuts.push(YUTNORI.yut);
                })

                YUTNORI.stompClient.subscribe("/topic/yutnori/" + YUTNORI.roomId + "/playing", function (response) { //말 움직이기
                    const moveResultDtos = JSON.parse(response.body)

                    YUTNORI.moveYutCon.parentNode.removeChild(this.moveYutCon)

                    YUTNORI.moveYutCon = ""
                    YUTNORI.srcPoint = ""

                    YUTNORI.applyMoveResults(moveResultDtos)

                })

                YUTNORI.stompClient.send('/topic/yutnori/' + YUTNORI.roomId)
            })

            window.console.log("yutnori created()")
        },

        methods: {
            getYutnoriUsers(gameStartResponseDtos) {
                let yutnoriUsers = new Map();
                for (let i = 0; i < gameStartResponseDtos.length; i++) {
                    const gameStartResponseDto = gameStartResponseDtos[i];
                    yutnoriUsers.set(gameStartResponseDto.color, {
                        name: gameStartResponseDto.userName,
                        standby: this.pointCount(gameStartResponseDto.pieceLocations, 'STANDBY')
                    })
                }
                return yutnoriUsers
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
                window.console.log("source point = " + this.srcPoint)
            },
            chooseYut(yutCon) {
                this.moveYutCon = yutCon;
                window.console.log("move yut = " + this.moveYutCon.innerText);
            },
            requestMove() {
                if (this.moveYutCon == "") window.console.log("윷 선택 필요")
                else if (this.srcPoint == "") window.console.log("말 선택 필요")
                else {
                    const moveRequest = {
                        yut: this.moveYutCon.innerText,
                        pointName: this.srcPoint
                    };
                    this.stompClient.send("/app/yutnori/" + this.roomId + "/playing", moveRequest);

                    window.console.log("move yut : " + this.moveYutCon.innerText);
                    window.console.log("src point : " + this.srcPoint);

                    this.moveYutCon.parentNode.removeChild(this.moveYutCon)

                    this.moveYutCon = ""
                    this.srcPoint = ""
                }

            },
            testApplyRoute() {
                const movingResults = [
                    [
                        {
                            color: 'red',
                            route: ['STANDBY', 'DO', 'GAE']
                        },
                        {
                            color: 'black',
                            route: ['STANDBY', 'DO']
                        }
                    ],

                    [
                        {
                            color: 'red',
                            route: ['STANDBY', 'DO', 'GAE']
                        },
                        {
                            color: 'black',
                            route: ['DO', 'GAE', 'GUL']
                        }
                    ],
                    [
                        {
                            color: 'red',
                            route: ['GAE', 'STANDBY']
                        },
                        {
                            color: 'black',
                            route: ['GUL', 'STANDBY']
                        }
                    ]
                ];

                this.movingResultsIndex++;
                if (this.movingResultsIndex == movingResults.length) this.movingResultsIndex = 0;
                this.applyMoveResults(movingResults[this.movingResultsIndex]);
            },
            applyMoveResults(moveResultDtos) {
                window.console.log("yutnori.applyMoveResults")
                window.console.log(moveResultDtos)

                let movingResultTest = [];
                const YUTNORI = this;
                moveResultDtos.forEach(function (moveResultDto) {
                    const color = moveResultDto.color;
                    const route = moveResultDto.route;
                    const srcPoint = route[0];
                    const destPoint = route[route.length - 1];

                    movingResultTest.push({
                        color: color, srcPoint: srcPoint, destPoint: destPoint
                    })

                    if (srcPoint == 'STANDBY') YUTNORI.yutnoriUsers.get(color).standby--;
                    if (destPoint == 'STANDBY') YUTNORI.yutnoriUsers.get(color).standby++;
                })
                this.movingResults = movingResultTest;
                window.console.log('movingResultTest')
                window.console.log(this.movingResults)
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