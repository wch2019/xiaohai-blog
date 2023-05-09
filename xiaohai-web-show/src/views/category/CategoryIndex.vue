<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11" style="height: 100%">
    <h1 class="flex-center">
      <el-icon><Menu /></el-icon> 分类
    </h1>
    <el-card class="box-card" shadow="hover">
      <el-space v-for="category in categories" :key="category.id" wrap size="large">
        <el-button
          text
          bg
          size="large"
          @click="cancelClick('/categorySearch/' + category.id + '?name=' + category.name)"
        >
          <svg-icon icon-class="label-sign"></svg-icon> {{ category.name }}
          <div class="tags">{{ category.count }}</div>
        </el-button>
      </el-space>
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import RightSide from '@/components/layouts/RightSide.vue'
import { listCategory } from '@/api/show'
// 分类列表
const categories = ref([])

/**
 * 分类
 */
function getCategory() {
  listCategory().then((response) => {
    categories.value = response.data.data
  })
}
const router = useRouter()
// 分类跳转
function cancelClick(path: any) {
  router.push(path)
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
