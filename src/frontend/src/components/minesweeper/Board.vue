<template>
  <v-container id="board">
    <template v-for="y in blocks.length">
      <v-row no-gutters :key="y" justify="center">
        <template v-for="x in blocks[0].length">
          <v-col :key="x" style="flex-grow: 0">
            <Block @clickBlock="clickBlock" :x="x" :y="y" :block="blocks[y-1][x-1]"></Block>
          </v-col>
        </template>
      </v-row>
    </template>
  </v-container>
</template>

<script>
  import axios from 'axios';
  import Block from "./Block";

  export default {
    name: "Board",
    components: {
      Block
    },
    props: {
      boardBlocks: Array,
    },
    data() {
      return {
        blocks: this.boardBlocks,
        minesweeperStatus: '',
      }
    },
    methods: {
      clickBlock(x, y, clickType) {
        if (this.checkGameOver()) {
          return;
        }

        if ('LEFT' === clickType) {
          this.clickLeft(x, y);
          return;
        }
        this.clickRight(x, y);
      },
      async clickLeft(x, y) {
        const clickResponse = await this.click(x, y, "LEFT");
        const clickedBlocks = clickResponse.clickedBlocks;
        this.minesweeperStatus = clickResponse.minesweeperStatus;
        for (let i = 0, len = clickedBlocks.length; i < len; i++) {
          const clickedBlock = clickedBlocks[i];
          this.blocks = this.changeStatus(clickedBlock.x, clickedBlock.y,
              clickedBlock.blockStatus, clickedBlock.numberOfAroundMines);
        }

        this.checkGameOver();
      },
      async clickRight(x, y) {
        const clickResponse = await this.click(x, y, "RIGHT");
        const clickedBlocks = clickResponse.clickedBlocks;
        this.blocks = this.changeStatus(x, y, clickedBlocks[0].blockStatus, clickedBlocks[0].numberOfAroundMines);
      },
      changeStatus(x, y, blockStatus, numberOfAroundMines) {
        const nextStatus = {
          iconName: blockStatus,
          numberOfAroundMines: numberOfAroundMines
        };
        let newBoard = [...this.blocks];
        newBoard[y].splice(x, 1, nextStatus);
        return newBoard;
      },
      async click(x, y, clickType) {
        try {
          const response = await axios.put('/rooms/1/minesweeper', {
            position: {
              x: x,
              y: y
            },
            clickType: clickType
          });
          return {
            clickedBlocks: this.parseBlocks(response.data.clickedBlocks.clickedBlocks),
            minesweeperStatus: response.data.minesweeperStatus
          };
        } catch (error) {
          alert(error)
        }
      },
      checkGameOver() {
        if (this.isGameOver()) {
          alert(this.minesweeperStatus);
          return true;
        }
        return false;
      },
      isGameOver() {
        return "WIN" === this.minesweeperStatus || "LOSE" === this.minesweeperStatus;
      },
      parseBlocks(clickedBlocks) {
        const blocks = [];
        for (let block in clickedBlocks) {
          const position = this.parsePosition(block);
          clickedBlocks[block].x = position.x;
          clickedBlocks[block].y = position.y;
          blocks.push(clickedBlocks[block])
        }

        return blocks;
      },
      parsePosition(block) {
        return {
          x: block.match("x=[0-9]+")[0].split("=")[1],
          y: block.match("y=[0-9]+")[0].split("=")[1]
        }
      },
    }
  }
</script>

<style scoped>

</style>