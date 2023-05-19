<template>
  <svg-icon
    icon-class="search"
    class="search-icon"
    style="font-size: 20px"
    @click="dialogVisible = true"
  ></svg-icon>
  <el-dialog v-model="dialogVisible" :append-to-body="true" :fullscreen="isMobile()">
    <template #header>
      <span class="search-title">搜索</span>
    </template>
    <!-- 输入框 -->

    <div class="search-input-wrapper">
      <input v-model="data.keywords" placeholder="输入文章标题或内容..." />
      <svg-icon
        icon-class="search"
        class="search-icon"
        style="font-size: 25px; margin-right: 10px"
      ></svg-icon>
    </div>

    <!-- 搜索结果 -->
    <div class="search-result-wrapper">
      <hr class="divider" />
      <ul style="list-style: none; padding: 0">
        <li class="search-reslut" v-for="item of data.articleList" :key="item.id">
          <!-- 文章标题 -->
          <el-link @click="goTo(item.id)" style="font-size: larger" v-html="item.title"></el-link>
          <!-- 文章内容 -->
          <p class="search-reslut-content" v-html="item.summary" />
        </li>
      </ul>
      <!-- 搜索结果不存在提示 -->
      <div v-show="data.flag && data.articleList.length === 0" style="font-size: 0.875rem">
        找不到您查询的内容：{{ data.keywords }}
      </div>
    </div>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { getSearch } from '@/api/show'

const dialogVisible = ref(false)

const data = reactive({
  keywords: '',
  articleList: [],
  flag: false
})
function reset() {
  data.keywords = ''
  data.articleList = []
  data.flag = false
}
const router = useRouter()
// 页面跳转
function goTo(id: any) {
  dialogVisible.value = false
  router.push({ path: `/article/${id}` })
}

// 判断屏幕大小
function isMobile() {
  reset()
  const { clientWidth } = document.documentElement
  console.log(clientWidth)
  return clientWidth <= 960
}
watch(
  () => data.keywords,
  () => {
    data.flag = data.keywords.trim() !== ''
    if (data.flag) {
      getSearch(data.keywords).then((res) => {
        data.articleList = res.data.data
      })
    }
  }
)
</script>

<style scoped>
.search-title {
  color: #49b1f5;
  font-size: 1.25rem;
  line-height: 1;
}
.search-input-wrapper {
  display: flex;
  padding: 5px;
  border: 2px solid #8e8cd8;
  border-radius: 2rem;
  align-items: center;
}
.search-input-wrapper input {
  width: 99%;
  margin-left: 5px;
  outline: none;
  border: 0;
}
@media (min-width: 960px) {
  .search-result-wrapper {
    padding-right: 5px;
    height: 450px;
    overflow: auto;
  }
}
@media (max-width: 959px) {
  .search-result-wrapper {
    overflow: auto;
  }
}
.search-reslut a {
  color: #555;
  font-weight: bold;
  /*border-bottom: 1px solid #999;*/
  text-decoration: none;
}
.search-reslut-content {
  color: #555;
  cursor: pointer;
  border-bottom: 1px dashed #ccc;
  padding: 5px 0;
  line-height: 2;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.divider {
  margin: 20px 0;
  border: 2px dashed #d2ebfd;
}
</style>
