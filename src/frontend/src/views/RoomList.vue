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
                    <v-btn v-on:click="refresh" icon>
                        <v-icon>mdi-refresh</v-icon>
                    </v-btn>
                </v-row>
                <v-row align="center" justify="center">
                    <v-toolbar-title>게임 {{$route.params.id}}</v-toolbar-title>
                </v-row>
                <v-row align="center" justify="end" class="ma-5">
                    <v-btn v-on:click="createRoom" icon>
                        방 만들기
                    </v-btn>
                </v-row>

            </v-toolbar>
            <v-list>
                <RoomPreview class="bottom-line" v-for='room in rooms' :key="room.id" :room="room"
                             :game-logo="gameLogo"/>
            </v-list>
        </v-card>
    </v-app>
</template>

<script>
    import RoomPreview from "../components/RoomPreview";
    import axios from "axios";

    export default {
        components: {RoomPreview},
        data() {
            return {
                rooms: [],
                gameLogo: 'https://cdn.vuetifyjs.com/images/lists/3.jpg'
            }
        },

        created() {
            axios.get('/rooms')
                .then(response => {
                    this.rooms = response.data;
                })
        },

        methods: {
            createRoom() {
                axios.post('/rooms')
                    .then(response => {
                        this.rooms.push(response.data)
                    })
            },
            refresh() {

            }
        }
    }

</script>

<style>
    .bottom-line:not(:last-child) {
        border-bottom: 1px solid lightgray;
    }
</style>