import { createApp } from 'vue'
// 初始化系统样式
import './style/index.scss'
import ElementPlus from 'element-plus'
import App from './App.vue'
// 挂载路由配置
import router from './router/index'
// 挂载 Vuex 配置
import store from './store/index'
// 挂载 Element Plus
import 'element-plus/dist/index.css'
// 注册所有图标
// eslint-disable-next-line import/order
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)

app.use(router)
app.use(store)
app.use(ElementPlus)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount('#app')
