<template>
  <!-- 导航栏 -->
  <el-affix>
    <Heard></Heard>
  </el-affix>
  <!--内容区-->
  <el-main :style="isDarkBackground()" style="height: 100%">
    <el-row :gutter="20">
      <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
      <el-col :md="24" :lg="15" :xl="13">
        <el-carousel :interval="4000" height="400px">
          <el-carousel-item v-for="item in 6" :key="item">
            <h3 text="2xl" justify="center">{{ item }}</h3>
          </el-carousel-item>
        </el-carousel>
        <h2>最新文章</h2>
        <div style="flex: 1">
          <a href="https://vitejs.dev" target="_blank">
            <img src="/vite.svg" class="logo" alt="Vite logo" />
          </a>
          <a href="https://vuejs.org/" target="_blank">
            <img src="@/assets/vue.svg" class="logo vue" alt="Vue logo" />
          </a>
          <HelloWorld msg="Vite + Vue" />
          <Axios></Axios>
        </div>
      </el-col>
      <el-col class="hidden-md-and-down" :md="8" :lg="7" :xl="5">
        <el-space direction="vertical" fill size="large" style="width: 100%">
          <el-card v-for="i in 2" :key="i" class="box-card" shadow="hover" style="height: 40%">
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
  <Footer></Footer>
  <!--返回顶部-->
  <BackTop></BackTop>
</template>

<script setup lang="ts">
import HelloWorld from '@/components/HelloWorld.vue'
import Axios from '@/views/axios.vue'
import Heard from '@/components/layouts/Heard.vue'
import Footer from '@/components/layouts/Footer.vue'
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

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}

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
</style>
