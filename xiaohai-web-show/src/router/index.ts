// 路由配置文件
import { createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw } from 'vue-router'

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
      { path: '/about', component: () => import('@/views/about/AboutIndex.vue') },
      { path: '/article/:id', component: () => import('@/views/article/ArticleIndex.vue') }
      // { path: '/chat', component: () => import('@/views/chat/ChatIndex.vue') }
    ]
  }
]

const router = createRouter({
  // history: createWebHashHistory(),
  history: createWebHistory(), // 去掉地址栏里的#
  routes
})

export default router
