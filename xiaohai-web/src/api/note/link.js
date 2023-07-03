import request from '@/utils/request'

const api = '/note/friend-link'

// 查询友链列表数据
export function listLink(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增友链
export function addLink(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新友链
export function updateLink(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询友链
export function getLink(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除友链
export function delLink(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
