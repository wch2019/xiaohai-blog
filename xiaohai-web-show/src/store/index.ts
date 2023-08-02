import { defineStore } from 'pinia'
import { getToken, removeToken } from '@/utils/auth'
import { getInfo, logout } from '@/api/user'
import { findShowBasic, friendLink, hotArticles, listTag } from '@/api/show'
import { image } from '@/utils/publicMethods'

const useStore = defineStore('user', {
  state: () => {
    return {
      token: getToken(),
      name: '',
      summary: '',
      avatar: '',
      userId: '',
      showBasic: '',
      website: {} as any,
      tags: [],
      friendLinkList: [],
      hotArticles: [],
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
        findShowBasic()
          .then((response) => {
            this.showBasic = response.data.data.basic
            this.website = response.data.data.website
            document.title = this.website.title
            // 获取指定 meta 标签
            const descriptionMeta = document.querySelector('meta[name="description"]')
            if (descriptionMeta) {
              // 修改 meta 标签的 content 属性
              descriptionMeta.setAttribute('content', this.website.description)
            }
            // 获取其他 meta 标签
            const keywordsMeta = document.querySelector('meta[name="keywords"]')
            if (keywordsMeta) {
              // 修改 meta 标签的 content 属性
              keywordsMeta.setAttribute('content', this.website.keywords)
            }
            // 获取现有的或新建一个 favicon 链接元素
            const faviconLink = document.querySelector('link[rel="icon"]') as HTMLLinkElement
            if (faviconLink) {
              faviconLink.href = image(this.website.logo+'?random=' + new Date().getTime())
            }
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // 获取标签信息
    getTags() {
      return new Promise<void>((resolve, reject) => {
        listTag()
          .then((response) => {
            this.tags = response.data.data
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // 获取友情链接
    getFriendLink() {
      return new Promise<void>((resolve, reject) => {
        friendLink()
          .then((response) => {
            this.friendLinkList = response.data.data
            resolve()
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    // 获取热门文章
    getHot() {
      return new Promise<void>((resolve, reject) => {
        hotArticles()
          .then((response) => {
            this.hotArticles = response.data.data.records
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
