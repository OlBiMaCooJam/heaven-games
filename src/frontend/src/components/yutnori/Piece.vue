<template>
    <div @click="pieceClick" class="piece">
        <v-icon :style="{color: color}" class="margin-0-auto" :size="getSize">mdi-emoticon-devil</v-icon>
        <span class="piece-count" v-if="count > 1">x{{count}}</span>
    </div>
</template>

<script>
    export default {
        name: 'Piece',
        props: {
            color: String,
            count: {
                type: Number,
                default: 0
            },
            selected: {
                type: Boolean,
                default: false
            },
            turnColor: String
        },
        computed: {
            getSize() {
                return (this.selected && this.checkTurn) ? 40 : 20;
            }
        },
        methods: {
            pieceClick() {
                this.$emit('pieceClick', this.checkTurn())
            },
            checkTurn() {
                return this.turnColor == this.color;
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