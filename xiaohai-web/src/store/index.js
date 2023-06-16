import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import dict from './modules/dict'
import permission from './modules/permission'
import createPersistedState from 'vuex-persistedstate'
import Cookies from 'js-cookie'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    dict,
    user,
    permission
  },
  getters,
  plugins: [
    createPersistedState({
      // 键名
      key: process.env.VUE_APP_USERIFNO_KEY,
      storage: {
        getItem: key => Cookies.get(key),
        setItem: (key, value) => Cookies.set(key, value),
        removeItem: key => Cookies.remove(key)
      },
      // 设置存储
      setState: (key, value) => {
        Cookies.set(key, value)
      },
      // 恢复存储
      getState: (key) => {
        return Cookies.getJSON(key)
      },
      // 要存储的state，默认全部
      reducer: state => {
        const { user } = state
        return {
          user
        }
      }
    })
  ]
})

export default store
