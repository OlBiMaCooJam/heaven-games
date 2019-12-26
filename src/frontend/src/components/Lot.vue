<template>
    <v-card
            max-width="400"
            class="margin-right-200 margin-bottom-50"
            tile
    >
        <v-img @click="click"
               :src=this.src
               class="black--text align-end"
               height="200px"
               width="200px"
        >
            {{result}}
        </v-img>
    </v-card>
</template>

<script>
    export default {
        name: "Lot",
        props: {
            lot: {},
            count: Number,
        },
        data() {
            return {
                src: require('../assets/note.png'),
                result: this.count,
            }
        },
        methods: {
            click() {
                if (this.lot.lots != null) {
                    if (this.lot.lots.lots[this.count - 1] === 'WHACK') {
                        this.src = require('../assets/whack.png');
                        this.result = this.count + '.꽝';
                    }
                    if (this.lot.lots.lots[this.count - 1] === 'PASS') {
                        this.src = require('../assets/pass.png');
                        this.result = this.count + '.통과';
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>