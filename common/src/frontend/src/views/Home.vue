<template>
    <v-container fluid class="home" style="font-family: rose">
        <v-row>
            <v-col cols="12">
                <v-row align='center' justify='center'>
                    <GameThumbnail class='thumb' v-for='title in titles' :key="title.text" :title='title'/>
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
                titles: ['마피아게임', '윷놀이', '지뢰찾기', '숫자야구', '사다리타기', '맞고'] //데이터 예시
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