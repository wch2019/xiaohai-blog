import { asyncRoutes1, constantRoutes } from '@/router'
import { filterAsyncRouter } from '@/utils/permission'
const state = {
  routes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit, rootState }) {
    return new Promise(resolve => {
      // const accessedRoutes = asyncRoutes1
      const accessedRoutes = filterAsyncRouter(rootState.user.menu)
      accessedRoutes.push({ path: '*', redirect: '/404', hidden: true })
      commit('SET_ROUTES', accessedRoutes)
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
