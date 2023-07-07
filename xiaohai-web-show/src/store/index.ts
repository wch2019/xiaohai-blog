import { defineStore } from 'pinia'
import { getToken, removeToken } from '@/utils/auth'
import { getInfo, logout } from '@/api/user'
import {findShowBasic, friendLink, hotArticles, listTag} from '@/api/show'
const useStore = defineStore('user', {
  state: () => {
    return {
      token: getToken(),
      name: '',
      summary: '',
      avatar: '',
      userId: '',
      showBasic:'',
      tags:[],
      friendLinkList:[],
      hotArticles:[],
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
    // 获取系统信息
    getSystem() {
      return new Promise<void>((resolve, reject) => {
        findShowBasic().then((response) => {
          this.showBasic = response.data.data
          resolve()
        }).catch((error) => {
          reject(error)
        })
      })
    },
    // 获取标签信息
    getTags() {
      return new Promise<void>((resolve, reject) => {
        listTag().then((response) => {
          this.tags = response.data.data
          resolve()
        }).catch((error) => {
          reject(error)
        })
      })
    },
    // 获取友情链接
    getFriendLink() {
      return new Promise<void>((resolve, reject) => {
        friendLink().then((response) => {
          this.friendLinkList = response.data.data
          resolve()
        }).catch((error) => {
          reject(error)
        })
      })
    },
    // 获取热门文章
    getHot() {
      return new Promise<void>((resolve, reject) => {
        hotArticles().then((response) => {
          this.hotArticles = response.data.data.records
          resolve()
        }).catch((error) => {
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
