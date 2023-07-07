<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    {{ typeId }}
    <h1 class="flex-center"><svg-icon icon-class="tags"></svg-icon> {{ name }}</h1>
    <articleList :articleType="articleType" :typeId="typeId"></articleList>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import RightSide from '@/components/layouts/RightSide.vue'
import articleList from '@/components/articleList/index.vue'

const route = useRoute()

// 标签名称
const name = ref('')

const articleType = ref(5)
const typeId = ref(0)

onMounted(() => {
  // 监听$route对象上的参数属性变化
  watch(
    () => route.params.id,
    () => {
      // console.log(newId, oldId)
      // if (newId !== oldId) {
      typeId.value = Number(route.params.id)
      name.value = <string>route.query.name
      // 如果发生变化重新载入
      // window.location.reload()
      // }
    }
  )
})
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
