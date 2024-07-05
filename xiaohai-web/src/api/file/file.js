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
export function markdownImage(data) {
  return request({
    url: api + '/markdownImage',
    method: 'get',
    params: data
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

// 文件上传
export function uploadFile(data) {
  return request({
    url: api + '/',
    method: 'post',
    data
  })
}

// 仅支持markdown图片路径删除
export function delFile(data) {
  return request({
    url: api + '/markdownImage',
    method: 'delete',
    params: { path: data }
  })
}

// 根据id删除文件
export function delFileIds(data) {
  return request({
    url: api + '/' + data,
    method: 'delete'
  })
}
// 重命名文件
export function renameFile(data) {
  return request({
    url: api + '/renameFile',
    method: 'put',
    data
  })
}
// 新建文件夹
export function newFolder(data) {
  return request({
    url: api + '/newFolder',
    method: 'post',
    data
  })
}

// 获取当前系统硬盘使用情况
export function hardDiskSize() {
  return request({
    url: api + '/hardDiskSize',
    method: 'get'
  })
}
// 获取当前用户存储使用情况
export function userHardDiskSize() {
  return request({
    url: api + '/userHardDiskSize',
    method: 'get'
  })
}
// markdown文件导入列表
export function getImportFiles() {
  return request({
    url: api + '/import/markdownFile',
    method: 'get'
  })
}
// markdown文件导出列表
export function getExportFiles() {
  return request({
    url: api + '/export/markdownFile',
    method: 'get'
  })
}

