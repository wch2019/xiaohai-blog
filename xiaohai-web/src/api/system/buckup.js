import request from '@/utils/request'

const api = '/system/backup'

// 新增系统备份
export function addBackup() {
  return request({
    url: api,
    method: 'post'
  })
}

// 文件名称还原备份
export function restoreFileName(fileName) {
  return request({
    url: api + '/restore/path/' + fileName,
    method: 'post'
  })
}

// 上传还原备份文件
export function restoreBackupFile(data) {
  return request({
    url: api + '/restore/upload',
    method: 'post',
    data
  })
}
