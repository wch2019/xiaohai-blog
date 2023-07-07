import router from '@/router'

/**
 * 图片地址拼接
 * @param cover
 */
export function image(cover: any) {
  return import.meta.env.VITE_APP_BASE_API_FILE + cover
}

/**
 * 根据文章id跳转到文章详情页
 * @param id
 */
export function getArticle(id: any) {
  return router.push({ path: `/article/${id}` })
}
