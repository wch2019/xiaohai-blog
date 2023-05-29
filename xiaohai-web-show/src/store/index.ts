import { createStore } from 'vuex'
import { getToken, removeToken } from '@/utils/auth'
import { logout } from '@/api/user'

const defaultState = {
  count: 0,
  username: '',
  token: getToken(),
  name: 'aaa',
  avatar: ''
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
    }
  },
  getters: {
    double(state: typeof defaultState) {
      return 2 * state.count
    }
  }
})
