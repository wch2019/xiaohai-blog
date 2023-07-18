<script setup lang="ts">
import { computed } from 'vue'
import HeardBar from '@/components/layouts/HeardBar.vue'
import FooterBar from '@/components/layouts/FooterBar.vue'
import BackTop from '@/components/layouts/BackTop.vue'
import { isDark } from '@/utils/dark'
import router from '@/router'

// 内容背景色根据主题色调整
function isDarkBackground() {
  if (!isDark.value) {
    return 'background-color: #f7f7fb'
  }
  return ''
}

const key = computed(() => {
  return router.currentRoute.value.fullPath
})
</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- 导航栏 -->
      <el-header height="70px" style="padding: 0">
        <el-affix>
          <HeardBar></HeardBar>
        </el-affix>
      </el-header>
      <!--内容区-->
      <el-main :style="isDarkBackground()">
        <el-row :gutter="20" class="el-row-flex">
          <el-col class="hidden-md-and-down" :lg="2" :xl="4"></el-col>
          <router-view :key="key"></router-view>
          <el-col class="hidden-md-and-down" :lg="2" :xl="4"></el-col>
        </el-row>
      </el-main>
      <el-footer height="140px" style="padding: 0">
        <!-- 底部栏 -->
        <FooterBar></FooterBar>
      </el-footer>
      <!--返回顶部-->
      <BackTop></BackTop>
    </el-container>
  </div>
</template>

<style scoped>
.common-layout {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-container {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.el-main {
  /*flex: 1;*/
  /*overflow: auto; !* 添加滚动条，如果需要 *!*/
}
.el-row-flex {
  display: flex;
  flex-wrap: wrap;
  position: relative;
  box-sizing: border-box;
  justify-content: center;
}
</style>
