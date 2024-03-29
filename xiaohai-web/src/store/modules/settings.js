import defaultSettings from '@/settings'

const { sideTheme, showSettings, fixedHeader, sidebarLogo } = defaultSettings

const state = {
  theme: '#409EFF',
  sideTheme: sideTheme || sideTheme,
  showSettings: showSettings,
  fixedHeader: fixedHeader,
  sidebarLogo: sidebarLogo
}

const mutations = {
  CHANGE_SETTING: (state, { key, value }) => {
    // eslint-disable-next-line no-prototype-builtins
    if (state.hasOwnProperty(key)) {
      state[key] = value
    }
  }
}

const actions = {
  // 修改布局设置
  changeSetting({ commit }, data) {
    commit('CHANGE_SETTING', data)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

