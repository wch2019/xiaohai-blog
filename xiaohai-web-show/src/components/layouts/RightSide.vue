<template>
  <el-space direction="vertical" fill size="large" style="width: 100%">
    <el-card class="box-card" shadow="hover" :body-style="{ padding: '0px', height: '400px' }">
      <img src="http://localhost:8089/api/document/upload/image/1/20230401.jpg" class="image" />
      <div style="padding: 14px">
        <span>Yummy hamburger</span>
        <div class="bottom">
          <time class="time">{{ currentDate }}</time>
          <el-button text class="button">Operating</el-button>
        </div>
      </div>
    </el-card>
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
  height: 100%;
}
.image {
  width: 100%;
  display: block;
}
.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}
</style>
