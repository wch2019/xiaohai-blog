import request from '@/utils/axios'

const api = '/home/show'
const comment = '/note'

// 标签
export const listTag = () => {
  return request({
    url: `${api}/tags`,
    method: 'get'
  })
}
// 分类
export const listCategory = () => {
  return request({
    url: `${api}/category`,
    method: 'get'
  })
}
// 查询展示文章表列表数据
export const listArticles = (query: any) => {
  return request({
    url: `${api}/articles`,
    method: 'get',
    params: query
  })
}
// 文章详情
export const article = (id: any) => {
  return request({
    url: `${api}/article/${id}`,
    method: 'get'
  })
}
// 归档列表
export const listBack = (query: any) => {
  return request({
    url: `${api}/back`,
    method: 'get',
    params: query
  })
}
// 查询文章
export const getSearch = (keywords: any) => {
  return request({
    url: `${api}/search`,
    method: 'get',
    params: { keywords }
  })
}

// 文章id查询评论
export const getComment = (id: any) => {
  return request({
    url: `${api}/article/comment/${id}`,
    method: 'get'
  })
}
// 新增评论
export const addComment = (data: any) => {
  return request({
    url: `${comment}/comment`,
    method: 'post',
    data
  })
}
// 站点信息展示
export const findShowBasic = () => {
  return request({
    url: `${api}/findShowBasic`,
    method: 'get'
  })
}
export const articleLike = (data: any) => {
  return request({
    url: `${comment}/article-like`,
    method: 'post',
    data
  })
}
// 友情链接
export const friendLink = () => {
  return request({
    url: `${api}/friendLink`,
    method: 'get'
  })
}
// 删除评论
export const deleteComment = (ids: any) => {
  return request({
    url: `${comment}/comment/${ids}`,
    method: 'delete'
  })
}
