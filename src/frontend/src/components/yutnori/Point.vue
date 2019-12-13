<template>
    <v-card class="point" :id="pointName" color="#d3d3d3"
            :style="{marginLeft: left + 'px', marginTop: top + 'px'}">
        <v-card-text> </v-card-text>
    </v-card>
</template>

<script>
    export default {
        name: 'Point',
        props: {
            pointName: String,
            left: Number,
            top: Number
        },
    };
</script>

<style scoped>
    .point {
        position: absolute;
        border-radius: 50px;
        width: 30px;
        height: 30px;
    }
</style>