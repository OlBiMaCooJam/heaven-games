<template>
    <div>
        <div class="block">
            <p class="digit">{{ seconds | two_digits }}</p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Timer",
        mounted: function () {
            window.setInterval(() => {
                this.now = Math.trunc((new Date()).getTime() / 1000);
            }, 1000);
        },
        props: {
            date: Number,
            roomId: Number,
            client: {},
            day: Boolean,
            occupation: String,
            dialog: Boolean
        },

        data() {
            return {
                now: Math.trunc((new Date()).getTime() / 1000),
                limit: Math.trunc((new Date().getTime() / 1000) + Number(this.date)),
            }
        },

        computed: {
            seconds() {
                const time = this.limit - this.now;
                if (this.day) {
                    if (time === 0) {
                        //TODO: 모든 타이머 마다 send ??
                        this.client.send('/app/rooms/' + this.roomId + '/vote');
                    }
                } else {
                    if (time === 15 && this.occupation !== 'SOLDIER') {
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