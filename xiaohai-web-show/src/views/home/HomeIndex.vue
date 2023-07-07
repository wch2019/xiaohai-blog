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
            <el-button round @click="getList(1)">最新文章</el-button>
            <el-button round @click="getList(2)">最热文章</el-button>
            <el-button round @click="getList(3)">原创文章</el-button>
            <el-button round @click="getList(4)">转载文章</el-button>
          </el-space>
        </el-scrollbar>
      </el-card>
      <el-card
        v-loading="loading"
        v-for="article in dataList"
        :key="article"
        class="box-card box-card-hover"
        shadow="hover"
      >
        <div style="display: flex; flex-direction: row">
          <el-image :src="image(article.cover)" class="image" @click="getArticle(article.id)" />
          <div
            style="
              margin-left: 18px;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              width: 100%;
            "
          >
            <el-link :underline="false" style="justify-content: left">
              <h2
                style="
                  overflow: hidden;
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  -webkit-box-orient: vertical;
                "
                @click="getArticle(article.id)"
              >
                <svg-icon v-if="article.isTop == 1" icon-class="top" />
                {{ article.title }}
              </h2>
            </el-link>
            <span
              style="
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
              "
            >
              {{ article.summary }}
            </span>
            <span style="display: flex; align-items: center; justify-content: space-between">
              <span style="display: flex; align-items: center">
                <el-space size="small">
                  <el-avatar size="small" :src="image(article.avatar)" />
                  <span v-if="article.nickName" class="text-xs">{{ article.nickName }}</span>
                  <span v-else class="text-xs">{{ article.username }}</span>
                  <el-tag size="small">{{ article.categoryName }}</el-tag>
                  <template v-for="(item, index) in tags">
                    <el-tag
                      v-if="article.tags && article.tags.includes(item.id)"
                      :key="index"
                      style="margin-right: 4px"
                      type="success"
                      size="small"
                      :label="index"
                      border
                      >{{ item.name }}
                    </el-tag>
                  </template>
                </el-space>
              </span>

              <el-space alignment="center" size="large">
                <span class="text-xs font-number text-color">
                  <svg-icon icon-class="eye-light" style="font-size: 15px" /> {{ article.pageView }}
                </span>
                <span class="text-xs font-number text-color"
                  ><svg-icon icon-class="message" style="font-size: 15px" />
                  {{ article.commentCount }}</span
                >
                <span class="text-xs font-number" @click="clickLike(article)"
                  ><svg-icon
                    :icon-class="article.clickLike == 1 ? 'give-dark' : 'give-light'"
                    style="font-size: 15px; cursor: pointer"
                    :style="{ color: article.clickLike == 1 ? '#fd5a5a' : '' }"
                  />
                  {{ article.likeCount }}</span
                >
              </el-space>
            </span>
          </div>
        </div>
      </el-card>
      <el-button v-if="loadMores" text type="primary" bg @click="loadMore">加载更多</el-button>
      <el-button v-else text disabled>没有更多了</el-button>
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
          <el-button round @click="getList(1)">最新文章</el-button>
          <el-button round @click="getList(2)">最热文章</el-button>
          <el-button round @click="getList(3)">原创文章</el-button>
          <el-button round @click="getList(4)">转载文章</el-button>
        </el-space>
      </el-scrollbar>
    </el-card>

    <el-card
      v-for="article in dataList"
      :key="article"
      class="box-card-mobile box-card-hover"
      shadow="hover"
      :body-style="{ padding: '10px' }"
    >
      <div class="article-flex">
        <el-image :src="image(article.cover)" class="image" @click="getArticle(article.id)" />
        <div
          style="
            margin-left: 10px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 100%;
          "
        >
          <h2
            class="text-md"
            style="
              overflow: hidden;
              display: -webkit-box;
              -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;
            "
            @click="getArticle(article.id)"
          >
            <svg-icon v-if="article.isTop == 1" icon-class="top" />
            {{ article.title }}
          </h2>
          <span style="display: flex; align-items: center; justify-content: space-between">
            <span style="display: flex; align-items: center">
              <el-space size="small">
                <el-avatar size="small" :src="image(article.avatar)" />
                <span v-if="article.nickName" class="text-xs">{{ article.nickName }}</span>
                <span v-else class="text-xs">{{ article.username }}</span>
              </el-space>
            </span>
            <span class="text-xs font-number" @click="clickLike(article)">
              <svg-icon
                :icon-class="article.clickLike == 1 ? 'give-dark' : 'give-light'"
                style="font-size: 15px; cursor: pointer"
                :style="{ color: article.clickLike == 1 ? '#fd5a5a' : '' }"
              />
              {{ article.likeCount }}
            </span>
          </span>
        </div>
      </div>
    </el-card>
    <el-button v-if="loadMores" text type="primary" bg @click="loadMore">加载更多</el-button>
    <el-button v-else text disabled>没有更多了</el-button>
  </el-space>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref, reactive, toRefs } from 'vue'
import { useRouter } from 'vue-router'
import RightSide from '@/components/layouts/RightSide.vue'
import { listTag, listArticles, articleLike } from '@/api/show'
import {image} from "@/utils/publicMethods";

const loading = ref(true)
// 展示文章列表
const dataList: any = ref([])
// 轮播列表
const carouselList: any = ref([])
// 标签列表
const tags = ref([])
// 总数
const total = ref()
// 是否展示加载更多
const loadMores = ref(true)

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    type: 1
  },
  rules: {
    dictName: [{ required: true, message: '字典名称不能为空', trigger: 'blur' }],
    dictType: [{ required: true, message: '字典类型不能为空', trigger: 'blur' }]
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询展示文章列表 */
function getList(type: any) {
  console.log(type, 'type')
  queryParams.value.type = type
  queryParams.value.pageNum = 1
  queryParams.value.pageSize = 10
  loading.value = true
  listArticles(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
    total.value = response.data.data.total
    const a = Math.ceil(total.value / queryParams.value.pageSize)
    if (queryParams.value.pageNum + 1 > a) {
      loadMores.value = false
    }
    loading.value = false
  })
}
function clickLike(val: any) {
  const params: any = {
    articleId: val.id,
    clickLike: val.clickLike == null || val.clickLike === 0 ? 1 : 0
  }
  articleLike(params).then((res: any) => {
    if (res.data.code === 200) {
      for (let i = 0; i < dataList.value.length; i++) {
        if (dataList.value[i].id === val.id) {
          dataList.value[i].clickLike = dataList.value[i].clickLike === 1 ? 0 : 1
          if (dataList.value[i].clickLike === 1) {
            // 点赞
            dataList.value[i].likeCount += 1
          } else {
            // 取消点赞
            dataList.value[i].likeCount -= 1
          }
        }
      }
    }
  })
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

const router = useRouter()
function getArticle(id: any) {
  router.push({ path: `/article/${id}` })
}
/**
 * 标签列表
 */
const getTags = async () => {
  // 函解构用async和await包裹
  const { data: res } = await listTag() // 获取接口调用函数getList中的值data 其中data是表单里的数据
  // 对data进行解构赋值 取出请求的结果res
  tags.value = res.data
  // data = res.data // 将请求结果的data值赋给data.list 方便表格table与之数据双向绑定
}

/**
 * 加载更多
 */
function loadMore() {
  const a = Math.ceil(total.value / queryParams.value.pageSize)
  if (queryParams.value.pageNum + 1 >= a) {
    loadMores.value = false
  }
  if (queryParams.value.pageNum + 1 <= a) {
    queryParams.value.pageNum = 1 + queryParams.value.pageNum
    listArticles(queryParams.value).then((response) => {
      dataList.value = [...dataList.value, ...response.data.data.records]
    })
  }
}
getTags() // 调用函数
getList(1)
getCarouselList()
</script>

<style scoped>
.box-card {
  max-height: 180px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.box-card-mobile {
  max-height: 120px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.image {
  border-radius: 10px;
  min-width: 238px;
  height: 140px;
}

.box-card-hover:hover {
  filter: drop-shadow(0 0 0.5em rgb(252, 191, 191));
  transform: scale(1.01);
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
.category {
  height: 40px;
}
.article-flex {
  display: flex;
  flex-direction: row;
}
.article-right-flex {
  display: flex;
  flex-direction: row;
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
