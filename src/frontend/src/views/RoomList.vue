<template>
    <v-app>
        <v-card width="800" style="margin: 0 auto">
            <v-toolbar color="#4682B4" dark>
                <v-row align="center" justify="start" class="ma-3">
                    <v-btn v-on:click="refresh" icon>
                        <v-icon>mdi-refresh</v-icon>
                    </v-btn>
                </v-row>
                <v-row align="center" justify="center">
                    <v-toolbar-title>{{gameKind}}</v-toolbar-title>
                </v-row>
                <v-row align="center" justify="end" class="ma-5">
                    <v-btn @click="createRoom" icon>
                        방 만들기
                    </v-btn>
                </v-row>

            </v-toolbar>
            <v-list>
                <RoomPreview v-for='room in rooms' :key="room.id" class="bottom-line"
                             :room="room" :game-logo="gameLogo" :gameKind="gameKind"/>
            </v-list>
        </v-card>
    </v-app>
</template>

<script>
    import RoomPreview from "../components/RoomPreview";
    import axios from "axios";
    import 'url-search-params-polyfill';

    export default {
        components: {RoomPreview},
        props: {
            gameKind: String
        },
        data() {
            return {
                rooms: [],
                gameLogo: require('../assets/Logo.jpg')
            }
        },

        created() {
            this.refresh();
        },

        methods: {
            createRoom() {
                let params = new URLSearchParams();
                params.append('gameKind', this.gameKind)

                axios.post('/rooms', params)
                    .then(response => {
                        window.console.log("new room : " + response.data)
                    })
            },
            refresh() {
                axios.get('/rooms', {
                    params: {
                        gameKind: this.gameKind
                    }
                })
                    .then(response => {
                        this.rooms = response.data;
                    })
            }
        }
    }

</script>

<style>
    .bottom-line:not(:last-child) {
        border-bottom: 1px solid lightgray;
    }
</style>