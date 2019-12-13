<template>
    <v-container class="pa-0">
        <router-link :to="'/rooms/' + room.id + '/yutnori'">
<!--        <router-link :to="'/rooms/' + room.id">-->
            <v-list-item class="room">
                <v-list-item-avatar class="game-logo">
                    <v-img :src="gameLogo"/>
                </v-list-item-avatar>

                <v-list-item-avatar class="room-number">
                    <v-icon v-text="room.players.length+'/10'"></v-icon>
                </v-list-item-avatar>

                <v-list-item-content class="text-align-center">
                    <v-list-item-title v-text="room.id + '번 방'"></v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </router-link>
    </v-container>
</template>

<script>
    export default {
        name: 'RoomPreview',
        props: {
            room: {
                id: Number,
                title: String,
                players: Array
            },
            gameLogo: String
        }
    };
</script>

<style scoped>
    .room-number {
        margin-left: 5%
    }

    .text-align-center {
        text-align: center;
        left: -100px
    }

    .game-logo {
        margin-left: 3%;
    }

</style>