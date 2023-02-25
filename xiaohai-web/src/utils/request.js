import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_PROXY_TARGET + process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // 跨域请求时发送 Cookie
  timeout: 15000 // 请求超时时间,
})

// request 请求拦截器
service.interceptors.request.use(config => {
  // 在发送请求之前执行某些操作
  console.log(store.getters.token)
  if (store.getters.token) {
    // let each request carry token
    // ['X-Token'] is a custom headers key
    // please modify it according to the actual situation
    config.headers['authorization'] = getToken()
  }
  return config
},
error => {
  // do something with request error
  console.log(error) // for debug
  return Promise.reject(error)
}
)

// response 响应拦截器
service.interceptors.response.use(
  /**
   * 如果您想获取 http 信息，例如标头或状态，请返回响应 =>响应
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    // 如果自定义代码不是 200，则将其判断为错误。
    if (res.code !== 200) {
      Message({ message: res.msg || 'Error', type: 'error', duration: 5 * 1000 })
      return Promise.reject(new Error(res.message || 'Error'))
    } else {
      return res
    }
  },
  error => {
    if (error.response) {
      // 业务异常
      if (error.response.data.code === 400) {
        Message({ message: error.response.data.msg, type: 'error', duration: 5 * 1000 })
      }
      // 权限异常
      if (error.response.data.code === 403) {
        Message({ message: error.response.data.msg, type: 'error', duration: 5 * 1000 })
      }
      // 登录异常
      if (error.response.data.code === 401) {
        // to re-login
        MessageBox.confirm('您已注销，您可以取消以留在此页面，或重新登录', '确认注销', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      Message({ message: error.response.data.msg, type: 'error', duration: 5 * 1000 })
    } else {
      // 设置触发错误的请求时发生某些情况
      console.log('Error', error.message)
      Message({ message: error.message, type: 'error', duration: 5 * 1000 })
    }
    return Promise.reject(error)
  }
)

export default service
