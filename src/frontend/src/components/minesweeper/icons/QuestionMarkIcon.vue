<template>
  <v-icon color="purple">{{icon}}</v-icon>
</template>

<script>
  export default {
    name: "QuestionMarkBlock",
    data() {
      return {
        icon: "mdi-map-marker-question-outline",
      }
    }
  }
</script>

<style scoped>
  v-icon {
    color: black;
  }
</style>