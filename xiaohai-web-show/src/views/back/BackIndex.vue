<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <h1 class="flex-center"><svg-icon icon-class="time-dark"></svg-icon> 归档</h1>
    <el-card class="box-card" shadow="hover">
      <el-timeline>
        <el-timeline-item
          v-for="article in dataList"
          :key="article"
          :timestamp="article.createdTime.split(' ')[0]"
          placement="top"
        >
          <el-card class="box-card">
            <h2>{{ article.title }}</h2>
            <span style="display: flex; align-items: center; flex-direction: row-reverse">
              <el-space size="small">
                <el-avatar size="small" :src="image(article.avatar)" />
                <span v-if="article.nickName" class="text-xs">{{ article.nickName }}</span>
                <span v-else class="text-xs">{{ article.username }}</span>
              </el-space>
            </span>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { reactive, ref, toRefs } from 'vue'
import RightSide from '@/components/layouts/RightSide.vue'
import { listBack } from '@/api/show'

// 展示归档列表
const dataList: any = ref([])

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 20
  }
})

const { queryParams } = toRefs(data)

/** 查询展示推荐列表 */
function getList() {
  listBack(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
  })
}

/**
 * 图片地址拼接
 * @param cover
 */
function image(cover: any) {
  return import.meta.env.VITE_APP_BASE_API_FILE + cover
}

getList()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
