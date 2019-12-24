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
                if (time < 0) {
                    this.client.send('/app/mafia/' + this.roomId + '/vote');
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