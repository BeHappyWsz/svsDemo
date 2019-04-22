import Vue from 'vue'
import Router from 'vue-router'
import wszRouter from './wsz'

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
    ...wszRouter
  ]
})
export default router
