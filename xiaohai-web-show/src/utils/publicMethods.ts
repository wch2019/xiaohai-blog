import router from '@/router'
import {ElMessageBox} from "element-plus";

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
// 获取url参数
export function getQueryVariable(variable: any) {
  const query = window.location.search.substring(1)
  const vars = query.split('&')
  for (let i = 0; i < vars.length; i++) {
    const pair = vars[i].split('=')
    if (pair[0] === variable) {
      return pair[1]
    }
  }
  return false
}

/**
 * 设置全局md编写图片路径
 * @param replace
 */
export function markdownImageFile(replace: any) {
  return '../files'.replace(replace, '')
}

/**
 * 展示型弹窗
 * @param number
 */
export function open(number: any){
  ElMessageBox.alert(number, {
    showConfirmButton:false,
    closeOnClickModal:true,
    showClose:false,
    center: true,
  })
}
