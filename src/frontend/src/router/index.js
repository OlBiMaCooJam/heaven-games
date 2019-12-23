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
    path: '/games/:id',
    name: 'game',
    component: () => import('../views/Game.vue')
  },
  {
      path: '/games/:gameId/rooms',
    name: 'RoomList',
    component: () => import('../views/RoomList.vue')
  },
  {
      path: '/games/:gameId/rooms/:roomId',
    name: 'game',
    component: () => import('../views/Game.vue')
  },
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
        path: '/games/:gameId/rooms/:roomId/start',
        name: 'mafia',
        component: () => import('../views/Mafia.vue')
    },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
