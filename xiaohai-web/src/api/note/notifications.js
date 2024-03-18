import request from '@/utils/request'

const api = '/notifications'

// 查询系统通知列表数据
export function listNotifications(data) {
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
