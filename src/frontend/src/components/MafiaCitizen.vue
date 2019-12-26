<template>
    <v-card
            max-width="400"
            class="margin-right-200 margin-bottom-50 background-transparent"
            tile
    >
        <v-img
                :src='citizen.src'
                class="black--text align-end"
                height="100px"
                width="100px"
        >
            <v-card-title>{{citizen.name}}</v-card-title>
        </v-img>
    </v-card>
</template>

<script>
    export default {
        name: 'MafiaCitizen',
        props: {
            citizen: {
                name: String,
                occupation: String,
                src: String
            }
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