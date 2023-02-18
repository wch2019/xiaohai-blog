import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function register(data) {
  return request({
    url: '/register',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function sendEmailCode(data) {
  return request({
    url: '/sendEmailCode',
    method: 'get',
    params: data
  })
}
