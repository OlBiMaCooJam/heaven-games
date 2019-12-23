<template>
    <div @click="pieceClick" class="piece">
        <v-icon :style="{color: pieceColor}" class="margin-0-auto">mdi-emoticon-devil</v-icon>
        <span class="piece-count" v-if="count > 1">x{{count}}</span>
    </div>
</template>

<script>
    export default {
        name: 'Piece',
        props: {
            pieceColor: String,
            count: {
                type: Number,
                default: 0
            }
        },
        methods: {
            pieceClick() {
                this.$emit('pieceClick')
            }
        }
    }
</script>

<style scoped>
    .piece {
        width: 100px;
        display: flex;
    }

    .piece-count {
        font-size: 13px;
        color: black;
    }

    .margin-0-auto {
        margin: 0 auto;
    }

</style>