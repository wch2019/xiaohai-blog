import request from '@/utils/request'

const api = '/note/article'

// 查询文章列表数据
export function listArticle(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增草稿文章
export function addDraftArticle(data) {
  return request({
    url: api + '/add-draft',
    method: 'post',
    data
  })
}

// 更新草稿文章
export function updateDraftArticle(data) {
  return request({
    url: api + '/update-draft',
    method: 'put',
    data
  })
}

// 新增发布文章
export function addArticle(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// 更新发布文章
export function updateArticle(data) {
  return request({
    url: api,
    method: 'put',
    data
  })
}

// id查询文章
export function getArticle(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除文章
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

// id更新发布状态
export function updatePush(id) {
  return request({
    url: api + '/push/' + id,
    method: 'put'
  })
}

// id更新置顶状态
export function updateTop(id) {
  return request({
    url: api + '/top/' + id,
    method: 'put'
  })
}
// 抓取文章
export function reptileArticle(data) {
  return request({
    url: api+"/reptile-article",
    method: 'post',
    data
  })
}
