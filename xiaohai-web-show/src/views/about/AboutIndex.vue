<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <h1>
      <el-icon><UserFilled /></el-icon> 关于
    </h1>
    <el-card class="box-card" shadow="hover">
      <el-space wrap size="large">
        <el-button v-for="o in 10" :key="o" text bg size="large"
          ><svg-icon icon-class="label-sign"></svg-icon> linux</el-button
        >
      </el-space>
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
