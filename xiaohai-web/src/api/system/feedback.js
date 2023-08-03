import request from '@/utils/request'

const api = '/system/feedback'

// 查询用户反馈列表数据
export function listFeedback(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增用户反馈
export function addFeedback(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新用户反馈
export function updateFeedback(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询用户反馈
export function getFeedback(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除用户反馈
export function delFeedback(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
