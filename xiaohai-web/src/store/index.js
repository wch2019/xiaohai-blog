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
    // createPersistedState({
    //   storage: window.sessionStorage
    // })
  ]
})

export default store
