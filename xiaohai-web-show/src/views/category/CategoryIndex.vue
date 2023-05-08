<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11" style="height: 100%">
    <h1 class="flex-center">
      <el-icon><Menu /></el-icon> 分类
    </h1>
    <el-card class="box-card" shadow="hover">
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
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref } from 'vue'
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
getCategory()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
