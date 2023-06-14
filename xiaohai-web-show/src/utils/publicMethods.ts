/**
 * 图片地址拼接
 * @param cover
 */
export function image(cover: any) {
  return import.meta.env.VITE_APP_BASE_API_FILE + cover
}
