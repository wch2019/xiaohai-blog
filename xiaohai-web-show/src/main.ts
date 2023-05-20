import { createApp } from 'vue'
// 初始化系统样式
import './style/index.scss'
import ElementPlus from 'element-plus'
import VMdPreview from '@kangc/v-md-editor/lib/preview'
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js'
import hljs from 'highlight.js'
// undraw
import UndrawUi from 'undraw-ui'
import App from './App.vue'
// 挂载路由配置
import router from './router/index'
// 挂载 Vuex 配置
import store from './store/index'
// 挂载 Element Plus
import 'element-plus/dist/index.css'
// 隐藏元素
import 'element-plus/theme-chalk/display.css'
// 注册所有图标
import 'virtual:svg-icons-register' // 引入注册脚本
import SvgIcon from '@/components/SvgIcon/index.vue'
import elementIcons from '@/components/SvgIcon/svgicon'
// v-md-editor 预览组件
import '@kangc/v-md-editor/lib/style/preview.css'
import '@kangc/v-md-editor/lib/theme/style/github.css'
// undraw
import 'undraw-ui/dist/style.css'

// highlightjs
VMdPreview.use(githubTheme, {
  Hljs: hljs
})

const app = createApp(App)

app.use(router)
app.use(store)
app.use(ElementPlus)
app.component('SvgIcon', SvgIcon)
app.use(elementIcons)
app.use(VMdPreview)
app.use(UndrawUi)

app.mount('#app')
