import Layout from '@/layout/index'
import ParentView from '@/components/ParentView'

// 遍历后台传来的路由字符串，转换为组件对象
export function filterAsyncRouter(asyncRouterMap) {
  return asyncRouterMap.filter(route => {
    if (route.component) {
      // Layout ParentView 组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else if (route.component === 'ParentView') {
        route.component = ParentView
      } else {
        // 添加字典不展示页面
        if (route.path === 'dictData/:id') {
          route.hidden = true
        }
        // 写作中心不展示页面
        if (route.path === 'edit') {
          route.hidden = true
        }
        route.component = loadView(route.component)
      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }

    return true
  })
}

export const loadView = (view) => {
  if (process.env.NODE_ENV === 'development') {
    return (resolve) => require([`@/views/${view}`], resolve)
  } else {
    // 使用 import 实现生产环境的路由懒加载
    // return () => import(`@/views${view}`)
    return (resolve) => require([`@/views/${view}`], resolve)
  }
}
