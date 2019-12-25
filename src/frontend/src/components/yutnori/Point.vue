<template>
    <div :id="pointName" :style="{marginLeft: left + 'px', marginTop: top + 'px'}" class="point"
         color="#f0f8ff">
        <Piece :count="piece.count" :color="piece.color" @pieceClick="pieceClick" v-if="piece.count > 0"></Piece>
    </div>
</template>

<script>
    import Piece from "./Piece";

    export default {
        name: 'Point',
        components: {Piece},
        props: {
            pointName: String,
            left: Number,
            top: Number,
            piece: {
                type: Object,
                default: () => {
                    return {color: "", count: 0}
                }
            }
        },
        methods: {
            pieceClick() {
                this.$emit('chooseSrcPoint', this.pointName)
            },
        },
        created() {
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