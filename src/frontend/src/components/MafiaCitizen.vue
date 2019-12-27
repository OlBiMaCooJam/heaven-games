<template>
    <v-card
            max-width="400"
            class="margin-right-200 margin-bottom-50 background-transparent"
            tile
    >
    <v-card-title>{{name}}</v-card-title>
    </v-card>
</template>

<script>
    export default {
        name: 'MafiaCitizen',
        props: {
            name: String,
        }
    }
</script>

<style scoped>
    .margin-bottom-50 {
        margin-bottom: 50px
    }

    .margin-right-200 {
        margin-right: 200px
    }

    .background-transparent {
        background-color: transparent;
    }
</style>