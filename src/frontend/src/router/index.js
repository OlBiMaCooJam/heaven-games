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
    path: '/games/:id',
    name: 'game',
    component: () => import('../views/Game.vue')
  },
  {
    path: '/rooms',
    name: 'RoomList',
    component: () => import('../views/RoomList.vue')
  },
  {
    path: '/rooms/:id',
    name: 'game',
    component: () => import('../views/Game.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
