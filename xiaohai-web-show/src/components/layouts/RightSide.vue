<template>
  <el-space direction="vertical" fill size="large" style="width: 100%">
    <el-card class="box-card" shadow="hover" style="height: 400px"> </el-card>
    <el-card class="box-card" shadow="hover">
      <div v-for="o in 4" :key="o" class="text item">
        {{ 'List item ' + o }}
      </div>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>热门文章</span>
        </div>
      </template>
      <div>啦啦啦</div>
      <div>啦啦啦</div>
      <div>啦啦啦</div>
      <div>啦啦啦</div>
      <div>啦啦啦</div>
      <div>啦啦啦</div>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>标签云</span>
        </div>
      </template>
      啦啦啦
    </el-card>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span>标签云</span>
        </div>
      </template>
      啦啦啦
    </el-card>
  </el-space>
</template>

<script>
export default {
  name: 'RightSide'
}
</script>

<style scoped>
.box-card {
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
