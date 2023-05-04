<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <!--电脑端-->
    <el-card v-loading="loading" class="box-card hidden-sm-and-down" shadow="hover">
      <h1 class="flex-center">{{ articleOne.title }}</h1>
      <span style="display: flex; align-items: center; justify-content: space-between">
        <span style="display: flex; align-items: center">
          <el-space size="default">
            <el-avatar
              size="default"
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
            <span class="text-sm">xiaohai</span>
            <el-tag size="default">{{ articleOne.categoryName }}</el-tag>
            <el-tag type="success" size="default">Tag 2</el-tag>
            <el-tag type="success" size="default">Tag 2</el-tag>
          </el-space>
        </span>
        <el-space alignment="center" size="large">
          <span class="text-sm font-number text-color">
            <svg-icon icon-class="time-light" />{{ articleOne.createdTime }}</span
          >
          <span class="text-sm font-number text-color">
            <svg-icon icon-class="eye-light" style="font-size: 15px" /> 100
          </span>
          <span class="text-sm font-number text-color"
            ><svg-icon icon-class="message" style="font-size: 15px" /> 30</span
          >
          <span class="text-sm font-number"
            ><svg-icon icon-class="give-light" style="font-size: 15px" /> 20</span
          >
        </el-space>
      </span>
      <el-divider />
      <div v-if="articleOne.isOriginal == 0" class="tip">
        原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br />
      </div>
      <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br /></div>
      <v-md-preview :text="articleOne.text"></v-md-preview>
      <el-divider />
      <el-divider />
      <h3 class="flex-center"><svg-icon icon-class="message"></svg-icon> <span>评论</span></h3>
      <el-empty description="暂无评论" />
    </el-card>
    <!--手机端-->
    <el-card class="box-card hidden-md-and-up" shadow="hover">
      <el-space alignment="center" wrap size="small">
        <el-tag size="small">Tag 1</el-tag>
        <el-tag type="success" size="small">Tag 2</el-tag>
        <el-tag type="success" size="small">Tag 2</el-tag>
      </el-space>
      <h1 class="flex-center">这是一个标题嗷nnbnbbn嗷</h1>
      <span
        style="display: flex; align-items: center; justify-content: space-between; flex-wrap: wrap"
      >
        <span style="display: flex; align-items: center">
          <el-space size="default">
            <el-avatar
              size="small"
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
            <span class="text-sm">xiaohai</span>
          </el-space>
        </span>
        <el-space alignment="center" size="small">
          <span class="text-xs font-number text-color">
            <svg-icon icon-class="time-light" /> 2023-04-22</span
          >
          <span class="text-xs font-number text-color">
            <svg-icon icon-class="eye-light" /> 100
          </span>
          <span class="text-xs font-number text-color"><svg-icon icon-class="message" /> 30</span>
          <span class="text-xs font-number"><svg-icon icon-class="give-light" /> 20</span>
        </el-space>
      </span>
      <el-divider />

      <div class="tip">原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br /></div>
      <div class="tip">转载 本文转载自http://localhost:4001/#/article<br /></div>
      <div>
        <h2>ddddd</h2>
      </div>

      <el-divider />
      <h3 class="flex-center"><svg-icon icon-class="message"></svg-icon> <span>评论</span></h3>
      <el-empty description="暂无评论" />
    </el-card>
  </el-col>

  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import RightSide from '@/views/article/RightSide.vue'
import { article } from '@/api/show'

const loading = ref(true)
const articleOne = ref('')
const route = useRoute()

function getArticle() {
  const { id } = route.params
  loading.value = true
  article(id).then((response) => {
    articleOne.value = response.data.data
    loading.value = false
  })
}
getArticle()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.image {
  border-radius: 10px;
  min-width: 238px;
  height: 140px;
}
/*手机端*/
@media (max-width: 992px) {
  .image {
    border-radius: 10px;
    min-width: 149px;
    height: 92px;
  }
}
.image:hover {
  transform: scale(1.1);
}
.tip {
  padding: 10px;
  margin: 20px auto 15px;
  background-color: #ecf8ff;
  border-radius: 4px;
  border-left: 5px solid #50bfff;
  color: #888;
}
</style>
