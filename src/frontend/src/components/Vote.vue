<template>
    <v-dialog v-model="dialog" persistent max-width="290">
        <template v-slot:activator="{ on }">
            <v-btn color="primary" dark v-on="on">투표</v-btn>
        </template>
        <v-card>
            <v-card-title class="headline">{{vote_msg}}</v-card-title>
            <v-select
                    :items="citizens"
                    item-text="name"
                    v-model="selected" aria-required="true"></v-select>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
                <v-btn color="green darken-1" text @click="select">투표</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: "Vote",
        props: {
            roomId: Number,
            vote_msg: String,
            client: {},
            dialog:Boolean,
            citizens: Array,
            selected: String,
            day: Boolean,
            occupation: String,
            date: Number
        },
        methods: {
            select() {
                this.dialog = false;
                if(this.day){
                    this.client.send('/app/rooms/' + this.roomId + '/select', JSON.stringify({ "name" : this.selected}));
                }else{
                    this.client.send('/app/rooms/' + this.roomId + '/' + this.occupation, JSON.stringify({ "name" : this.selected}));
                }
            }
        },

        computed() {
            if(this.day === false && this.date === 0) {
                this.dialog = false;
                this.client.send('/app/rooms/' + this.roomId + '/nightResult');
            }
        },
    }
</script>

<style scoped>

</style>