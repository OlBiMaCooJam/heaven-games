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
        roomId: this.$route.params.id,
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
    }
  }
</script>

<style scoped>
</style>