import WxRequest from 'mina-request'
const instance = new WxRequest({
  baseURL: getApp().globalData.baseURL + getApp().globalData.baseAPI,
  timeout: 15000,
  isLoading: false // 不使用默认 loading
})

// 添加请求拦截器
instance.interceptors.request = (config) => {
  const token = wx.getStorageSync('token')
  if (token) {
    config.header['token'] = token
  }
  return config
}

// 添加响应拦截器
instance.interceptors.response = (response) => {
  const { isSuccess, data } = response

  // 如果 isSuccess 为 false，说明执行了 fail 回调函数
  // 这时候就说明网络异常，需要给用户提示网络异常
  if (!isSuccess) {
    wx.showToast({
      title: '网络异常，请稍后重试 !'
    })

    return response
  }
  return data
}


export default instance