import { defineStore } from 'pinia'
import {getToken, removeToken} from "@/utils/auth";
import {getInfo, logout} from "@/api/user";

 const useStore = defineStore('user', {
  state:()=>{
    return{
      token: getToken(),
      name: '',
      avatar: '',
      userId: '',
      count: 0,
      app:0
    }
  },
  actions:{
    // 获取用户登录信息
    getInfo() {
      return new Promise((resolve, reject) => {
        getInfo().then((response) => {
            const { data } = response.data
            if (data == null) {
              return reject('验证失败，请重新登录。')
            }
            const { nickName, avatar, id } = data.info
            this.name = nickName
            this.avatar = import.meta.env.VITE_APP_BASE_API_FILE + avatar
            this.userId = id
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
        logout().then(() => {
            removeToken()
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

