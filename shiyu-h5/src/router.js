import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import Home from './views/Home'
import { localRead } from '@/utils'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('./views/Login')
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
    },
    {
      path: '/avatar',
      name: 'avatar',
      component: () => import('./views/Avatar')
    }
  ]
})

NProgress.configure({ showSpinner: false })

router.beforeEach((to, from, next) => {
  NProgress.start()

  const token = localRead('token')
  if (to.path === '/login' || token) {
    next()
  } else {
    next('/login')
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
