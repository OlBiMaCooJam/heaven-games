<template>
    <v-container>
        <v-container>
            <span>Game Id : {{$route.params.id}}</span>
        </v-container>
        <Board></Board>
    </v-container>
</template>

<script>
    import Board from "../components/yutnori/Board"

    export default {
        components: {Board},
        name: "Yutnori"
    }
</script>

<style scoped>

</style>