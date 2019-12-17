<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-subheader>가로</v-subheader>
                <v-card-text>
                  <v-row>
                    <v-col class="pr-4">
                      <v-slider
                          v-model="board.columns"
                          class="align-center"
                          :label="String(board.columns)"
                          inverse-label
                          :max="board.maxColumns"
                          :min="board.minColumns"
                          @click="checkNumMines"
                          hide-details
                      >
                      </v-slider>
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-col>
              <v-col cols="12">
                <v-subheader>세로</v-subheader>
                <v-card-text>
                  <v-row>
                    <v-col class="pr-4">
                      <v-slider
                          v-model="board.rows"
                          class="align-center"
                          :label="String(board.rows)"
                          inverse-label
                          :max="board.maxRows"
                          :min="board.minRows"
                          @click="checkNumMines"
                          hide-details
                      >
                      </v-slider>
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-col>
              <v-col cols="12">
                <v-subheader>지뢰 개수</v-subheader>
                <v-card-text>
                  <v-row>
                    <v-col class="pr-4">
                      <v-slider
                          v-model="board.mines"
                          class="align-center"
                          :max="board.maxMines"
                          :min="board.minMines"
                          :label="String(board.mines)"
                          inverse-label
                          hide-details
                      >
                      </v-slider>
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="createGame">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-container id="board" v-if="gameCreated">
      <template v-for="y in board.rows">
        <v-row no-gutters :key="y" justify="center">
          <template v-for="x in board.columns">
            <v-col :key="x" style="flex-grow: 0">
              <v-btn
                  class="pa-0 ma-0"
                  style="height: 64px; background-color: lightgoldenrodyellow; border: 1px solid #E7B547"
                  outlined
                  tile
                  @click.left="clickLeft(x-1, y-1)"
                  @click.right="clickRight(x-1, y-1, $event)"
              >
                <v-icon>{{board.blocks[y-1][x-1].status}}</v-icon>
              </v-btn>
            </v-col>
          </template>
        </v-row>
      </template>
    </v-container>
  </v-row>
</template>
<script>
  import axios from 'axios';

  export default {
    name: "Minesweeper",
    data() {
      return {
        board: {
          blocks: [],
          minRows: 10,
          minColumns: 10,
          maxRows: 30,
          maxColumns: 30,
          minMines: 1,
          rows: 10,
          columns: 10,
          mines: 1,
          maxMines: 99,

        },
        icons: {
          UNCLICKED: '',
          MINE: 'mdi-mine',
          FLAG: 'mdi-flag-variant',
          QUESTION_MARK: 'mdi-map-marker-question-outline',
          CLICKED: ['mdi-numeric-0', 'mdi-numeric-1', 'mdi-numeric-2',
            'mdi-numeric-3', 'mdi-numeric-4', 'mdi-numeric-5',
            'mdi-numeric-6', 'mdi-numeric-7', 'mdi-numeric-8']
        },
        dialog: true,
        gameCreated: false,
      }
    },
    methods: {
      addBlock() {
        for (let i = 0; i < this.board.rows; i++) {
          this.board.blocks.push([]);
          for (let j = 0; j < this.board.columns; j++) {
            this.board.blocks[i].push({status: this.icons.UNCLICKED, clicked: false});
          }
        }
      },
      async clickLeft(x, y) {
        const response = await this.click(x, y, "LEFT");
        const blockStatus = response.data.blockStatus;
        const numberOfAroundMines = response.data.numberOfAroundMines;

        if (response.status === 200) {
          this.board.blocks = this.changeStatus(x, y, blockStatus, numberOfAroundMines);
          this.board.blocks[y][x].clicked = true;
          if (blockStatus === "CLICKED" && numberOfAroundMines === 0) {
            this.propagateBlanks(x, y);
          }
        }
      },
      async clickRight(x, y, event) {
        event.preventDefault();
        const response = await this.click(x, y, "RIGHT");
        if (response.status === 200) {
          this.board.blocks = this.changeStatus(x, y, response.data.blockStatus);
        }
      },
      changeStatus(x, y, blockStatus, numberOfAroundMines) {
        const nextStatus = this.getNextStatus(blockStatus, numberOfAroundMines);
        let newBoard = [...this.board.blocks];
        newBoard[y].splice(x, 1, {status: nextStatus, clicked: this.board.blocks[y][x].clicked});
        return newBoard;
      },
      getNextStatus(status, numberOfAroundMines) {
        if (status === "CLICKED") {
          return this.icons.CLICKED[numberOfAroundMines];
        }
        return this.icons[status]
      },
      async propagateBlanks(x, y) {
        for (let i = y - 1; i <= y + 1; i++) {
          for (let j = x - 1; j <= x + 1; j++) {
            if (i >= 0 && j >= 0 && i < this.board.columns && j < this.board.rows && this.board.blocks[j][i].clicked === false) {
              this.clickLeft(j, i)
            }
          }
        }
      },
      checkNumMines() {
        const maxMines = this.board.rows * this.board.columns - 1;
        this.board.maxMines = maxMines;
        if (this.board.mines > maxMines) {
          this.board.mines = maxMines;
        }
      },
      createBoard() {
        this.addBlock();
        this.gameCreated = true;
      },
      async createGame() {
        try {
          const response = await axios.post('/rooms/1/minesweeper', {
            columns: this.board.columns,
            rows: this.board.rows,
            mines: this.board.mines
          });
          if (response.status === 201) {
            this.dialog = false;
            this.createBoard();
          }
        } catch (error) {
          alert(error)
        }
      },
      click(x, y, clickType) {
        try {
          return axios.put('/rooms/1/minesweeper', {
            position: {
              x: x,
              y: y
            },
            clickType: clickType
          });
        } catch (error) {
          alert(error)
        }
      },
    }
  }
</script>

<style scoped>
  .display-none {
    display: none;
  }
</style>