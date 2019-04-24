const menuRouter = [
  {
    path: '/index',
    menu: true,
    name: '用户管理',
    // 默认一个component，否则会跳转到空页面
    component: () => import('@/view/index'),
    children: [
      {
        path: '/index/user',
        name: '用户管理',
        components: {
          // 指定一个路由view，否则页面组件无法加载
          indexRouter: () => import('@/view/user/user')
        }
      },
      {
        path: '/index/charts',
        name: '用户统计',
        components: {
          indexRouter: () => import('@/view/user/charts')
        }
      }
    ]
  },
  {
    path: '/index',
    menu: true,
    name: '用户管理',
    // 默认一个component，否则会跳转到空页面
    component: () => import('@/view/index'),
    children: [
      {
        path: '/index/user1',
        name: '用户管理',
        components: {
          // 指定一个路由view，否则页面组件无法加载
          indexRouter: () => import('@/view/user/user')
        }
      },
      {
        path: '/index/charts1',
        name: '用户统计',
        components: {
          indexRouter: () => import('@/view/user/charts')
        }
      }
    ]
  }
]
export default menuRouter
