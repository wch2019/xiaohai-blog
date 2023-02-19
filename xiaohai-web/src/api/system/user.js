import request from '@/utils/request'

const api = '/system/user'

// 查询用户表列表数据
export function findListByPage(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 新增用户表
export function create(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新用户表
export function update(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询用户表
export function findById(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 删除用户表
export function remove(data) {
  return request({
    url: api,
    method: 'delete',
    data
  })
}
