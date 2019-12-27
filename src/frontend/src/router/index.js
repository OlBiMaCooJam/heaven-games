import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('../views/Home.vue')
    },
    {
        path: '/:gameKind/rooms',
        name: 'rooms',
        component: () => import('../views/RoomList.vue'),
        props: true
    },
    {
        path: '/yutnori/:id',
        name: 'yutnori',
        component: () => import('../views/Yutnori.vue')
    },
    {
        path: '/:gameKind/rooms/:id',
        name: 'game',
        component: () => import('../views/Game.vue'),
        props: true
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/MINE/:id',
        name: 'minesweeper',
        component: () => import('../views/Minesweeper.vue')
    },
    {
        path: '/MAFIA/:id',
        name: 'mafia',
        component: () => import('../views/Mafia.vue'),
        props: true,
    },
    {
        path: '/draw/:id',
        name: 'draw',
        component: () => import('../views/Draw.vue'),
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
