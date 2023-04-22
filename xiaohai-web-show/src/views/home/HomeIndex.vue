<template>
  <!--左内容区-->
  <el-col :lg="12" :xl="10">
    <!--电脑端-->
    <el-space direction="vertical" fill size="large">
      <el-carousel :interval="4000" height="310px">
        <el-carousel-item v-for="item in 3" :key="item">
          <h3 text="2xl" justify="center">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
      <el-card class="box-card" shadow="hover">
        <el-space wrap>
          <el-button round>最新文章</el-button>
          <el-button round>最热文章</el-button>
          <el-button round>原创文章</el-button>
          <el-button round>转载文章</el-button>
        </el-space>
      </el-card>

      <el-card v-for="i in 10" :key="i" class="box-card box-card-hover" shadow="hover">
        <div style="display: flex; flex-direction: row">
          <el-image
            src="http://localhost:8089/api/document/upload/image/1/20230401.jpg"
            class="image"
          />
          <div style="margin-left: 18px">
            <h4>啦啦啦啦啦啦，对对对</h4>
            <span>
              过去两年是人工智能赛道需要极力反思的两年。一方面，巨亏之下，明星AI企业融资难度加大，云从、商汤等，要么上市一波三折，要么股价节节败退，资本市场不看好的声音此起彼伏。，资本市场不看好的声音此起彼伏。
              另 过去两年是人工智...
            </span>
            <span style="display: flex; align-items: center; justify-content: space-between">
              <span style="display: flex; align-items: center">
                <el-avatar
                  size="default"
                  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                />
                xiaohai
              </span>

              <el-space alignment="center" size="large">
                <span> <svg-icon icon-class="eye-light" style="font-size: 20px" /> 100 </span>
                <span><svg-icon icon-class="give-light" style="font-size: 20px" /> 20</span>
                <span><svg-icon icon-class="message" style="font-size: 20px" /> 30</span>
              </el-space>
            </span>
          </div>
        </div>
      </el-card>
    </el-space>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="4">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import RightSide from '@/components/layouts/RightSide.vue'

const checked = ref(false)
const onChange = (status: boolean) => {
  checked.value = status
}
</script>

<style scoped>
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

.box-card {
  max-height: 180px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.image {
  border-radius: 10px;
  height: 140px;
}
.box-card-hover:hover {
  filter: drop-shadow(0 0 2em rgb(252, 191, 191));
  transform: scale(1.01);
}
</style>
