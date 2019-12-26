<template>
  <v-app id="app">
    <v-row id="nav">
      <v-col cols="1" id="home">
        <router-link to="/">
          <img id="btn-home" src="./assets/Logo.jpg" alt="Logo">
        </router-link>
      </v-col>
      <v-col cols="11">
        <span id="title">미 니 게 임 천 국</span>
      </v-col>
      <v-col cols="1">
        <router-link to="/login">
          <v-btn text large>로그인</v-btn>
        </router-link>
        <router-link to="/draw">
          <v-btn>Draw</v-btn>
        </router-link>
      </v-col>
    </v-row>
    <router-view/>
  </v-app>
</template>

<script>
  export default {
    name: 'App',
  }
</script>

<style lang="scss">
  @font-face {
    font-family: 'rose';
    src: url('assets/a장미다방.otf')
  }

  #app {
    font-family: 'rose';
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    background-image: url('assets/Background.png');
    height: 100%;
  }

  #nav {
    padding: 30px;
    max-height: 30%;
  }

  #btn-home {
    width: 50px;
  }

  #home {
    display: unset;
    float: left;
    position: fixed;
  }

  #title {
    font-size: 5em;
    line-height: 1;
    display: inline-block;
    margin-left: 100px;
  }
</style>
