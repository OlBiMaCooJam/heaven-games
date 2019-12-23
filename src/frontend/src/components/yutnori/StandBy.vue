<template>
    <div class="standby">
        <Piece :count="1" :key="index" :pieceColor="pieceColor"
               @pieceClick="pieceClick" v-for="index in count"></Piece>
    </div>
</template>

<script>
    import Piece from "./Piece";

    export default {
        name: 'StandBy',
        components: {Piece},
        props: {
            count: Number,
            pieceColor: {
                type: String,
                default: 'black'
            },
        },
        methods: {
            pieceClick() {
                this.$emit('chooseSrcPoint', 'STANDBY')
            }
        }
    }
</script>

<style scoped>
    .standby {
        display: flex;
        width: 200px;
    }
</style>