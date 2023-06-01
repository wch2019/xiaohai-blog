import { createStore } from 'vuex'
import { getToken, removeToken } from '@/utils/auth'
import { logout, getInfo } from '@/api/user'

const defaultState = {
  token: getToken(),
  name: '',
  avatar: '',
  userId: '',
  count: 0
}

// Create a new store instance.
export default createStore({
  state() {
    return defaultState
  },
  mutations: {
    increment(state: typeof defaultState) {
      state.count++
    }
  },
  actions: {
    increment(context) {
      context.commit('increment')
    },
    // 获取用户登录信息
    getInfo() {
      return new Promise((resolve, reject) => {
        getInfo()
          .then((response) => {
            const { data } = response.data
            console.log(data)
            if (data == null) {
              return reject('验证失败，请重新登录。')
            }
            const { nickName, avatar, id } = data.info
            defaultState.name = nickName
            defaultState.avatar = import.meta.env.VITE_APP_BASE_API_FILE + avatar
            defaultState.userId = id
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
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // remove token
    resetToken() {
      console.log('vvvvvv')
      return new Promise<void>((resolve) => {
        removeToken()
        resolve()
      })
    }
  },
  getters: {
    double(state: typeof defaultState) {
      return 2 * state.count
    }
  }
})
