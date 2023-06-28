import Axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
// import { useStore } from 'vuex'
import useStore from '@/store/index'
import { getToken } from '@/utils/auth'
import router from '@/router'

const axios = Axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 20000 // 请求超时 20s
})
// 前置拦截器（发起请求之前的拦截）
axios.interceptors.request.use(
  (response) => {
    const newResponse = { ...response }
    // 在发送请求之前执行某些操作
    if (getToken()) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      newResponse.headers.authorization = getToken()
    }
    /**
     * 根据你的项目实际情况来对 config 做处理
     * 这里对 config 不做任何处理，直接返回
     */
    return response
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 后置拦截器（获取到响应时的拦截）
axios.interceptors.response.use(
  (response) => {
    /**
     * 根据你的项目实际情况来对 response 和 error 做处理
     * 这里对 response 和 error 不做任何处理，直接返回
     */
    // 登录异常
    if (response.data.code === 401) {
      // ElMessage.error(`Code: ${response.status}, Message: ${response.data.msg}`)
      ElMessageBox.confirm('继续使用，请重新登录', '提示', {
        confirmButtonText: '去登陆',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          window.open(
            `${import.meta.env.VITE_APP_BLOG_WEB_API}/#/login?show=${
              router.currentRoute.value.fullPath
            }`,
            '_self'
          )
        })
        .catch(() => {})
      const store = useStore()
      store.resetToken().then(() => {
        // window.location.reload()
      })
    }
    return response
  },
  (error) => {
    if (error.response && error.response.data) {
      const code = error.response.status
      const msg = error.response.data.message
      ElMessage.error(`Code: ${code}, Message: ${msg}`)
    } else {
      ElMessage.error(`${error}`)
    }
    return Promise.reject(error)
  }
)

export default axios
