<template>
    <v-container id="board">
        <v-container id="inner-board">
        <Row class="absolute-position" v-for='row in rows'
                  :key="row.names" :row="row"/>
        </v-container>
    </v-container>
</template>

<script>
    import Row from "./Row"

    export default {
        name: "Board",
        components: {Row},
        data: function () {
            const weight = 12;
            const x = 6, y = 5;
            const weightedX = x * weight, weightedY = y * weight;

            return {
                rows: [
                    {
                        names: ["DUITMO", "DUITYUT", "DUITGUL", "DUITGAE", "DUITDO", "MO"],
                        left: 0,
                        top: 0,
                        interval: weightedX
                    },
                    {
                        names: ["DUITMODO", "MODO"],
                        left: weightedY,
                        top: weightedY,
                        interval: weightedY * 4
                    },
                    {
                        names: ["ZZIDO", "YUT"],
                        left: 0,
                        top: weightedX,
                        interval: weightedX * 5
                    },
                    {
                        names: ["DUITMOGAE", "MOGAE"],
                        left: weightedY * 2,
                        top: weightedY * 2,
                        interval: weightedY * 2
                    },
                    {
                        names: ["ZZIGAE", "GUL"],
                        left: 0,
                        top: weightedX * 2,
                        interval: weightedX * 5
                    },
                    {
                        names: ["BANG"],
                        left: weightedY * 3,
                        top: weightedY * 3,
                        interval: 0
                    },
                    {
                        names: ["ZZIGUL", "GAE"],
                        left: 0,
                        top: weightedX * 3,
                        interval: weightedX * 5
                    },
                    {
                        names: ["SOKYUT", "BANGSUGI"],
                        left: weightedY * 2,
                        top: weightedY * 4,
                        interval: weightedY * 2
                    },
                    {
                        names: ["ZZIYUT", "DO"],
                        left: 0,
                        top: weightedX * 4,
                        interval: weightedX * 5
                    },
                    {
                        names: ["SOKMO", "ANZZI"],
                        left: weightedY,
                        top: weightedY * 5,
                        interval: weightedY * 4
                    },
                    {
                        names: ["ZZIMO", "NALDO", "NALGAE", "NALGUL", "NALYUT", "NALMO"],
                        left: 0,
                        top: weightedX * 5,
                        interval: weightedX
                    }
                ]
            }
        }
    }
</script>

<style scoped lang="scss">
    #board {
        height: 450px;
        width: 450px;
        background-color: black;
    }
    #inner-board {
        height: 400px;
        width: 400px;
        padding-left: 20px;
        padding-top: 18px;
    }
</style>