import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/rooms/:gameKind',
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
    path: '/rooms/:id/:gameKind',
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
    path: '/minesweeper',
    name: 'minesweeper',
    component: () => import('../views/Minesweeper.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
