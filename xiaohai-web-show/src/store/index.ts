import { createStore } from 'vuex'
import { getToken } from '@/utils/auth'

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
    }
  },
  getters: {
    double(state: typeof defaultState) {
      return 2 * state.count
    }
  }
})
