<template>
  <v-row justify="center">
    <BoardSpec :dialog="dialog" @createGame="createGame"></BoardSpec>
    <Board v-if="gameCreated" :boardBlocks="blocks" :gameCreated="gameCreated"></Board>
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
      }
    },
    methods: {
      async createGame(boardSpec) {
        try {
          const response = await axios.post('/rooms/1/minesweeper', {
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
            this.blocks[i].push('');
          }
        }
      },
    }
  }
</script>

<style scoped>
</style>