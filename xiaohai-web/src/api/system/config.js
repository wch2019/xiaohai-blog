import request from '@/utils/request'

const api = '/system/config'

// 查询系统配置
export function getConfig() {
  return request({
    url: api,
    method: 'get'
  })
}

// 新增系统配置
export function addConfig(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新系统配置
export function updateConfig(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}
