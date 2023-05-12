import request from '@/utils/axios'

const api = '/home/show'

// 查询文章列表数据
// export function listArticle(data: any) {
//   return request({
//     url: api,
//     method: 'get',
//     params: data
//   })
// }
export const listTag = () => {
  return request({
    url: `${api}/tags`,
    method: 'get'
  })
}
export const listCategory = () => {
  return request({
    url: `${api}/category`,
    method: 'get'
  })
}
export const listArticles = (query: any) => {
  return request({
    url: `${api}/articles`,
    method: 'get',
    params: query
  })
}
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
