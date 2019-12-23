<template>
    <div :id="pointName" :style="{marginLeft: left + 'px', marginTop: top + 'px'}" class="point"
         color="#f0f8ff">
        <Piece :count="pieceCnt" :pieceColor="pieceColor" @pieceClick="pieceClick" v-if="pieceCnt > 0"></Piece>
    </div>
</template>

<script>
    import Piece from "./Piece";

    export default {
        name: 'Point',
        components: {Piece},
        data: function () {
            return {
                count: 0
            }
        },
        computed: {
            pieceCnt: function () {
                window.console.log("count = " + this.count);
                window.console.log("inc = " + this.inc);
                return this.count + this.inc;
            }
        },
        props: {
            pointName: String,
            left: Number,
            top: Number,

            pieceColor: {
                type: String,
                default: 'black'
            },
            initialCount: {
                type: Number,
                default: 0
            },
            inc: {
                type: Number,
                default: 0
            }
        },
        methods: {
            pieceClick() {
                this.$emit('chooseSrcPoint', this.pointName)
            }
        },
        created() {
            this.count = this.initialCount;
        }
    };
</script>

<style scoped>
    .point {
        position: absolute;
        border-radius: 50px;
        width: 30px;
        height: 30px;
        border-color: black;
        background-color: cadetblue;

        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>