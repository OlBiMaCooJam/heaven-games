<template>
    <v-app id="app">
        <div id="nav">
            <router-link id="home" to="/">
                <img id="btn-home" src="./assets/Logo.jpg">
            </router-link>
            <span id="title">미 니 게 임 천 국</span>
        </div>
        <router-view/>
    </v-app>
</template>

<style lang="scss">
    @font-face {
        font-family: 'rose';
        src: url('assets/a장미다방.otf')
    }

    #app {
        font-family: 'rose';
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        background-image: url('assets/Background.png');
        height: 100%;
    }

    #nav {
        padding: 30px;
    }

    #btn-home {
        width: 50px;
    }

    #home {
        display: unset;
        float: left;
    }

    #title {
        font-size: 5em;
        line-height: 1;
        display: inline-block;
        margin-left: -50px;
    }
</style>
