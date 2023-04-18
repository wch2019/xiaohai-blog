<template>
  <div  class=" banner_content">
    <el-menu
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
    >
      <el-menu-item index="0">DotCode</el-menu-item>
      <el-menu-item index="1">Processing Center</el-menu-item>
      <div class="flex-grow" />
      <el-sub-menu index="2">
        <template #title>Workspace</template>
        <el-menu-item index="2-1">item one</el-menu-item>
        <el-menu-item index="2-2">item two</el-menu-item>
        <el-menu-item index="2-3">item three</el-menu-item>
        <el-sub-menu index="2-4">
          <template #title>item four</template>
          <el-menu-item index="2-4-1">item one</el-menu-item>
          <el-menu-item index="2-4-2">item two</el-menu-item>
          <el-menu-item index="2-4-3">item three</el-menu-item>
        </el-sub-menu>

      </el-sub-menu>
      <el-menu-item h="full" @click="toggleDark()">
        <el-icon size="20"><Sunny /></el-icon>
        <el-icon size="20"><Moon /></el-icon>
      </el-menu-item>
    </el-menu>
  </div>

</template>

<script lang="ts" setup>
import { toggleDark } from "@/utils/dark";


</script>

<style>
.flex-grow {
  flex-grow: 1;
}
.banner_content::before{
  　　content: "";
  　　position: absolute;
  　　top: -10px;left: 0;bottom: 0;right: 0;
  　　filter: blur(20px);
  　　margin: -30px;
}
</style>
