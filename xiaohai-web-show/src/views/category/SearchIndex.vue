<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11" style="height: 100%">
    <h1 class="flex-center">
      <el-icon><Menu /></el-icon> {{ name }}
    </h1>
    <!--电脑端-->
    <el-space
      class="hidden-sm-and-down"
      direction="vertical"
      fill
      size="large"
      style="display: flex"
    >
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
                  <svg-icon icon-class="eye-light" style="font-size: 15px" />
                  {{ article.pageView }}
                </span>
                <span class="text-xs font-number text-color"
                  ><svg-icon icon-class="message" style="font-size: 15px" /> 30</span
                >
                <span class="text-xs font-number text-color"
                  ><svg-icon icon-class="give-light" style="font-size: 15px" /> 20</span
                >
              </el-space>
            </span>
          </div>
        </div>
      </el-card>
    </el-space>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { reactive, ref, toRefs, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import RightSide from '@/components/layouts/RightSide.vue'
import { listArticles, listTag } from '@/api/show'
import {image} from "@/utils/publicMethods";

const loading = ref(true)
const route = useRoute()
// 展示文章列表
const dataList = ref([])
// 标签列表
const tags = ref([])
// 标签名称
const name = ref('')

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    type: 6,
    id: 0
  }
})

const { queryParams } = toRefs(data)

/** 查询展示文章列表 */
function getList() {
  queryParams.value.id = Number(route.params.id)
  name.value = <string>route.query.name
  loading.value = true
  listArticles(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
    loading.value = false
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

getTags() // 调用函数
getList()
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
