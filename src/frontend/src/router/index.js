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
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
