import Vue from 'vue'
import Router from 'vue-router'
import wszRouter from './wsz'
import menuRouter from './menu'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: '/',
      meta: {
        title: '用户登录'
      },
      redirect: '/login'
    },
    ...wszRouter,
    ...menuRouter
  ]
})
export default router
