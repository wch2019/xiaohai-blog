import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

import { parseTime } from '@/utils/index'

// 全局方法挂载
Vue.prototype.parseTime = parseTime

// 分页组件
import Pagination from '@/components/Pagination'
import DictTag from '@/components/DictTag'

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('DictTag', DictTag)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock')
//   mockXHR()
// }
import VueParticles from 'vue-particles'
Vue.use(VueParticles)

// set ElementUI lang to EN
// Vue.use(ElementUI, { locale })
// 如果想要中文版 element-ui，按如下方式声明
Vue.use(ElementUI)

// mavonEditor  www.mavoneditor.com
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

// 高亮语法
import hljs from 'highlight.js'
import 'highlight.js/styles/atom-one-light.css'
Vue.directive('highlight', function(el) {
  hljs.configure({ useBR: true })
  const blocks = el.querySelectorAll('pre')
  blocks.forEach((block) => {
    hljs.highlightBlock(block)
  })
})

// 图片查看组件
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
Vue.use(Viewer)
// json展示插件
import JsonViewer from 'vue-json-viewer'
Vue.use(JsonViewer)
// Jsonp
import { VueJsonp } from 'vue-jsonp'
Vue.use(VueJsonp)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
