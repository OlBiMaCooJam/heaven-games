<template>
  <v-container id="board">
    <template v-for="y in blocks.length">
      <v-row no-gutters :key="y" justify="center">
        <template v-for="x in blocks[0].length">
          <v-col :key="x" style="flex-grow: 0">
            <v-btn
                class="pa-0 ma-0"
                style="height: 64px; background-color: lightgoldenrodyellow; border: 1px solid #E7B547"
                outlined
                tile
                @click.left="clickBlock(x-1, y-1, clickLeft)"
                @click.right="clickBlock(x-1, y-1, clickRight, $event)"
            >
              <v-icon>{{blocks[y-1][x-1]}}</v-icon>
            </v-btn>
          </v-col>
        </template>
      </v-row>
    </template>
  </v-container>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "Board",
    props: {
      boardBlocks: Array,
    },
    data() {
      return {
        blocks: this.boardBlocks,
        icons: {
          UNCLICKED: '',
          MINE: 'mdi-mine',
          FLAG: 'mdi-flag-variant',
          QUESTION_MARK: 'mdi-map-marker-question-outline',
          CLICKED: ['mdi-numeric-0', 'mdi-numeric-1', 'mdi-numeric-2',
            'mdi-numeric-3', 'mdi-numeric-4', 'mdi-numeric-5',
            'mdi-numeric-6', 'mdi-numeric-7', 'mdi-numeric-8']
        },
        minesweeperStatus: '',
      }
    },
    methods: {

      clickBlock(x, y, clickCallback, event) {
        if (this.checkGameOver()) {
          return;
        }

        clickCallback(x, y, event);
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
      async clickRight(x, y, event) {
        event.preventDefault();
        const clickResponse = await this.click(x, y, "RIGHT");
        const clickedBlocks = clickResponse.clickedBlocks;
        this.blocks = this.changeStatus(x, y, clickedBlocks[0].blockStatus, clickedBlocks[0].numberOfAroundMines);
      },
      changeStatus(x, y, blockStatus, numberOfAroundMines) {
        const nextStatus = this.getNextStatus(blockStatus, numberOfAroundMines);
        let newBoard = [...this.blocks];
        newBoard[y].splice(x, 1, nextStatus);
        return newBoard;
      },
      getNextStatus(status, numberOfAroundMines) {
        if (status === "CLICKED") {
          return this.icons.CLICKED[numberOfAroundMines];
        }
        return this.icons[status]
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