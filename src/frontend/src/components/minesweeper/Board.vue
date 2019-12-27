<template>
  <v-container id="board">
    <template v-for="y in boardBlocks.length">
      <v-row no-gutters :key="y" justify="center">
        <template v-for="x in boardBlocks[0].length">
          <v-col :key="x" style="flex-grow: 0">
            <Block @clickBlock="clickBlock" :x="x" :y="y" :block="boardBlocks[y-1][x-1]"></Block>
          </v-col>
        </template>
      </v-row>
    </template>
  </v-container>
</template>

<script>
  import Block from "./Block";

  export default {
    name: "Board",
    components: {
      Block
    },
    props: {
      boardBlocks: Array
    },
    methods: {
      clickBlock(x, y, clickType) {
        this.$emit("clickBlock", x, y, clickType);
      },
    }
  }
</script>

<style scoped>

</style>