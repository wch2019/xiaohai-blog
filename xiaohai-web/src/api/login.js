import request from '@/utils/request'

// 登录
export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}
// 注册
export function register(data) {
  return request({
    url: '/register',
    method: 'post',
    data
  })
}
// 获取当前用户信息
export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}
// 退出
export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}
// 邮箱验证码
export function sendEmailCode(data) {
  return request({
    url: '/sendEmailCode',
    method: 'get',
    params: data
  })
}
