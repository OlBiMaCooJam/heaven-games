<template>
  <v-btn
      class="pa-0 ma-0"
      :style="{backgroundColor : backgroundColor()}"
      style="height: 64px; border: 1px solid #E7B547"
      outlined
      tile
      @click.left="clickBlock(x-1, y-1, 'LEFT', $event)"
      @click.right="clickBlock(x-1, y-1, 'RIGHT', $event)"
  >
    <component :is="icon" :numberOfAroundMines="numberOfAroundMines"></component>
  </v-btn>
</template>

<script>
  import ClickedIcon from "./icons/ClickedIcon";
  import FlagIcon from "./icons/FlagIcon";
  import MineIcon from "./icons/MineIcon";
  import QuestionMarkIcon from "./icons/QuestionMarkIcon";
  import UnclickedIcon from "./icons/UnclickedIcon";

  export default {
    name: "Block",
    props: {
      x: Number,
      y: Number,
      block: Object,
    },
    components: {
      UNCLICKED: UnclickedIcon,
      MINE: MineIcon,
      FLAG: FlagIcon,
      QUESTION_MARK: QuestionMarkIcon,
      CLICKED: ClickedIcon,
    },
    data() {
      return {
        icon: this.block.iconName,
        numberOfAroundMines: this.block.numberOfAroundMines,
        backgroundColor: () => {
          return this.isPressed() ? this.isMine() ? 'red' : 'orange' : 'lightgoldenrodyellow'
        },
      }
    },
    watch: {
      block: {
        handler() {
          this.icon = this.block.iconName;
          this.numberOfAroundMines = this.block.numberOfAroundMines;
        }
      }
    },
    methods: {
      clickBlock(x, y, clickType, event) {
        event.preventDefault();
        this.$emit('clickBlock', x, y, clickType);
      },
      isPressed() {
        return this.icon === 'CLICKED' || this.isMine()
      },
      isMine() {
        return this.icon === 'MINE'
      }
    },
  }
</script>

<style scoped>
  .pressed {
    background-color: yellow;
  }
</style>