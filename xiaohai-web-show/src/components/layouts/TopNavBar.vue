<template>
  <nav>
    <ul>
      <li>
        <div class="font">dotcode</div>
      </li>
      <li><a href="#">首页</a></li>
      <li><a href="#">技术</a></li>
      <li><a href="#">设计</a></li>
      <li><a href="#">运营</a></li>
      <li><a href="#">生活</a></li>
      <li><a href="#">关于我</a></li>
    </ul>
  </nav>
  <!--  <el-card class="box-card">-->
  <!--    <div class="float-left blog-title">-->
  <!--      &lt;!&ndash;      <router-link to="/">&ndash;&gt;-->
  <!--      <div class="font">dotcode</div>-->
  <!--      &lt;!&ndash;      </router-link>&ndash;&gt;-->
  <!--    </div>-->
  <!--    <div @click="toggleDark()">-->
  <!--      <el-icon size="20">-->
  <!--        <Sunny />-->
  <!--      </el-icon>-->
  <!--      <el-icon size="20">-->
  <!--        <Moon />-->
  <!--      </el-icon>-->
  <!--    </div>-->
  <!--    <el-menu-->
  <!--      background-color="rgba(255, 255, 255, 0.1)"-->
  <!--      style="backdrop-filter: blur(5px); border: 1px solid transparent"-->
  <!--      class="el-menu-demo"-->
  <!--      mode="horizontal"-->
  <!--      :ellipsis="false"-->
  <!--    >-->
  <!--      <el-menu-item index="0">LOGO</el-menu-item>-->
  <!--      <el-menu-item index="4" @click="toggleDark()">-->
  <!--        <el-icon size="20">-->
  <!--          <Sunny />-->
  <!--        </el-icon>-->
  <!--        <el-icon size="20">-->
  <!--          <Moon />-->
  <!--        </el-icon>-->
  <!--      </el-menu-item>-->
  <!--      <div class="flex-grow" />-->
  <!--      <el-menu-item index="1">Processing Center</el-menu-item>-->
  <!--      <el-sub-menu index="2">-->
  <!--        <template #title>Workspace</template>-->
  <!--        <el-menu-item index="2-1">item one</el-menu-item>-->
  <!--        <el-menu-item index="2-2">item two</el-menu-item>-->
  <!--        <el-menu-item index="2-3">item three</el-menu-item>-->
  <!--        <el-sub-menu index="2-4">-->
  <!--          <template #title>item four</template>-->
  <!--          <el-menu-item index="2-4-1">item one</el-menu-item>-->
  <!--          <el-menu-item index="2-4-2">item two</el-menu-item>-->
  <!--          <el-menu-item index="2-4-3">item three</el-menu-item>-->
  <!--        </el-sub-menu>-->
  <!--      </el-sub-menu>-->
  <!--    </el-menu>-->
  <!--  </el-card>-->
</template>

<script lang="ts" setup>
import { toggleDark } from '@/utils/dark'
</script>

<style scoped>
.box-card {
  border: 1px solid transparent;
  /* 主要内容 */
  background-color: rgba(255, 255, 255, 0.5);
  /* 模糊大小就是靠的blur这个函数中的数值大小 */
  backdrop-filter: blur(5px);
}

a:hover {
  filter: drop-shadow(0 0 2em rgb(252, 191, 191));
}

.font {
  font-size: 40px;
  /*color: #50bfff !important;*/
  background: linear-gradient(to bottom, red, #fd5a5aff);
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
}

nav {
  /* 主要内容 */
  background-color: rgba(255, 255, 255, 0.5);
  /* 模糊大小就是靠的blur这个函数中的数值大小 */
  backdrop-filter: blur(5px);
  box-shadow: 1px 1px 1px 1px #fcbfbfff;
  padding: 10px;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

li {
  float: left;
}

a {
  display: block;
  color: #333;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

a:hover {
  background-color: rgb(252, 191, 191);
}
</style>
