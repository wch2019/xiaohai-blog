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

// 更新系统通知（已读）
export function updateNotifications(data) {
  return request({
    url: api + '/' + data,
    method: 'put',
    data
  })
}

// 查询未读系统通知
export function getUnread(data) {
  return request({
    url: api + '/unread',
    method: 'get',
    params: data
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
