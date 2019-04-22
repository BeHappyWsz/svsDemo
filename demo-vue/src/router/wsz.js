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
  }
]
export default wszRouter
