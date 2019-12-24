<template>
  <v-container>
    <span>Game Id : {{$route.params.id}}</span>
      <v-row align="center" justify="end" class="ma-5">
          <router-link :to="'/games/' + $route.params.gameId + '/rooms/' + $route.params.roomId + '/start'">
              <v-btn icon @click="start()">
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

  export default {
    name: "Game",
    data() {
      return {
        userId: '',
        roomId: '',
        client: {}
      }
    },
    methods: {
      start: function() {
        this.client.send('/app/rooms/'+ this.roomId + '/start');
      }
    },
    created() {
        this.roomId = this.$route.params.roomId;
      const game = this;
      game.client = Stomp.over(new SockJS('/websocket'));

      game.client.connect({}, function () {
        let join = true;
        game.client.send('/app/rooms/' + game.roomId);
        game.client.subscribe('/topic/rooms/' + game.roomId, function(response){
          const roomResponse = JSON.parse(response.body)
          const players = roomResponse.players;
          if(join){
            game.userId = players[players.length-1].id;
            join = false;
          }
        });
        game.client.subscribe('/topic/rooms/' + game.roomId + '/start', function(){
            router.push('/games/' + game.roomId + '/rooms/' + game.roomId + '/start');
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