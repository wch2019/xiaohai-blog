import { defineStore } from 'pinia'
import { getToken, removeToken } from '@/utils/auth'
import { getInfo, logout } from '@/api/user'

const useStore = defineStore('user', {
  state: () => {
    return {
      token: getToken(),
      name: '',
      summary: '',
      avatar: '',
      userId: '',
      count: 0,
      app: 0
    }
  },
  actions: {
    // 获取用户登录信息
    getInfo() {
      return new Promise((resolve, reject) => {
        getInfo()
          .then((response) => {
            const { data } = response.data
            if (data == null) {
              return reject('验证失败，请重新登录。')
            }
            const { username, nickName, avatar, id, summary } = data.info
            this.name = nickName || username
            this.avatar = import.meta.env.VITE_APP_BASE_API_FILE + avatar
            this.userId = id
            this.summary = summary
            resolve(data)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // 退出系统
    logOut() {
      return new Promise<void>((resolve, reject) => {
        logout()
          .then(() => {
            removeToken()
            window.location.reload()
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // remove token
    resetToken() {
      return new Promise<void>((resolve) => {
        removeToken()
        resolve()
      })
    }
  }
})
export default useStore
