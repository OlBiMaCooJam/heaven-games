<template>
    <v-container id="mafia-app" fluid>
        <v-row>
            <v-col cols="6">
                <v-row align='center' justify='center'>
                    <MafiaCitizen :citizen="citizen" :key="citizen.name" v-for='citizen in citizens'/>
                </v-row>
            </v-col>
            <v-col cols="3">
                <v-row align='center' justify='center'>
                    <Timer :date=date :roomId=id></Timer>
                </v-row>
            </v-col>
            <v-col cols="3">
                <Vote :dialog=false :citizens="citizens" :vote_msg="vote_message"></Vote>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import MafiaCitizen from "../components/MafiaCitizen";
    import Timer from "../components/Timer";
    import Vote from "../components/Vote";

    export default {
        name: 'Mafia',
        components: {
            Vote,
            Timer,
            MafiaCitizen
        },
        data() {
            return {
                citizens: [{name: '비모', occupation: '마피아', src: require('../assets/p1.png')},
                    {name: '쿠기', occupation: '마피아', src: require('../assets/p2.png')},
                    {name: '제이엠', occupation: '마피아', src: require('../assets/p3.png')},
                    {name: '올라프', occupation: '마피아', src: require('../assets/p4.png')},
                    {name: '코맥', occupation: '마피아', src: require('../assets/p5.png')},
                    {name: '맥코', occupation: '마피아', src: require('../assets/p6.png')},
                    {name: '코맥코', occupation: '마피아', src: require('../assets/p7.png')},
                    {name: '맥코맥', occupation: '마피아', src: require('../assets/p8.png')},
                    {name: '쿠기쿠', occupation: '마피아', src: require('../assets/p9.png')},
                    {name: '모비', occupation: '마피아', src: require('../assets/p10.png')}, // 테스트 데이터
                ],
                date: 234, // 테스트 데이터
                vote_message: "누가 마피아일까?",
            }
        },
        id: 1,
    }
</script>

<style scoped>
</style>