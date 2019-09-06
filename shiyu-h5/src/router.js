import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/contact',
      name: 'contact',
      component: () => import('./views/Contact')
    },
    {
      path: '/discover',
      name: 'discover',
      component: () => import('./views/Discover')
    },
    {
      path: '/me',
      name: 'me',
      component: () => import('./views/Me')
    }
  ]
})
