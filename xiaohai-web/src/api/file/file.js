import request from '@/utils/request'

const api = '/file'

// 文件列表
export function getFile(data) {
  return request({
    url: api,
    method: 'get',
    params: data
  })
}

// markdown图片列表
export function markdownImage() {
  return request({
    url: api + '/markdownImage',
    method: 'get'
  })
}

// 头像上传
export function uploadAvatar(data) {
  return request({
    url: api + '/avatar',
    method: 'post',
    data
  })
}

// markdown图片上传
export function uploadImage(data) {
  return request({
    url: api + '/image',
    method: 'post',
    data
  })
}

//文件删除
export function delFile(data) {
  return request({
    url: api,
    method: 'delete',
    params: { path: data }
  })
}
