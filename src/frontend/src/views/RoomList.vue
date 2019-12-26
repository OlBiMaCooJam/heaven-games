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
                    <v-btn icon @click="refresh">
                        <v-icon>mdi-refresh</v-icon>
                    </v-btn>
                </v-row>
                <v-row align="center" justify="center">
                    <v-toolbar-title>게임 {{$route.params.id}}</v-toolbar-title>
                </v-row>
                <v-row align="center" justify="end" class="ma-5">
                    <v-btn icon @click="createRoom">
                        방 만들기
                    </v-btn>
                </v-row>

            </v-toolbar>
            <v-list>
                <RoomPreview class="bottom-line" v-for='room in rooms' :key="room.id" :game-title="gameTitle"
                             :room="room"
                             :game-logo="gameLogo"/>
            </v-list>
        </v-card>
    </v-app>
</template>

<script>
    import RoomPreview from "../components/RoomPreview";

    export default {
        components: {RoomPreview},
        props: {
            gameTitle: String,
        },
        data() {
            return {
                rooms: [],
                gameLogo: require('../assets/Logo.jpg'),
            }
        },
        created() {
            this.$axios.get('/rooms')
                .then(response => {
                    this.rooms = response.data;
                })
        },
        methods: {
            createRoom: function () {
                this.$axios.post('/rooms')
                    .then((response) => {
                        return response.headers;
                    })
                    .then((headers) => {
                        const url = headers.location;
                        this.$router.push(url);
                    });
            },
            refresh: function () {
                this.$axios.get('/rooms')
                    .then(response => {
                        this.rooms = response.data;
                    })
            }
        },
    }
</script>

<style>
    .bottom-line:not(:last-child) {
        border-bottom: 1px solid lightgray;
    }
</style>