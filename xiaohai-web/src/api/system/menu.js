import request from '@/utils/request'

const api = '/system/menu'

// 查询菜单列表数据
export function fetchMenu(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 新增菜单
export function createMenu(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新菜单
export function updateMenu(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询菜单
export function findById(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 删除菜单
export function removeMenu(data) {
  return request({
    url: api,
    method: 'delete',
    data
  })
}
