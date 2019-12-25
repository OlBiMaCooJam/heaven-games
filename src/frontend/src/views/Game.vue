<template>
    <v-container>
        <v-container>
            <span>Game Id : {{id}}</span>
        </v-container>
        <v-container>
            <span>게임 타입 : {{gameKind}}</span>
        </v-container>

        <v-btn @click="start">
            게임 시작
        </v-btn>
    </v-container>
</template>

<script>
    import SockJS from "sockjs-client";
    import Stomp from "webstomp-client";
    import router from "../router";

    export default {
        name: "Game",
        props: {
            id: Number,
            gameKind: String
        },
        methods: {
            start() {
                this.client.send('/app/rooms/' + this.id + '/start');
            }
        },
        created() {
            alert("id, gamekind = " + this.id + ",  " + this.gameKind)

            const game = this;
            game.client = Stomp.over(new SockJS('/websocket'));
            game.client.connect({}, function () {
                let join = true;
                game.client.send('/app/rooms/' + game.id);
                game.client.subscribe('/topic/rooms/' + game.id, function (response) {
                    const roomResponse = JSON.parse(response.body)
                    const players = roomResponse.players;
                    if (join) {
                        game.userId = players[players.length - 1].id;
                        join = false;
                    }
                });
                game.client.subscribe('/topic/rooms/' + game.id + '/start', function (response) {
                    const gameStartResponseDto = JSON.parse(response.body);

                    if (gameStartResponseDto.isGameStart) {
                        router.push('/' + gameStartResponseDto.gameKind + '/' + game.id);
                    }
                    else {
                        alert("게임 시작 불가")
                    }
                });
            });
        },
    }
</script>

<style scoped>

</style>