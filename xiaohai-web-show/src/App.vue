<script setup lang="ts">
import HeardBar from '@/components/layouts/HeardBar.vue'
import FooterBar from '@/components/layouts/FooterBar.vue'
import BackTop from '@/components/layouts/BackTop.vue'
import { isDark } from '@/utils/dark'

// 内容背景色根据主题色调整
function isDarkBackground() {
  if (!isDark.value) {
    return 'background-color: #f7f7fb'
  }
  return ''
}
</script>

<template>
  <!-- 导航栏 -->
  <el-affix>
    <HeardBar></HeardBar>
  </el-affix>
  <!--内容区-->
  <el-main :style="isDarkBackground()">
    <el-row :gutter="20">
      <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
      <router-view></router-view>
      <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
    </el-row>
  </el-main>
  <!-- 底部栏 -->
  <FooterBar></FooterBar>
  <!--返回顶部-->
  <BackTop></BackTop>
</template>

<style scoped></style>
