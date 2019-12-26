<template>
    <div>
        <div class="block">
            <p class="digit">{{ seconds | two_digits }}</p>
        </div>
    </div>
</template>

<script>
    import {eventBus} from "../main";

    export default {
        name: "Timer",
        mounted: function () {
            window.setInterval(() => {
                this.now = Math.trunc((new Date()).getTime() / 1000);
            }, 1000);
            eventBus.$on(`initTime`, () => this.limit = Math.trunc((new Date().getTime() / 1000) + Number(this.date)));
        },
        props: {
            date: Number,
            roomId: String,
            client: {},
            day: Boolean,
            occupation: String,
            dialog: Boolean
        },

        data() {
            return {
                now: Math.trunc((new Date()).getTime() / 1000),
                limit: Math.trunc((new Date().getTime() / 1000) + Number(this.date))
            }
        },

        computed: {
            seconds() {
                const time = this.limit - this.now;
                if (this.day) {
                    window.console.log('낮');
                    if (time === 0) {
                        this.client.send('/app/rooms/' + this.roomId + '/vote');
                    }
                } else {
                    window.console.log('밤');
                    window.console.log(this.dialog);
                    window.console.log(`time:` + time + `occupation` + this.occupation);
                    if (time === 0 && this.occupation !== 'SOLDIER' && this.occupation !== 'CITIZEN') {
                        window.console.log('ㅁㄴㅇ');
                        this.client.send('/app/rooms/' + this.roomId + '/vote');
                    }
                }

                return time > 0 ? time : 0;
            },
        },
    }

</script>

<style scoped>
    @import url(https://fonts.googleapis.com/css?family=Roboto+Condensed:400|Roboto:100);

    .block {
        display: flex;
        flex-direction: column;
        margin: 20px;
    }

    .digit {
        color: #ecf0f1;
        font-size: 80px;
        font-weight: 100;
        font-family: 'Roboto', serif;
        margin: 10px;
        text-align: center;
    }
</style>