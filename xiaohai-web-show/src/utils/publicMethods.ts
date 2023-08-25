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
/**
 * 打开QQ
 * @param number
 */
export function openQQ(number: any){
  /**适用于PC和任何手机跳转qq添加好友界面的实例**/
  const uag = navigator.userAgent;
  const ipad = uag.match(/(iPad).*OS\s([\d_]+)/),
    isMqVer = !ipad && uag.match(/(iPhone\sOS)\s([\d_]+)/) || uag.match(/(Android)\s+([\d.]+)/);
  if(isMqVer){
    //手机端自动打开弹出 包括苹果ios、安卓等均可弹出
   return "mqqapi://card/show_pslcard?src_type=internal&version=1&uin="+number+"&card_type=person&source=sharecard";
  }else{
    //pc浏览器默认打开弹出
    return"tencent://AddContact/?fromId=45&fromSubId=1&subcmd=all&uin="+number;
  }
}
