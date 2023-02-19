import request from '@/utils/request'

const api = '/system/role'

// 查询角色列表数据
export function findListByPage(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 新增角色
export function create(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新角色
export function update(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询角色
export function findById(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 删除角色
export function remove(data) {
  return request({
    url: api,
    method: 'delete',
    data
  })
}
