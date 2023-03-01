import request from '@/utils/request'

const api = '/system/user'

// 查询用户表列表数据
export function listUser(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 新增用户表
export function addUser(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新用户表
export function updateUser(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询用户表
export function getUser(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除用户表
export function delUser(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
