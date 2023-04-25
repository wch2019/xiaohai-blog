// 路由配置文件
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    children: [
      { path: '/', component: () => import('@/views/home/HomeIndex.vue') },
      { path: '/category', component: () => import('@/views/category/CategoryIndex.vue') },
      { path: '/tags', component: () => import('@/views/tags/TagsIndex.vue') },
      { path: '/back', component: () => import('@/views/back/BackIndex.vue') },
      { path: '/message', component: () => import('@/views/message/MessageIndex.vue') },
      { path: '/links', component: () => import('@/views/links/LinksIndex.vue') },
      { path: '/about', component: () => import('@/views/about/AboutIndex.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
