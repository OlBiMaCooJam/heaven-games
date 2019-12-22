<template>
    <router-link :to="'/games/' + game.id + '/rooms'">
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
          link: String,
          id: Number
      }
    }
  }
</script>

<style scoped>
</style>