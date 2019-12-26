<template>
    <div class="thrown-yuts">
        <p :key="index" v-for='(yut, index) in yuts'
           :style="{color: fontColor(index, chooseYutIndex)}" @click="chooseYut(yut, index)">{{yut}}</p>
    </div>
</template>

<script>
    export default {
        name: 'ThrownYuts',
        props: {
            yuts: Array,
            turn: Object,
            chooseYutIndex: Number
        },
        computed: {
            fontColor() {
                return (index, selectedYutIndex) => {
                    return selectedYutIndex == index ? "red" : "#2c3e50"
                }
            }
        },
        methods: {
            chooseYut(yut, index) {
                if (this.canClick()) {
                    this.$emit('chooseYut', yut, index, this.canClick());
                }
            },
            canClick() {
                return !this.turn.canThrow;
            }
        }
    }
</script>

<style scoped>
    .thrown-yuts {
        width: 100px;
        font-size: 40px;
    }
</style>