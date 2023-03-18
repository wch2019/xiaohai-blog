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
