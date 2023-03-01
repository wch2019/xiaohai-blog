import request from '@/utils/request'

const api = '/system/dict-type'

// 查询字典类型列表数据
export function listDictType(data) {
  return request({
    url: api,
    method: 'get',
    params: data
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
    url: api + '/' + data,
    method: 'delete',
  })
}
// 刷新字典缓存
export function refreshDict(data) {
  return request({
    url: api + '/refresh-dict',
    method: 'delete',
    data
  })
}
// 获取字典选择框列表
export function optionSelect() {
  return request({
    url: api + '/option-select',
    method: 'GET'
  })
}

