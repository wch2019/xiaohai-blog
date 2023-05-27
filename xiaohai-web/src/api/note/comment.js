import request from '@/utils/request'

const api = '/note/comment'

// 查询留言列表数据
export function listComment(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// 新增留言
export function addComment(data) {
  return request({
    url: api,
    method: 'post',
    data
  })
}

// id查询留言
export function getComment(data) {
  return request({
    url: api + '/' + data,
    method: 'get'
  })
}

// 删除留言
export function delComment(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
