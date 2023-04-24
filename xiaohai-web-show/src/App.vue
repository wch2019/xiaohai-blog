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
  <el-affix :offset="0.0001">
    <HeardBar></HeardBar>
  </el-affix>
  <!--内容区-->
  <el-main :style="isDarkBackground()">
    <el-row
      :gutter="20"
      style="
        display: flex;
        flex-wrap: wrap;
        position: relative;
        box-sizing: border-box;
        justify-content: center;
      "
    >
      <el-col class="hidden-md-and-down" :lg="2" :xl="4"></el-col>
      <router-view></router-view>
      <el-col class="hidden-md-and-down" :lg="2" :xl="4"></el-col>
    </el-row>
  </el-main>
  <!-- 底部栏 -->
  <FooterBar></FooterBar>
  <!--返回顶部-->
  <BackTop></BackTop>
</template>

<style scoped></style>
