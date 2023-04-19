// 路由配置文件
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import index from '@/views/index.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: index,
    children: [
      { path: '/', component: () => import('@/views/index.vue') },
      { path: '/axios', component: () => import('@/views/axios.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
