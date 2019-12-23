<template>
    <v-app>
        <v-card
                width="800"
                style="margin: 0 auto"
        >
            <v-toolbar
                    color="#4682B4"
                    dark
            >
                <v-row align="center" justify="start" class="ma-3">
                    <v-btn icon>
                        <v-icon>mdi-refresh</v-icon>
                    </v-btn>
                </v-row>
                <v-row align="center" justify="center">
                    <v-toolbar-title>게임 {{$route.params.gameId}}</v-toolbar-title>
                </v-row>
                <v-row align="center" justify="end" class="ma-5">
                    <v-btn icon>
                        방 만들기
                    </v-btn>
                </v-row>

            </v-toolbar>
            <v-list>
                <RoomPreview class="bottom-line" v-for='room in rooms' :key="room.id" :game-id="gameId" :room="room"
                             :game-logo="gameLogo"/>
            </v-list>
        </v-card>
    </v-app>
</template>

<script>
    import RoomPreview from "../components/RoomPreview";

    export default {
        components: {RoomPreview},
        data() {
            return {
                rooms: [
                    {id: '1', playerCnt: 2, title: '일번'},
                    {id: '2', playerCnt: 3, title: '이번'},
                    {id: '3', playerCnt: 1, title: '삼번'},
                    {id: '4', playerCnt: 4, title: '사번'},
                ],
                // gameLogo: '../assets/yutgame.png'
                gameLogo: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
                gameId: 1,
            }
        },
    }
</script>

<style>
    .bottom-line:not(:last-child) {
        border-bottom: 1px solid lightgray;
    }
</style>