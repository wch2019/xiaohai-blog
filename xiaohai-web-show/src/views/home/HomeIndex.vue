<script setup lang="ts">
import { ref, reactive, toRefs } from 'vue'
import RightSide from '@/components/layouts/RightSide.vue'
import { listArticles } from '@/api/show'

import articleList from '@/components/articleList/index.vue'
import { getArticle, image } from '@/utils/publicMethods'

// 轮播列表
const carouselList: any = ref([])

const articleType = ref(1)
const typeId = ref(null)

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    type: 1
  }
})

const { queryParams } = toRefs(data)
// 修改文章类型
function getType(type: any) {
  articleType.value = type
}

/**
 * 轮播
 * @param type
 */
function getCarouselList() {
  queryParams.value.type = 2
  queryParams.value.pageNum = 1
  queryParams.value.pageSize = 3
  listArticles(queryParams.value).then((response) => {
    carouselList.value = response.data.data.records
  })
}

getCarouselList()
</script>

<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <!--电脑端-->
    <el-space
      class="hidden-sm-and-down"
      direction="vertical"
      fill
      size="large"
      style="display: flex"
    >
      <el-carousel :interval="4000" height="310px" style="border-radius: 10px">
        <el-carousel-item v-for="article in carouselList" :key="article">
          <el-image :src="image(article.cover)" @click="getArticle(article.id)" />
          <div class="carousel-title" @click="getArticle(article.id)">
            <span>{{ article.title }}</span>
          </div>
        </el-carousel-item>
      </el-carousel>

      <el-card class="box-card" shadow="hover" :body-style="{ padding: '10px' }">
        <el-scrollbar>
          <el-space wrap class="category">
            <el-button round @click="getType(1)">最新文章</el-button>
            <el-button round @click="getType(2)">最热文章</el-button>
            <el-button round @click="getType(3)">原创文章</el-button>
            <el-button round @click="getType(4)">转载文章</el-button>
          </el-space>
        </el-scrollbar>
      </el-card>
      <articleList :articleType="articleType" :typeId="typeId"></articleList>
    </el-space>
  </el-col>
  <!--手机端-->
  <el-space class="hidden-md-and-up" direction="vertical" fill size="large">
    <el-carousel :interval="4000" height="210px" style="border-radius: 10px">
      <el-carousel-item v-for="article in carouselList" :key="article">
        <el-image :src="image(article.cover)" @click="getArticle(article.id)" />
        <div class="carousel-title" @click="getArticle(article.id)">
          <span>{{ article.title }}</span>
        </div>
      </el-carousel-item>
    </el-carousel>

    <el-card class="box-card" shadow="hover" :body-style="{ padding: '10px' }">
      <el-scrollbar>
        <el-space wrap class="category">
          <el-button round @click="getType(1)">最新文章</el-button>
          <el-button round @click="getType(2)">最热文章</el-button>
          <el-button round @click="getType(3)">原创文章</el-button>
          <el-button round @click="getType(4)">转载文章</el-button>
        </el-space>
      </el-scrollbar>
    </el-card>
    <articleList :articleType="articleType" :typeId="typeId"></articleList>
  </el-space>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<style scoped>
.box-card {
  max-height: 180px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.category {
  height: 40px;
}
.carousel-title {
  cursor: pointer;
  position: absolute;
  z-index: 10;
  bottom: 40px;
  height: 40px;
  width: 100%;
  line-height: 40px;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
  font-size: 22px;
  color: #ecf8ff;
  font-weight: bold;
}
</style>
