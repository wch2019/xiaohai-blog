<script setup lang="ts">
import HelloWorld from './components/HelloWorld.vue'
import axios from '@/views/axios.vue'
import Axios from "@/views/axios.vue";
import TopNavBar from "@/components/layouts/TopNavBar.vue";
import Footer from "@/components/layouts/Footer.vue";


</script>

<template>

  <!-- 导航栏 -->
  <el-affix>
    <TopNavBar></TopNavBar>
  </el-affix>

<!--  <el-main style="">-->
    <div style="flex: 1;">
      <a href="https://vitejs.dev" target="_blank">
        <img src="/vite.svg" class="logo" alt="Vite logo"/>
      </a>
      <a href="https://vuejs.org/" target="_blank">
        <img src="./assets/vue.svg" class="logo vue" alt="Vue logo"/>
      </a>
      <HelloWorld msg="Vite + Vue"/>
      <axios></axios>
      <axios></axios>
    </div>

<!--  </el-main>-->
  <!-- 底部栏 -->
  <Footer></Footer>
</template>

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

.el-container{
  height: 100vh;
  display: flex;
  flex-direction: column;
}
.el-main{
  flex: 1;
}
.el-footer{
  margin-top: auto;
}
</style>
