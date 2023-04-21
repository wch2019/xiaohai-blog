// 路由配置文件
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    children: [
      { path: '/', component: () => import('@/views/home/index.vue') },
      { path: '/category', component: () => import('@/views/category/index.vue') },
      { path: '/tags', component: () => import('@/views/tags/index.vue') },
      { path: '/back', component: () => import('@/views/back/index.vue') },
      { path: '/message', component: () => import('@/views/message/index.vue') },
      { path: '/links', component: () => import('@/views/links/index.vue') },
      { path: '/about', component: () => import('@/views/about/index.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
