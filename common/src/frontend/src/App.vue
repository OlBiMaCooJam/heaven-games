<template>
    <v-app id="app">
        <div id="nav">
            <router-link to="/">home</router-link>
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
        font-family: 'Avenir', Helvetica, Arial, sans-serif, 'rose';
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        background-image: url('assets/Background.png');
        height: 1024px;
    }

    #nav {
        padding: 30px;

        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }
</style>
