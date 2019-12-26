<template>
    <v-layout row>
        <Point v-for='(pointName, index) in row.names' :key="pointName"
               :pointName="pointName" :left="row.left + row.interval * index" :top="row.top"/>
    </v-layout>
</template>

<script>
    import Point from "./Point"

    export default {
        name: "Row",
        components: {Point},
        props: {
            row: {
                names: Array,
                left: Number,
                top: Number,
                interval: Number
            }
        }
    }
</script>