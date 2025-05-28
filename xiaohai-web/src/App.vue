<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
// 获取现有的或新建一个 favicon 链接元素
import { getValidFaviconUrl } from '@/utils/common'

const faviconLink = document.querySelector('link[rel="icon"]')
if (faviconLink) {
  // 图片加载成功后再替换 favicon
  faviconLink.href = getValidFaviconUrl
}
export default {
  name: 'App'
}
</script>

<style lang="scss">
body {
  background-color: #fafafa;
  font-family: open sans, Helvetica, Arial, sans-serif;
}

::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 6px;
}

::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: #535353;
}

::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background: rgba(234, 233, 233, 0.98);
}

//html{
//  -webkit-filter:grayscale(100%);
//  -moz-filter:grayscale(100%);
//  -ms-filter:grayscale(100%);
//  -o-filter:grayscale(100%);
//  filter:grayscale(100%);
//  filter:url("data:image/svg+xml;utf8,<svg xmlns=\"http://www.w3.org/2000/svg\"><filter id=\"grayscale\"><feColorMatrix type=\"matrix\" values=\"0.3333 0.3333 0.3333 0 0 0.3333 0.3333 0.3333 0 0 0.3333 0.3333 0.3333 0 0 0 0 0 1 0\"/></filter></svg>#grayscale");
//  filter:progid:DXImageTransform.Microsoft.BasicImage(grayscale=1)
//}
</style>
