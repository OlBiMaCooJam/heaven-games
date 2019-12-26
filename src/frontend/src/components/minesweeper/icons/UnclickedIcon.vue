<template>
  <v-icon>{{icon}}</v-icon>
</template>

<script>
  export default {
    name: "UnclickedBlock",
    data() {
      return {
        icon: "",
      }
    }
  }
</script>

<style scoped>
  v-icon {
    color: black;
  }
</style>