<template>
    <div :id="name" :style="{marginLeft: left + 'px', marginTop: top + 'px'}" class="point"
         color="#f0f8ff">
        <Piece :turnColor="turnColor" :count="piece.count" :color="piece.color" @pieceClick="pieceClick"
               v-if="piece.count > 0" :selected="selected"></Piece>
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
            },
            selected: Boolean,
            turnColor: String
        },
        data() {
            return {
                name: this.pointName,
            }
        },

        methods: {
            pieceClick() {
                window.console.log("here5")

                this.$emit('chooseSrcPoint', this.name)
                window.console.log("here6")

            },
        },
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