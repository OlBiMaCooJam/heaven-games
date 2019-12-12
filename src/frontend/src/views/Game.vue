<template>
  <v-container>
    <span>Game Id : {{$route.params.id}}</span>
    <v-row align="center" justify="end" class="ma-5">
      <router-link :to="'/game/' + 'mafia'">
        <v-btn icon>
          게임 시작
        </v-btn>
      </router-link>
    </v-row>

  </v-container>
</template>

<script>
  export default {
    name: "Game"
  }
</script>

<style scoped>

</style>