<template>
  <div class="middle">
    <a :href="'http://' + host + '/login/kakao'">
      <img alt="kakao_login" class="logo" src="../assets/kakao_account_login_btn_large_narrow.png">
    </a>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        host: location.host
      }
    }
  }
</script>

<style scoped>
  div {
    background-color: black;
  }

  .logo {
    margin-left: auto;
    margin-right: auto;
    display: block;
  }

  .middle {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>