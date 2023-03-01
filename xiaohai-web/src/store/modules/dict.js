import { dictType, dictAll } from '@/api/system/dict/data'

const state = {
  dict: []
}
const mutations = {
  SET_DICT: (state, { key, value }) => {
    if (key !== null && key !== '') {
      state.dict.push({
        key: key,
        value: value
      })
    }
  },
  REMOVE_DICT: (state, key) => {
    try {
      for (let i = 0; i < state.dict.length; i++) {
        if (state.dict[i].key === key) {
          state.dict.splice(i, i)
          return true
        }
      }
      // eslint-disable-next-line no-empty
    } catch (e) {
    }
  },
  CLEAN_DICT: (state) => {
    state.dict = []
  },
  SET_DICT_ALL: (state, data) => {
    state.dict = data
  }
}

const actions = {
  // 设置字典
  setDict({ commit }, data) {
    commit('SET_DICT', data)
  },
  // 删除字典
  removeDict({ commit }, key) {
    commit('REMOVE_DICT', key)
  },
  // 清空字典
  cleanDict({ commit }) {
    commit('CLEAN_DICT')
  },
  // 重新获取字典
  setDictAll({ commit }) {
    return new Promise((resolve, reject) => {
      dictAll().then(response => {
        commit('SET_DICT_ALL', response.data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  }
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
