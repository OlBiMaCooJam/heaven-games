<template>
  <v-row justify="center">
    <BoardSpec :dialog="dialog" @createGame="createGame"></BoardSpec>
    <Board v-if="gameCreated" @clickBlock="clickBlock" :boardBlocks="blocks"
           :minesweeperStatus="minesweeperStatus"></Board>
  </v-row>
</template>
<script>
  import axios from 'axios';
  import Board from '../components/minesweeper/Board'
  import BoardSpec from "../components/minesweeper/BoardSpec";

  export default {
    name: "Minesweeper",
    components: {
      BoardSpec,
      Board,
    },
    data() {
      return {
        blocks: [],
        dialog: true,
        gameCreated: false,
        roomId: this.$route.params.id,
        minesweeperStatus: "PLAYING"
      }
    },
    methods: {
      async createGame(boardSpec) {
        try {
          const response = await axios.post(`/rooms/${this.roomId}/minesweeper`, {
            columns: boardSpec.columns,
            rows: boardSpec.rows,
            mines: boardSpec.mines
          });
          if (response.status === 201) {
            this.dialog = false;
            this.createBoard(boardSpec);
          }
        } catch (error) {
          alert(error)
        }
      },
      createBoard(boardSpec) {
        this.addBlock(boardSpec);
        this.gameCreated = true;
      },
      addBlock(boardSpec) {
        for (let i = 0; i < boardSpec.rows; i++) {
          this.blocks.push([]);
          for (let j = 0; j < boardSpec.columns; j++) {
            const block = {
              iconName: 'UNCLICKED',
              numberOfAroundMines: 0
            };
            this.blocks[i].push(block);
          }
        }
      },
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
          const response = await axios.put(`/rooms/${this.roomId}/minesweeper`, {
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