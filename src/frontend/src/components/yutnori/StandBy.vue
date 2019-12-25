<template>
    <div class="standby">
        <Piece :count="1" :key="index" :color="pieceColor" :turnColor="turnColor"
               @pieceClick="pieceClick" v-for="index in count" :selected="isSelected(index)"></Piece>
    </div>
</template>

<script>
    import Piece from "./Piece";

    export default {
        name: 'StandBy',
        components: {Piece},
        data() {
          return {
              name: 'STANDBY'
          }
        },
        computed: {
          isSelected() {
              const STANDBY = this;
              return (index) => {
                  return index == 1 && STANDBY.selected;
              }
            }
        },
        props: {
            count: Number,
            pieceColor: {
                type: String,
                default: 'black'
            },
            selected: Boolean,
            turnColor: String
        },
        methods: {
            pieceClick() {
                window.console.log("here2  ++")

                this.$emit('chooseSrcPoint', this.name)
                window.console.log("here -----")

            }
        }
    }
</script>

<style scoped>
    .standby {
        display: flex;
        width: 200px;
    }
</style>