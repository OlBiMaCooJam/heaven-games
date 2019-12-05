<template>
  <v-container class="home" fluid>
    <v-row>
      <v-col cols="12">
        <v-row align='center' justify='center'>
          <GameThumbnail :game="game" :key="game.title" class='thumb' v-for='game in games'/>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import GameThumbnail from "../components/GameThumbnail";

  export default {
    name: 'home',
    components: {
      GameThumbnail
    },
    data() {
      return {
        games: [{title: '마피아게임', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', id: '1'},
          {title: '윷놀이', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', id: '2'},
          {title: '지뢰찾기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', id: '3'},
          {title: '노래맞추기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', id: '4'},
          {title: '맞고', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', id: '5'},
          {title: '사다리타기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', id: '6'}] //데이터 예시
      }
    }
  }
</script>

<style scoped>
  .thumb {
    margin-bottom: 50px;
    margin-right: 5%;
    margin-left: 5%;
  }
</style>