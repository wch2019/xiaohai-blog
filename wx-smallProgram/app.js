// app.js
import towxml from '/towxml/index';

App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  // 引入`towxml3.0`解析方法
  towxml:towxml,
  globalData: {
    userInfo: null,
    // baseURL:'https://nas.dotcode.top:60002',
    baseURL:'https://www.dotcode.top',
    baseAPI:'/api',
    // fileUrl:'https://nas.dotcode.top:60002/api/document/upload',
    fileUrl:'https://www.dotcode.top/api/document/upload'
  }
})
