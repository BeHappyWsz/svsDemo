import Vue from 'vue'
import Router from 'vue-router'
import login from '@/view/login'
import wszRouter from './wsz'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login,
      meta: {
        title: '用户登录'
      }
    },
    ...wszRouter
  ]
})
export default router
