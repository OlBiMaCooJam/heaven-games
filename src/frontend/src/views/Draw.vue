<template>
    <v-container class="draw-app" fluid>
        <v-row>
            <v-col cols="4" class="white">
                <v-card flat color="transparent">
                    <v-subheader>사람 수</v-subheader>
                    <v-card-text>
                        <v-row>
                            <v-col class="pr-4">
                                <v-slider
                                        v-model="personCount"
                                        class="align-center"
                                        :label="String(personCount)"
                                        inverse-label
                                        :max="max"
                                        :min="min"
                                        @click="checkNumWhack"
                                        hide-details
                                >
                                </v-slider>
                            </v-col>
                        </v-row>
                    </v-card-text>
                    <v-subheader>꽝 개수</v-subheader>
                    <v-card-text>
                        <v-row>
                            <v-col class="pr-4">
                                <v-slider
                                        v-model="currentWhack"
                                        class="align-center"
                                        :label="String(currentWhack)"
                                        inverse-label
                                        :max="maxWhack"
                                        :min="minWhack"
                                        @click="checkNumWhack"
                                        hide-details
                                >
                                </v-slider>
                            </v-col>
                        </v-row>
                    </v-card-text>
                </v-card>
                <v-btn @click="start">섞기</v-btn>
            </v-col>
            <v-col cols="8">
                <v-row align='center' justify='center'>
                    <Lot :lot=lot :count="count" :key="count.id" v-for='count in personCount'/>
                </v-row>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
    import Lot from "../components/Lot";

    export default {
        name: "Draw",
        components: {Lot},
        data() {
            return {
                personCount: 4,
                currentWhack: 4,
                lot: [],
                min: 1,
                max: 10,
                maxWhack: 10,
                minWhack: 1,
            };
        },
        methods: {
            checkNumWhack() {
                const maxWhack = this.personCount;
                this.maxWhack = maxWhack;
                if (this.currentWhack > maxWhack) {
                    this.currentWhack = maxWhack;
                }
            },
            async start() {
                try {
                    const response = await this.$axios.post('/rooms/0/draw', {
                        personCount: this.personCount,
                        whackCount: this.currentWhack,
                    });
                    this.lot = response.data;
                } catch (e) {
                    alert(e);
                }
            },
        }
    }
</script>

<style scoped>

</style>