<template>
  <router-link :to="game.url">
      <!--  <router-link :to="'/games/' + game.id">-->

      <v-card
        class="mx-auto ma-3"
        max-width="400"
        tile
    >
      <v-img
          :src="game.src"
          class="white--text align-end"
          height="400px"
      >
        <v-card-title>{{game.title}}</v-card-title>
      </v-img>
    </v-card>
  </router-link>
</template>

<script>
  export default {
    name: "GameThumbnail",
    props: {
      game: {
        title: String,
        src: String,
        url: String,
        link: String
      }
    }
  }
</script>

<style scoped>
</style>