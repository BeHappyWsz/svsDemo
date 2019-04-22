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
    component: () => import('@/view/index'),
    children: [
      {
        path: '/user',
        name: '用户管理',
        children: [
          {
            path: '/index/user01',
            name: '用户管理',
            components: {
              indexRouter: () => import('@/view/user/user')
            }
          },
          {
            path: '/index/user02',
            name: '用户统计',
            components: () => import('@/view/user/user')
          }
        ]
      },
      {
        path: '/',
        name: '用户管理1',
        children: [
          {
            path: '/index/user11',
            name: 'user11',
            component: () => import('@/view/user/user')
          },
          {
            path: '/index/user12',
            name: 'user12',
            component: () => import('@/view/user/user')
          }
        ]
      }
    ]
  }
]
export default wszRouter
