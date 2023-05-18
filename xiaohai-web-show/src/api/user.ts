import request from '@/utils/axios'

// 退出
export const logout = () => {
  return request({
    url: '/logout',
    method: 'get'
  })
}
// 获取用户信息
export const userInfo = () => {
  return request({
    url: '/logout',
    method: 'get'
  })
}
