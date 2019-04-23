const wszRouter = [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '用户登录'
    },
    component: () => import('@/login')
  },
  {
    path: '/register',
    name: 'register',
    meta: {
      title: '用户注册'
    },
    component: () => import('@/register')
  },
  {
    path: '/index',
    name: 'index',
    meta: {
      title: '首页'
    },
    component: () => import('@/view/index')
  }
]
export default wszRouter
