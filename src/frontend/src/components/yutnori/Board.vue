<template>
    <div id="board">
        <Point v-for='[key, val] in points' :key="key" @chooseSrcPoint="chooseSrcPoint"
               :pointName="key" :left="val.left" :top="val.top" :piece="pieces[key]"/>
    </div>
</template>

<script>
    import Point from "./Point"

    export default {
        name: "Board",
        components: {Point},
        data: function () {
            return {
                points: Map //key: pointName, val: left, top
            }
        },
        props: {
            pieces: {}
        },
        created() {
            this.points = this.getPointsFromRows()
        },
        methods: {
            chooseSrcPoint(pointName) {
                this.$emit('chooseSrcPoint', pointName)
            },
            getPointsFromRows() {
                const weight = 12;
                const initX = 40, initY = 30;
                const verticalInterval = 6, diagonalInterval = 5;
                const weightedVerticalInterval = verticalInterval * weight,
                    weightedDiagonalInterval = diagonalInterval * weight;
                const rows = [
                    {
                        names: ["DUITMO", "DUITYUT", "DUITGUL", "DUITGAE", "DUITDO", "MO"],
                        left: initX,
                        top: initY,
                        interval: weightedVerticalInterval
                    },
                    {
                        names: ["DUITMODO", "MODO"],
                        left: weightedDiagonalInterval + initX,
                        top: weightedDiagonalInterval + initY,
                        interval: weightedDiagonalInterval * 4
                    },
                    {
                        names: ["ZZIDO", "YUT"],
                        left: initX,
                        top: weightedVerticalInterval + initY,
                        interval: weightedVerticalInterval * 5
                    },
                    {
                        names: ["DUITMOGAE", "MOGAE"],
                        left: weightedDiagonalInterval * 2 + initX,
                        top: weightedDiagonalInterval * 2 + initY,
                        interval: weightedDiagonalInterval * 2
                    },
                    {
                        names: ["ZZIGAE", "GUL"],
                        left: initX,
                        top: weightedVerticalInterval * 2 + initY,
                        interval: weightedVerticalInterval * 5
                    },
                    {
                        names: ["BANG"],
                        left: weightedDiagonalInterval * 3 + initX,
                        top: weightedDiagonalInterval * 3 + initY,
                        interval: 0
                    },
                    {
                        names: ["ZZIGUL", "GAE"],
                        left: initX,
                        top: weightedVerticalInterval * 3 + initY,
                        interval: weightedVerticalInterval * 5
                    },
                    {
                        names: ["SOKYUT", "BANGSUGI"],
                        left: weightedDiagonalInterval * 2 + initX,
                        top: weightedDiagonalInterval * 4 + initY,
                        interval: weightedDiagonalInterval * 2
                    },
                    {
                        names: ["ZZIYUT", "DO"],
                        left: initX,
                        top: weightedVerticalInterval * 4 + initY,
                        interval: weightedVerticalInterval * 5
                    },
                    {
                        names: ["SOKMO", "ANZZI"],
                        left: weightedDiagonalInterval + initX,
                        top: weightedDiagonalInterval * 5 + initY,
                        interval: weightedDiagonalInterval * 4
                    },
                    {
                        names: ["ZZIMO", "NALDO", "NALGAE", "NALGUL", "NALYUT", "CHAMMUGI"],
                        left: initX,
                        top: weightedVerticalInterval * 5 + initY,
                        interval: weightedVerticalInterval
                    }
                ]

                let points = new Map();
                for (let i = 0; i < rows.length; i++) {
                    for (let k = 0; k < rows[i].names.length; k++) {
                        const pointName = rows[i].names[k]

                        points.set(pointName, {
                            top: rows[i].top,
                            left: rows[i].left + rows[i].interval * k,
                        })
                    }
                }
                return points;
            }
        }
    }
</script>

<style scoped>
    #board {
        height: 450px;
        width: 470px;
        background-color: beige;
        text-align: center;
    }
</style>