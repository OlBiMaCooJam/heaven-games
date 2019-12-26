<template>
    <v-list-item>
        <v-list-item-content>
            <v-list-item-title>{{player.name}}</v-list-item-title>
        </v-list-item-content>
    </v-list-item>
</template>

<script>
    export default {
        name: "Player",
        props: {
            player: {},
        },
    }
</script>

<style scoped>

</style>