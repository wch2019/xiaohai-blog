<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    {{ typeId }}
    <h1 class="flex-center">
      <svg-icon icon-class="tags" @click="typeId = 0"></svg-icon> {{ name }}
    </h1>
    <el-card v-if="typeId == 0" class="box-card" shadow="hover">
      <el-space size="large" wrap>
        <div v-for="tag in tags" :key="tag.id">
          <el-button text bg size="large" @click="cancelClick(tag)">
            <svg-icon icon-class="label-sign"></svg-icon> {{ tag.name }}
            <div class="tags">{{ tag.count }}</div>
          </el-button>
        </div>
      </el-space>
    </el-card>
    <div v-else class="hidden-sm-and-down">
      <articleList :articleType="articleType" :typeId="typeId" />
    </div>
  </el-col>
  <div v-if="typeId != 0" class="hidden-md-and-up">
    <articleList :articleType="articleType" :typeId="typeId" />
  </div>

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
import articleList from '@/components/articleList/index.vue'
// 标签名称
const name = ref('标签')
// 标签列表
const tags = ref([])

const articleType = ref(5)
const typeId = ref(0)

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
function cancelClick(tag: any) {
  typeId.value = tag.id
  name.value = tag.name
  // router.push(path)
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
