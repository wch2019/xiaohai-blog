<template>
  <!--左内容区-->
  <el-col :md="24" :lg="15" :xl="13">
    <el-space direction="vertical" fill size="large" style="width: 100%">
      <el-carousel :interval="4000" height="310px">
        <el-carousel-item v-for="item in 3" :key="item">
          <h3 text="2xl" justify="center">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
      <el-card class="box-card" shadow="hover">
        <el-space wrap size="large">
          <el-button round>最新文章</el-button>
          <el-button round>最热文章</el-button>
          <el-button round>原创文章</el-button>
          <el-button round>转载文章</el-button>
        </el-space>
      </el-card>

      <el-card
        v-for="i in 10"
        :key="i"
        class="box-card box-card-hover"
        shadow="hover"
        style="height: 200px"
      >
        <el-row :gutter="20">
          <el-col span="6">
            <div style="height: 160px">
              <img
                src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
                class="image"
              />
            </div>
          </el-col>
          <el-col span="18">
            <h1>啦啦啦啦啦啦，对对对</h1>
            <span>啦啦啦啦啦啦，对对对</span>
            <div>
              <el-avatar
                size="default"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
              <el-space alignment="center">
                <svg-icon icon-class="eye-light" style="font-size: 20px"></svg-icon>
                <svg-icon icon-class="give-light" style="font-size: 20px"></svg-icon>
                <svg-icon icon-class="message" style="font-size: 20px"></svg-icon>
              </el-space>
            </div>
          </el-col>
        </el-row>

        <!--        <div>-->
        <!--          <a href="https://vitejs.dev" target="_blank">-->
        <!--            <img src="/vite.svg" class="logo" alt="Vite logo" />-->
        <!--          </a>-->
        <!--          <a href="https://vuejs.org/" target="_blank">-->
        <!--            <img src="@/assets/icons/svg/vue.svg" class="logo vue" alt="Vue logo" />-->
        <!--          </a>-->
        <!--        </div>-->
        <!--        <HelloWorld msg="Vite + Vue" />-->
      </el-card>
    </el-space>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :md="8" :lg="7" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import HelloWorld from '@/components/HelloWorld.vue'
import RightSide from '@/components/layouts/RightSide.vue'

const checked = ref(false)
const onChange = (status: boolean) => {
  checked.value = status
}
</script>

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
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.image {
  border-radius: 10px;
  height: 100%;
}
.box-card-hover:hover {
  filter: drop-shadow(0 0 2em rgb(252, 191, 191));
  transform: scale(1.01);
}
</style>
