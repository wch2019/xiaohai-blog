import request from '@/utils/request'

const api = '/system/menu'

// 查询菜单列表数据
export function listMenu(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 新增菜单
export function addMenu(data) {
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
export function getMenu(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除菜单
export function delMenu(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
