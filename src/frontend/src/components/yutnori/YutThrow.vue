<template>
    <div class="yut-throw">
        <div class="yut-text">
            <p>{{yut}}</p>
        </div>
        <div>
            <v-btn @click="throwYut" color="primary" :disabled="disabled" large>윷 던지기</v-btn>
        </div>
    </div>
</template>

<script>
    export default {
        name: "YutThrow",
        props: {
            yut: String,
            disabled: Boolean
        },
        methods: {
            throwYut() {
                this.$emit('throwYut')
            }
        }
    }
</script>

<style scoped>
    .yut-text {
        font-size: 100px;
        height: 180px;
    }

    .yut-throw {
        vertical-align: middle;
    }
</style>