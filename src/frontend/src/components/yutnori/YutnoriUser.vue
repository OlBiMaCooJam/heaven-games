<template>
    <v-container>
        <div>
            <v-img class="profile-image margin-0-auto"
                   src="https://middle.pngfans.com/20190620/er/avatar-icon-png-computer-icons-avatar-clipart-e1c00a5950d1849e.jpg"></v-img>
            <p>{{name}}</p>
        </div>
        <StandBy :count="standby" :pieceColor="color" :selected="isSelected" :turnColor="turnColor"
                 @chooseSrcPoint="chooseSrcPoint" class="margin-0-auto"></StandBy>
    </v-container>
</template>

<script>
    import StandBy from "./StandBy"

    export default {
        name: 'YutnoriUser',
        components: {StandBy},
        props: {
            name: String,
            color: String,
            standby: Number,
            selectedPoint: String,
            turnColor: String
        },
        computed: {
          isSelected() {
              return this.selectedPoint == 'STANDBY';
          }
        },
        methods: {
            chooseSrcPoint(pointName, canClick) {
                this.$emit('chooseSrcPoint', pointName, canClick)
            }
        }
    }
</script>

<style scoped>
    .profile-image {
        max-height: 100px;
        max-width: 100px;
    }

    .margin-0-auto {
        margin: 0 auto;
    }
</style>