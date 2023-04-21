<script setup lang="ts">
import { ref } from 'vue'
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
const checked = ref(false)
const onChange = (status: boolean) => {
  checked.value = status
}
</script>

<template>
  <div id="app">
    <!-- 导航栏 -->
    <el-affix>
      <HeardBar></HeardBar>
    </el-affix>
    <!--内容区-->
    <el-main :style="isDarkBackground()" style="height: 100%">
      <el-row :gutter="20">
        <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
        <el-col :md="24" :lg="15" :xl="13">
          <el-space direction="vertical" fill size="large" style="width: 100%">
            <el-carousel :interval="4000" height="400px">
              <el-carousel-item v-for="item in 3" :key="item">
                <h3 text="2xl" justify="center">{{ item }}</h3>
              </el-carousel-item>
            </el-carousel>

            <el-card class="box-card" shadow="hover">
              <el-check-tag :checked="checked" @change="onChange" effect="#99a9bf"
                >Toggle me</el-check-tag
              ><el-button round>最新文章</el-button>
            </el-card>

            <el-card v-for="i in 2" :key="i" class="box-card" shadow="hover">
              <router-view></router-view>
            </el-card>
          </el-space>
        </el-col>
        <el-col class="hidden-md-and-down" :md="8" :lg="7" :xl="5">
          <el-space direction="vertical" fill size="large" style="width: 100%">
            <el-card v-for="i in 2" :key="i" class="box-card" shadow="hover">
              <div v-for="o in 4" :key="o" class="text item">
                {{ 'List item ' + o }}
              </div>
            </el-card>
            <el-card v-for="i in 2" :key="i" class="box-card" shadow="hover">
              <template #header>
                <div class="card-header">
                  <span>Card name</span>
                  <el-button class="button" text>Operation button</el-button>
                </div>
              </template>
              <div v-for="o in 4" :key="o" class="text item">
                {{ 'List item ' + o }}
              </div>
            </el-card>
          </el-space>
        </el-col>
        <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
      </el-row>
    </el-main>
    <!-- 底部栏 -->
    <FooterBar></FooterBar>
    <!--返回顶部-->
    <BackTop></BackTop>
  </div>
</template>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
.box-card {
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
