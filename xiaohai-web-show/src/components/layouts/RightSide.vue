<template>
  <el-space direction="vertical" fill size="large" style="width: 100%">
    <el-card
      class="box-card"
      shadow="hover"
      :body-style="{ padding: '0px', height: '380px' }"
      style="position: relative"
    >
      <img src="../../assets/image/1.jpg" class="image" />
      <div style="display: flex; padding: 10px; justify-content: center; text-align: center">
        <el-avatar :src="image(showBasic.avatar)" class="panThumb" />

        <el-space direction="vertical" :size="'large'" fill style="margin-top: 20px; width: 80%">
          <h3>{{ showBasic.username }}</h3>
          <div>{{ showBasic.summary }}</div>
          <div style="display: inline-flex; vertical-align: top; justify-content: space-between">
            <el-space fill direction="vertical">
              <div class="text-sm text-color">文章</div>
              <div class="text-xl font-number">{{ showBasic.articleCount }}</div>
            </el-space>
            <el-space fill direction="vertical">
              <div class="text-sm text-color">分类</div>
              <div class="text-xl font-number">{{ showBasic.categoryCount }}</div>
            </el-space>
            <el-space fill direction="vertical" size="small">
              <div class="text-sm text-color">标签</div>
              <div class="text-xl font-number">{{ showBasic.tagsCount }}</div>
            </el-space>
            <el-space fill direction="vertical" size="small">
              <div class="text-sm text-color">评论</div>
              <div class="text-xl font-number">{{ showBasic.messageCount }}</div>
            </el-space>
          </div>
        </el-space>
      </div>
    </el-card>
    <el-card class="box-card" shadow="hover" style="text-align: center"> {{ a }} </el-card>

    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="hot"></svg-icon> 热门</h2>
      </template>
      <div
        v-loading="loading"
        v-for="article in dataList"
        :key="article"
        style="display: flex; margin-top: 16px; justify-content: space-between"
      >
        <div style="display: flex; flex-direction: column; justify-content: space-between">
          <el-link :underline="false" style="justify-content: left">
            <span
              style="
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
              "
              @click="getArticle(article.id)"
            >
              {{ article.title }}
            </span>
          </el-link>
          <span class="text-xs font-number text-color" v-if="article.createdTime">
            {{ article.createdTime.split(' ')[0] }}
          </span>
        </div>
        <el-image
          :src="image(article.cover)"
          style="margin-left: 10px; border-radius: 10px; height: 80px; min-width: 140px"
          class="image-hot"
          @click="getArticle(article.id)"
        />
      </div>
    </el-card>

    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="tags"></svg-icon> 标签</h2>
      </template>
      <el-space v-for="tag in tags" :key="tag.id" wrap size="small">
        <el-button
          text
          bg
          size="large"
          @click="cancelClick('/tagSearch/' + tag.id + '?name=' + tag.name)"
        >
          <svg-icon icon-class="label-sign"></svg-icon> {{ tag.name }}
          <div class="tags">{{ tag.count }}</div>
        </el-button>
      </el-space>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="link"></svg-icon> 友链</h2>
      </template>
      <el-space wrap size="small">
        <el-link v-for="link in friendLinkList" :key="link" :href="link.url" target="_blank"
          >{{ link.name }}</el-link
        >
      </el-space>
    </el-card>
  </el-space>
</template>

<script setup lang="ts">
import { reactive, ref, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import {listArticles, listTag, findShowBasic, friendLink} from '@/api/show'

const loading = ref(true)
// 标签列表
const tags = ref([])
// 展示热门文章列表
const dataList = ref([])
// 网站信息
const showBasic = ref({})
// 友情链接
const friendLinkList = ref({})

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 5,
    type: 2
  }
})
const { queryParams } = toRefs(data)
function greetings() {
  const date = new Date()
  if (date.getHours() >= 6 && date.getHours() < 8) {
    return '晨起披衣出草堂，轩窗已自喜微凉🌅！'
  }
  if (date.getHours() >= 8 && date.getHours() < 12) {
    return '上午好🌞！'
  }
  if (date.getHours() >= 12 && date.getHours() < 18) {
    return '下午好☕！'
  }
  if (date.getHours() >= 18 && date.getHours() < 24) {
    return '晚上好🌃！'
  }
  if (date.getHours() >= 0 && date.getHours() < 6) {
    return '偷偷向银河要了一把碎星，只等你闭上眼睛撒入你的梦中，晚安🌛！'
  }
  return ''
}
const a = greetings()
/**
 * 标签列表
 */
const getTags = async () => {
  // 函解构用async和await包裹
  const { data: res } = await listTag() // 获取接口调用函数getList中的值data 其中data是表单里的数据
  // 对data进行解构赋值 取出请求的结果res
  tags.value = res.data
}
/** 查询展示文章列表 */
function getList() {
  loading.value = true
  listArticles(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
    loading.value = false
  })
}
// 网站信息
function getShowBasic() {
  findShowBasic().then((response) => {
    showBasic.value = response.data.data
  })
}
// 友情链接
function getFriendLink() {
  friendLink().then((response) => {
    friendLinkList.value = response.data.data
  })
}
/**
 * 图片地址拼接
 * @param cover
 */
function image(cover: any) {
  return import.meta.env.VITE_APP_BASE_API_FILE + cover
}

const router = useRouter()
// 路由跳转
function getArticle(id: any) {
  router.push({ path: `/article/${id}` })
}
// 标签跳转
function cancelClick(path: any) {
  router.push(path)
}
getShowBasic()
getFriendLink()
getTags()
getList()
</script>

<style scoped>
.box-card {
  border-radius: 10px;
  border: 1px solid transparent;
  height: 100%;
}
.image {
  width: 100%;
  height: 170px;
  display: block;
}
.test::before {
  content: '';
  position: absolute;
  top: 90px;
  left: 0;
  width: 100%;
  height: 30px;
  z-index: 2;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0), #fff);
}
.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}
.panThumb {
  height: 100px !important;
  width: 100px !important;
  position: absolute !important;
  top: 115px;
  border: 5px solid #ffffff;
}
.image-hot:hover {
  transform: scale(1.1);
}

.image:hover {
  transform: scale(1.1);
}
</style>
