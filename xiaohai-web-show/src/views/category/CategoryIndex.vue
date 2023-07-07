<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11" style="height: 100%">
    <h1 class="flex-center">
      <el-icon><Menu /></el-icon>
      {{ name }}
    </h1>
    <el-card v-if="queryParams.id == 0" class="box-card" shadow="hover">
      <el-space wrap size="large">
        <div v-for="category in categories" :key="category.id">
          <el-button text bg size="large" @click="cancelClick(category)">
            <svg-icon icon-class="label-sign"></svg-icon> {{ category.name }}
            <div class="tags">{{ category.count }}</div>
          </el-button>
        </div>
      </el-space>
    </el-card>
    <el-space v-else direction="vertical" fill size="large" style="display: flex">
      <articleList :dataList="dataList"></articleList>
      <el-button v-if="loadMores" text type="primary" bg @click="loadMore">加载更多</el-button>
      <el-button v-else text disabled>没有更多了</el-button>
    </el-space>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import {reactive, ref, toRefs} from 'vue'
import { useRouter } from 'vue-router'
import RightSide from '@/components/layouts/RightSide.vue'
import {listArticles, listCategory} from '@/api/show'
import articleList from '@/components/articleList/index.vue'
import useStore from '@/store/index'
const store = useStore()

// 分类列表
const categories = ref([])
// 分类名称
const name = ref('分类')
// 标签列表
const tags = ref([])
// 展示文章列表
const dataList: any = ref([])
// 总数
const total = ref()
// 是否展示加载更多
const loadMores = ref(true)

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    type: 6,
    id:0
  }
})

const { queryParams } = toRefs(data)

const router = useRouter()
// 分类跳转
function cancelClick(category: any) {
  name.value = category.name
  getList(category.id)
}

/** 查询展示文章列表 */
function getList(val:any) {
  queryParams.value.id = val
  queryParams.value.pageNum = 1
  queryParams.value.pageSize = 10
  listArticles(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
    total.value = response.data.data.total
    const a = Math.ceil(total.value / queryParams.value.pageSize)
    loadMores.value = queryParams.value.pageNum + 1 <= a
  })
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
/**
 * 分类
 */
function getCategory() {
  listCategory().then((response) => {
    categories.value = response.data.data
  })
}

getCategory()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
