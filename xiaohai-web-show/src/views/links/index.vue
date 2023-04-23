<template>
  <!--左内容区-->
  <el-col :lg="12" :xl="10">
    <el-card class="box-card" shadow="hover">
      <el-timeline>
        <el-timeline-item timestamp="2018/4/12" placement="top">
          <el-card>
            <h4>Update Github template</h4>
            <p>Tom committed 2018/4/12 20:46</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item timestamp="2018/4/3" placement="top">
          <el-card>
            <h4>Update Github template</h4>
            <p>Tom committed 2018/4/3 20:46</p>
          </el-card>
        </el-timeline-item>
        <el-timeline-item timestamp="2018/4/2" placement="top">
          <el-card>
            <h4>Update Github template</h4>
            <p>Tom committed 2018/4/2 20:46</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="4">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import RightSide from '@/components/layouts/RightSide.vue'
</script>

<style scoped></style>
