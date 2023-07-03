import request from '@/utils/request'

const api = '/note/article-like'

// 查询喜欢列表数据
export function listLikes(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 删除喜欢
export function delLike(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}

