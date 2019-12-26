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
        path: '/:gameTitle/rooms/:id',
        name: 'game',
        component: () => import('../views/Game.vue'),
        props: true,
    },
    {
        path: '/:gameTitle/rooms',
        name: 'RoomList',
        component: () => import('../views/RoomList.vue'),
        props: true,
    },
    {
        path: '/rooms/:id/yutnori',
        name: 'Yutnori',
        component: () => import('../views/Yutnori.vue')
    },
    // {
    //   path: '/rooms/:id',
    //   name: 'game',
    //   component: () => import('../views/Game.vue')
    // },
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/minesweeper',
        name: 'minesweeper',
        component: () => import('../views/Minesweeper.vue')
    },
    {
        path: '/MAFIA/rooms/:id/start',
        name: 'mafia',
        component: () => import('../views/Mafia.vue'),
        props: true,
    },
    {
        path: '/draw',
        name: 'draw',
        component: () => import('../views/Draw.vue'),
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router
