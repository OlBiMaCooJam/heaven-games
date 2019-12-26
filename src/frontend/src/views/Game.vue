<template>
  <v-container>
      <span>Room Id: {{roomId}}</span>
      <v-list>
          <Player v-for='player in players' :key="player.name" :player="player"></Player>
      </v-list>
      <v-row align="center" justify="end" class="ma-5">
          <router-link :to="'/' + gameTitle + '/rooms/' + roomId + '/start'">
              <v-btn icon @click="start">
                  게임 시작
              </v-btn>
          </router-link>
      </v-row>
  </v-container>
</template>

<script>
    import SockJS from "sockjs-client";
    import Stomp from "webstomp-client";
    import router from "../router";
    import Player from "../components/Player";

    export default {
    name: "Game",
        components: {Player},
        props: {
            gameTitle: String,
        },
        data() {
      return {
        userId: '',
        roomId: this.$route.params.id,
          client: {},
          players: [
              // {name: "쿠기"},
              // {name: "마틴"},
          ],
      }
    },
    methods: {
      start: function() {
        this.client.send('/app/rooms/'+ this.roomId + '/start');
      }
    },
    created() {
      const game = this;
      game.client = Stomp.over(new SockJS('/websocket'));

      game.client.connect({}, function () {
        let join = true;
        game.client.send('/app/rooms/' + game.roomId);
        game.client.subscribe('/topic/rooms/' + game.roomId, function(response){
            const roomResponse = JSON.parse(response.body);
            window.console.log(roomResponse);
            game.players = roomResponse.players;
          if(join){
              game.userId = game.players[game.players.length - 1].id;
            join = false;
          }
        });
        game.client.subscribe('/topic/rooms/' + game.roomId + '/start', function(){
            router.push('/' + game.gameTitle + '/rooms/' + game.roomId);
        });
      });
    },
    destroyed() {
      this.client.send('/app/rooms/' + this.roomId + '/leave/' + this.userId);
      this.client.disconnect();
    }
  }
</script>

<style scoped>

</style>