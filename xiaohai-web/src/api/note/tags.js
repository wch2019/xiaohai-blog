import request from '@/utils/request'

const api = '/note/tags'

// 查询标签列表数据
export function listTags(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增标签
export function addTags(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新标签
export function updateTags(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询标签
export function getTags(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除标签
export function delTags(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
