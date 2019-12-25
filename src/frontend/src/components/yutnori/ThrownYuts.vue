<template>
    <div class="thrown-yuts">
        <p :key="index" @click="chooseYut" v-for='(yut, index) in yuts'>{{yut}}</p>
    </div>
</template>

<script>
    export default {
        name: 'ThrownYuts',
        props: {
            yuts: Array
        },
        methods: {
            chooseYut(event) {
                this.$emit('chooseYut', event.target.innerText);
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