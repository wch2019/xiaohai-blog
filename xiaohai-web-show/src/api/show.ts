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
export const listArticles = (data: any) => {
  return request({
    url: `${api}/articles`,
    method: 'get',
    data
  })
}
