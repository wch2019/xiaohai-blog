import request from '@/utils/request'

const api = '/home/manage'

// 文章数,用户数,评论数，浏览量
export function getCount() {
  return request({
    url: api + '/count',
    method: 'get'
  })
}

// 分类，标签，文章阅读量排行
export function getRank() {
  return request({
    url: api + '/rank',
    method: 'get'
  })
}
// 近一年文章贡献度
export function getContribution() {
  return request({
    url: api + '/contribution',
    method: 'get'
  })
}

// 获取最近一周访问量
export function getVisitWeek() {
  return request({
    url: api + '/visit-week',
    method: 'get'
  })
}

// 随机输出毒鸡汤
export function getWord() {
  return request({
    url: api + '/word',
    method: 'get'
  })
}
