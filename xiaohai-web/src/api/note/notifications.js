import request from '@/utils/request'

const api = '/notifications'

// 查询标签列表数据
export function listTags(data) {
  return request({
    url: api,
    method: 'get',
    params: data
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

// 查询未读系统通知
export function getUnread() {
  return request({
    url: api + '/unread',
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

// 获取标签选择列表
export function optionSelectTags() {
  return request({
    url: api + '/option-select',
    method: 'GET'
  })
}
