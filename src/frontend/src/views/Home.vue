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
        games: [{title: '마피아게임', src: require('../assets/mafia_thumbnail.jpg'), gameKind: 'MAFIA'},
          {title: '윷놀이', src: require('../assets/yutnori_thumbnail.jpeg'), gameKind: 'YUTNORI'},
          {title: '지뢰찾기', src: require('../assets/minesweeper_thumbnail.jpeg'), gameKind: 'MINE'},
          {title: '제비뽑기', src: require('../assets/draw_thumbnail.png'), gameKind: 'DRAW'},
          {title: '사다리타기', src: require('../assets/ladder_thumbnail.png'), gameKind: 'LADDER'},
          {title: '노래맞추기', src: 'https://cdn.vuetifyjs.com/images/cards/docks.jpg', gameKind: 'SONG'}],
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