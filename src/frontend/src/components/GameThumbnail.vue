<template>
    <router-link :to="'/' + game.gameTitle + '/rooms'" :gameTitle="game.gameTitle">
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
          <v-card-title class="yellow black--text">{{game.title}}</v-card-title>
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
          link: String,
          id: Number,
          gameTitle: String,
      },
    }
  }
</script>

<style scoped>
</style>