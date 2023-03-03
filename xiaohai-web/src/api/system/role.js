import request from '@/utils/request'

const api = '/system/role'

// 查询角色列表数据
export function listRole(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新角色
export function updateRole(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询角色
export function getRole(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除角色
export function delRole(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}

// 获取角色选择框列表
export function optionSelect() {
  return request({
    url: api + '/option-select',
    method: 'GET'
  })
}
