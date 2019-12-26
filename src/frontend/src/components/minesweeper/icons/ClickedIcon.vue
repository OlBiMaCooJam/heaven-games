<template>
  <v-icon :style="{color: getColor()}">{{icon}}</v-icon>
</template>

<script>
  export default {
    name: "ClickedBlock",
    props: {
      numberOfAroundMines: Number,
    },
    data() {
      return {
        icon: this.getIcon(),
        numericIcon: "mdi-numeric-",
        iconColors: ['black', 'grey', 'forestgreen', 'pink', 'navy', 'darkred', 'green', 'aqua', 'violet']
      }
    },
    methods: {
      getColor() {
        return this.iconColors[this.numberOfAroundMines];
      },
      getIcon() {
        return this.numberOfAroundMines > 0 ? `mdi-numeric-${this.numberOfAroundMines}` : '';
      }
    },
    watch: {
      numberOfAroundMines: {
        handler() {
          this.icon = this.numericIcon + this.numberOfAroundMines;
        }
      }
    }
  }
</script>

<style scoped>
</style>