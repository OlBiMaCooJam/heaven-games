<template>
    <v-card
            class="mx-auto ma-3"
            max-width="400"
            tile
    >
        <v-img
                class="white--text align-end"
                height="400px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
        >
            <v-card-title>{{title}}</v-card-title>
        </v-img>
    </v-card>
</template>

<script>
    export default {
        name: "GameThumbnail",
        props: {
            title: String,
            subtitle: String // 데이터 넣어주기
        }
    }
</script>

<style scoped>
</style>