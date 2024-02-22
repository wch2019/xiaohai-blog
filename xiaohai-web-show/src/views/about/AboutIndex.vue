<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <h1 class="flex-center">
      <el-icon><UserFilled /></el-icon> 关于
    </h1>
    <el-card class="box-card" shadow="hover">
<!--      <div class="menus-item">-->
<!--        <router-link class="menu-btn" to="/chat"> 聊天测试</router-link>-->
<!--      </div>-->
      <v-md-preview :text="content" ref="preview"></v-md-preview>
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import RightSide from '@/components/layouts/RightSide.vue'
import useStore from '@/store/index'
import {markdownImageFile} from "@/utils/publicMethods";

const store = useStore()
// 关于信息
const content = ref<any>('')
// 网站信息
const website = ref<any>(store.website)
function getContent() {
  // 文章内图片地址替换
  content.value = website.value.content.replaceAll(
    markdownImageFile(''),
    `${import.meta.env.VITE_APP_BASE_API_FILE}`+markdownImageFile('')
  )
}
getContent()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
/*样式穿透 md文件*/
>>> .github-markdown-body {
  padding: 0;
}
</style>
