<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <h1 class="flex-center">
      <el-icon><UserFilled /></el-icon> 关于
    </h1>
    <el-card class="box-card" shadow="hover"
      >欢迎来到我的博客！这里是一个分享知识、记录生活、思考未来的平台。在这里，我将和大家分享我对于生活、职场、学习等各个方面的见解和心得体会，希望能够为大家提供有价值的帮助和启发。同时，我也会在博客中记录我的生活点滴、分享我的兴趣爱好、思考我的未来规划，希望能够通过这种方式与大家建立更加亲近的关系。感谢您的光临，希望我的博客能够给您带来不一样的体验和收获！
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import RightSide from '@/components/layouts/RightSide.vue'
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
