const wszRouter = [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '用户登录'
    },
    component: () => import('@/view/login')
  }
]
export default wszRouter
