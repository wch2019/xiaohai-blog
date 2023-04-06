import request from '@/utils/request'

const api = '/note/category'

// 查询分类列表数据
export function listCategory(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增分类
export function addCategory(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新分类
export function updateCategory(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询分类
export function getCategory(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除分类
export function delCategory(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
// 获取分类选择列表
export function optionSelectCategory() {
  return request({
    url: api + '/option-select',
    method: 'GET'
  })
}
