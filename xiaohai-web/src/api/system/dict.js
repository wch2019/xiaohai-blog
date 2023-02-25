import request from '@/utils/request'

const api = '/system/dict-type'

// 查询字典类型列表数据
export function listDictType(data) {
  return request({
    url: api,
    method: 'get',
    data
  })
}

// 新增字典类型
export function addDictType(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新字典类型
export function updateDictType(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询字典类型
export function getDictType(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除字典类型
export function delDictType(data) {
  return request({
    url: api,
    method: 'delete',
    data
  })
}

export function refreshDict(data) {
  return request({
    url: api + '/refresh-dict',
    method: 'delete',
    data
  })
}

export function optionSelect() {
  return request({
    url: api + '/option-select',
    method: 'GET'
  })
}

