<template>
  <el-space direction="vertical" fill size="large" style="width: 100%">
    <el-card
      class="box-card"
      shadow="hover"
      :body-style="{ padding: '0px', height: '380px' }"
      style="position: relative"
    >
      <!--      <div class="test">-->
      <img src="http://localhost:8089/api/document/upload/image/1/20230401.jpg" class="image" />
      <!--      </div>-->

      <div style="display: flex; padding: 10px; justify-content: center; text-align: center">
        <el-avatar
          src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          class="panThumb"
        />

        <el-space direction="vertical" :size="'large'" fill style="margin-top: 20px; width: 80%">
          <h3>xiaohai</h3>
          <div>用一点点代码改变生活</div>
          <div style="display: inline-flex; vertical-align: top; justify-content: space-between">
            <el-space fill direction="vertical">
              <div class="text-sm text-color">文章</div>
              <div class="text-xl font-number">7</div>
            </el-space>
            <el-space fill direction="vertical">
              <div class="text-sm text-color">分类</div>
              <div class="text-xl font-number">4</div>
            </el-space>
            <el-space fill direction="vertical" size="small">
              <div class="text-sm text-color">标签</div>
              <div class="text-xl font-number">14</div>
            </el-space>
            <el-space fill direction="vertical" size="small">
              <div class="text-sm text-color">评论</div>
              <div class="text-xl font-number">2</div>
            </el-space>
          </div>
        </el-space>
      </div>
    </el-card>
    <el-card class="box-card" shadow="hover" style="text-align: center"> {{ a }} </el-card>

    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="hot"></svg-icon> 热门</h2>
      </template>
      <div v-for="o in 5" :key="o" style="display: flex; margin-top: 16px">
        <div style="display: flex; flex-direction: column; justify-content: space-between">
          <el-link :underline="false" style="justify-content: left">
            <span
              style="
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
              "
              >啦啦啦啦啦啦，对对对啦啦啦啦啦啦啦，对对对啦
            </span>
          </el-link>
          <span class="text-xs font-number text-color">2023-04-23</span>
        </div>
        <el-image
          src="http://localhost:8089/api/document/upload/image/1/20230401.jpg"
          style="margin-left: 10px; border-radius: 10px; height: 80px; min-width: 140px"
          class="image-hot"
        />
      </div>
    </el-card>

    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="tags"></svg-icon> 标签</h2>
      </template>
      <el-space wrap size="small">
        <el-button v-for="o in 10" :key="o" text bg
          ><svg-icon icon-class="label-sign"></svg-icon> linux</el-button
        >
      </el-space>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <template #header>
        <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="link"></svg-icon> 友链</h2>
      </template>
      <el-space wrap size="small">
        <el-link v-for="o in 10" :key="o" href="https://element-plus.org" target="_blank"
          >这是连接啊</el-link
        >
      </el-space>
    </el-card>
  </el-space>
</template>

<script setup lang="ts">
function greetings() {
  const date = new Date()
  if (date.getHours() >= 6 && date.getHours() < 8) {
    return '晨起披衣出草堂，轩窗已自喜微凉🌅！'
  }
  if (date.getHours() >= 8 && date.getHours() < 12) {
    return '上午好🌞！'
  }
  if (date.getHours() >= 12 && date.getHours() < 18) {
    return '下午好☕！'
  }
  if (date.getHours() >= 18 && date.getHours() < 24) {
    return '晚上好🌃！'
  }
  if (date.getHours() >= 0 && date.getHours() < 6) {
    return '偷偷向银河要了一把碎星，只等你闭上眼睛撒入你的梦中，晚安🌛！'
  }
  return ''
}
const a = greetings()
</script>

<style scoped>
.box-card {
  border-radius: 10px;
  border: 1px solid transparent;
  height: 100%;
}
.image {
  width: 100%;
  height: 170px;
  display: block;
}
.test::before {
  content: '';
  position: absolute;
  top: 90px;
  left: 0;
  width: 100%;
  height: 30px;
  z-index: 2;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0), #fff);
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
.panThumb {
  height: 100px !important;
  width: 100px !important;
  position: absolute !important;
  top: 115px;
  border: 5px solid #ffffff;
}
.image-hot:hover {
  transform: scale(1.1);
}

.image:hover {
  transform: scale(1.1);
}
</style>
