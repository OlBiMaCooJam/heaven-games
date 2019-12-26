<template>
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
                        :max="maxColumns"
                        :min="minColumns"
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
                        :max="maxRows"
                        :min="minRows"
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
                        :max="maxMines"
                        :min="minMines"
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
</template>

<script>
  export default {
    name: "BoardSpec",
    props: {
      dialog: Boolean
    },
    data() {
      return {
        minRows: 10,
        minColumns: 10,
        maxRows: 30,
        maxColumns: 30,
        minMines: 1,
        maxMines: 99,

        board: {
          rows: 10,
          columns: 10,
          mines: 1,
        }
      }
    },
    methods: {
      createGame() {
        this.$emit('createGame', this.board)
      },
      checkNumMines() {
        const maxMines = this.board.rows * this.board.columns - 1;
        this.maxMines = maxMines;
        if (this.board.mines > maxMines) {
          this.board.mines = maxMines;
        }
      },
    }
  }
</script>