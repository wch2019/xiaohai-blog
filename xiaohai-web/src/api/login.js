import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
export function getBing() {
  return request({
    url: 'https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=8',
    method: 'get'
  })
}
export function sendEmailCode(data) {
  return request({
    url: '/sendEmailCode',
    method: 'get',
    params: data,
    timeout: 20000
  })
}
