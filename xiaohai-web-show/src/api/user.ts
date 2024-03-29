import request from '@/utils/axios'

// 退出
export const logout = () => {
  return request({
    url: '/logout',
    method: 'get'
  })
}
// 获取用户信息
export const getInfo = () => {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}
// 新增评论
export const addComment = (data: any) => {
  return request({
    url: '/note/comment',
    method: 'post',
    data
  })
}
