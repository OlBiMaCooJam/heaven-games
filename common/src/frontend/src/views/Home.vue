<template>
    <v-container class="home" fluid>
        <v-row>
            <v-col cols="12">
                <v-row align='center' justify='center'>
                    <GameThumbnail class='thumb' v-for='game in games' :key="game.title" :game="game"/>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import GameThumbnail from "../components/GameThumbnail";

    export default {
        name: 'home',
        components: {
            GameThumbnail
        },
        data() {
            return {
                games: [{title: '미니게임천국', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', link: '#'},
                    {title: '윷놀이', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', link: '#'},
                    {title: '지뢰찾기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', link: '#'},
                    {title: '노래맞추기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', link: '#'},
                    {title: '맞고', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', link: '#'},
                    {title: '사다리타기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', link: '#'}] //데이터 예시
            }
        }
    }
</script>

<style scoped>
    .thumb {
        margin-bottom: 50px;
        margin-right: 50px;
    }
</style>