<!--<script setup lang="ts">-->
<!--import { ref, watch, reactive, toRefs } from 'vue'-->
<!--const props = defineProps({-->
<!--  total: {-->
<!--    type: Number,-->
<!--    default: 10-->
<!--  },-->
<!--  pageSize: {-->
<!--    type: Number,-->
<!--    default: 10-->
<!--  },-->
<!--  pageNum: {-->
<!--    type: Number,-->
<!--    default: 1-->
<!--  }-->
<!--})-->
<!--const { total, pageSize,pageNum } = toRefs(props)-->

<!--// 是否展示加载更多-->
<!--const loadMores = ref(true)-->

<!--/**-->
<!-- * 加载更多-->
<!-- */-->
<!--function loadMore() {-->
<!--  const a = Math.ceil(total.value / pageSize?.value)-->
<!--  if (pageNum?.value + 1 >= a) {-->
<!--    loadMores.value = false-->
<!--  }-->
<!--  if (pageNum?.value + 1 <= a) {-->
<!--    pageNum?.value = 1 + pageNum?.value-->
<!--    listArticles(queryParams.value).then((response) => {-->
<!--      dataList.value = [...dataList.value, ...response.data.data.records]-->
<!--    })-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<template>-->
<!--  <el-button v-if="loadMores" text type="primary" bg @click="loadMore">加载更多</el-button>-->
<!--  <el-button v-else text disabled>没有更多了</el-button>-->
<!--</template>-->

<!--<style scoped>-->

<!--</style>-->