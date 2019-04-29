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
        name: '用户信息',
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
    name: '文件管理',
    // 默认一个component，否则会跳转到空页面
    component: () => import('@/view/index'),
    children: [
      {
        path: '/index/file',
        name: '文件上传',
        components: {
          // 指定一个路由view，否则页面组件无法加载
          indexRouter: () => import('@/view/user/file')
        }
      }
    ]
  }
]
export default menuRouter
