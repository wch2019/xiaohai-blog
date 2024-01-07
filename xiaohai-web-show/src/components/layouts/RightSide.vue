<template>
  <el-space direction="vertical" fill size="large" style="width: 100%">
    <el-card
      class="box-card"
      shadow="hover"
      :body-style="{ padding: '0px', height: '440px' }"
      style="position: relative"
    >
      <img src="../../assets/image/1.jpg" class="image" />
      <div style="display: flex; padding: 10px; justify-content: center; text-align: center">
        <el-avatar v-if="showBasic.avatar" :src="image(showBasic.avatar)" class="panThumb" />

        <el-space direction="vertical" :size="'large'" fill style="margin-top: 20px; width: 80%">
          <h2 style="margin-bottom: 0px">{{ showBasic.username }}</h2>
          <div>{{ showBasic.summary }}</div>
          <div style="padding-top: 20px">
            <el-space wrap size="default">
              <a v-if="showBasic.gitee" :href="showBasic.gitee" target="_blank">
                <div class="diamond-clip-path diamond-icon">
                  <svg-icon icon-class="gitee"></svg-icon>
                </div>
              </a>
              <a v-if="showBasic.github" :href="showBasic.github" target="_blank">
                <div class="diamond-clip-path diamond-icon">
                  <svg-icon icon-class="github"></svg-icon>
                </div>
              </a>
              <a
                v-if="showBasic.qqNumber"
                :href=openQQ(showBasic.qqNumber)
                target="_blank"
              >
                <div class="diamond-clip-path diamond-icon">
                  <svg-icon icon-class="qq"></svg-icon>
                </div>
              </a>
              <el-link :underline="false" v-if="showBasic.weChat" @click="open(showBasic.weChat)">
                <div class="diamond-clip-path diamond-icon">
                  <svg-icon icon-class="wechat"></svg-icon>
                </div>
              </el-link>
            </el-space>
          </div>
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
    <el-card class="box-card" shadow="hover" style="text-align: center">
      {{ greetings() }}
    </el-card>

    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="hot"></svg-icon> 热门</h2>
      </template>
      <div
        v-loading="store.hotArticles ? false : loading"
        v-for="article in store.hotArticles"
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
                text-overflow: ellipsis;
                white-space: normal;
                word-wrap: break-word;
                word-break: break-all;
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
          fit="cover"
          :src="image(article.cover)"
          style="margin-left: 10px; border-radius: 10px; height: 80px; min-width: 140px"
          class="image-hot"
          @click="getArticle(article.id)"
        />
      </div>
    </el-card>

    <el-card class="box-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="tags"></svg-icon> 标签</h2>
          <el-link @click="cancelClick('/tags')">查看更多</el-link>
        </div>
      </template>
      <el-space wrap size="small">
        <div v-for="(tag, index) in store.tags" :key="tag.id">
          <el-button
            v-if="index < 10"
            text
            bg
            size="large"
            @click="cancelClick('/tags' + '?id=' + tag.id + '&name=' + tag.name)"
          >
            <svg-icon icon-class="label-sign"></svg-icon> {{ tag.name }}
            <div class="tags">{{ tag.count }}</div>
          </el-button>
        </div>
      </el-space>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="link"></svg-icon> 友链</h2>
      </template>
      <el-space wrap size="large">
        <div v-for="link in store.friendLinkList" :key="link">
          <el-link :href="link.url" target="_blank">{{ link.name }}</el-link>
        </div>
      </el-space>
    </el-card>
  </el-space>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {getArticle, image, open, openQQ} from '@/utils/publicMethods'
import useStore from '@/store/index'

const store = useStore()
const router = useRouter()

const loading = ref(true)

// 网站信息
console.log(store,'store')
const showBasic = ref<any>(store?.showBasic)

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

// 标签跳转
function cancelClick(path: any) {
  router.push(path).then(() => window.scroll(0, 0))
}
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
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
