import request from '@/utils/request'

const api = '/system/backup'

// 新增系统备份
export function addBackup() {
  return request({
    url: api,
    method: 'post'
  })
}
