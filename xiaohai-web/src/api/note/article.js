import request from '@/utils/request'

const api = '/note/article'

// 查询分类列表数据
export function listArticle(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增分类
export function addArticle(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新分类
export function updateArticle(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询分类
export function getArticle(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除分类
export function delArticle(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
// 获取随机图片必应
export function getBingWallpaper() {
  return request({
    url: api + '/bing-wallpaper',
    method: 'get'
  })
}
