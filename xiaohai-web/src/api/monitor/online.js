import request from '@/utils/request'

const api = '/monitor/online'

// 在线用户
export function listOnLineUser(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}
// 退出用户
export function kickOut(id) {
  return request({
    url: api + '/' + id,
    method: 'delete'
  })
}
