import request from '@/utils/request'

const api = '/system/dict-data'

// 查询字典数据列表数据
export function listDictData(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增字典数据
export function addDictData(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新字典数据
export function updateDictData(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询字典数据
export function getDictData(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 获取所有字典数据
export function dictAll() {
  return request({
    url: api + '/data/dictAll',
    method: 'get'
  })
}

// 获取指定字典数据
export function dictType(dictType) {
  return request({
    url: api + '/type/' + dictType,
    method: 'get'
  })
}

// 删除字典数据
export function delDictData(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}

