import request from '@/utils/request'

const api = '/system/log'

// 查询日志列表数据
export function listLog(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// id查询日志
export function getLog(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除日志
export function delLog(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
// 删除全部日志
export function delLogAll() {
  return request({
    url: api + '/all',
    method: 'delete'
  })
}

