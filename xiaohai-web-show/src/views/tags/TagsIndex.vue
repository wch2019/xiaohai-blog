<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <h1 class="flex-center"><svg-icon icon-class="tags"></svg-icon> 标签</h1>
    <el-card class="box-card" shadow="hover">
      <el-space v-for="tag in tags" :key="tag.id" wrap size="large">
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
import { listTag } from '@/api/show'
// 标签列表
const tags = ref([])
/**
 * 标签列表
 */
const getTags = async () => {
  // 函解构用async和await包裹
  const { data: res } = await listTag() // 获取接口调用函数getList中的值data 其中data是表单里的数据
  // 对data进行解构赋值 取出请求的结果res
  tags.value = res.data
}
const router = useRouter()
// 标签跳转
function cancelClick(path: any) {
  router.push(path)
}

getTags()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
