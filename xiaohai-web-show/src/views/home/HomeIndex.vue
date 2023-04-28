<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <!--电脑端-->
    <el-space class="hidden-sm-and-down" direction="vertical" fill size="large">
      <el-carousel :interval="4000" height="310px">
        <el-carousel-item v-for="item in 3" :key="item">
          <el-image src="http://localhost:8089/api/document/upload/image/1/20230401.jpg" />
          <div class="carousel-title">
            <span>这是一个神奇的数据</span>
          </div>
        </el-carousel-item>
      </el-carousel>

      <el-card class="box-card" shadow="hover" :body-style="{ padding: '10px' }">
        <el-scrollbar>
          <el-space wrap class="category">
            <router-link to="/article"> <el-button round>最新文章</el-button></router-link>
            <router-link to="/article"><el-button round>最热文章</el-button></router-link>
            <router-link to="/article"><el-button round>原创文章</el-button></router-link>
            <router-link to="/article"><el-button round>转载文章</el-button></router-link>
          </el-space>
        </el-scrollbar>
      </el-card>

      <el-card v-for="i in 10" :key="i" class="box-card box-card-hover" shadow="hover">
        <div style="display: flex; flex-direction: row">
          <el-image
            src="http://localhost:8089/api/document/upload/image/1/20230401.jpg"
            class="image"
          />
          <div
            style="
              margin-left: 18px;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            "
          >
            <el-link :underline="false" style="justify-content: left">
              <h2
                style="
                  overflow: hidden;
                  display: -webkit-box;
                  -webkit-line-clamp: 2;
                  -webkit-box-orient: vertical;
                "
              >
                <svg-icon icon-class="top" />
                啦啦啦啦啦啦，对对对
              </h2>
            </el-link>
            <span
              style="
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
              "
            >
              过去两年是人工智能赛道需要极力反思的两年。一方面，巨亏之下，明星AI企业融资难度加大，云从、商汤等，要么上市一波三折，要么股价节节败退，资本市场不看好的声音此起彼伏。，资本市场不看好的声音此起彼伏。
              另 过去两年是人工智
            </span>
            <span style="display: flex; align-items: center; justify-content: space-between">
              <span style="display: flex; align-items: center">
                <el-space size="small">
                  <el-avatar
                    size="small"
                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                  />
                  <span class="text-xs">xiaohai</span>
                  <el-tag size="small">Tag 1</el-tag>
                  <el-tag type="success" size="small">Tag 2</el-tag>
                  <el-tag type="success" size="small">Tag 2</el-tag>
                </el-space>
              </span>

              <el-space alignment="center" size="large">
                <span class="text-xs font-number text-color">
                  <svg-icon icon-class="eye-light" style="font-size: 15px" /> 100
                </span>
                <span class="text-xs font-number text-color"
                  ><svg-icon icon-class="message" style="font-size: 15px" /> 30</span
                >
                <span class="text-xs font-number text-color"
                  ><svg-icon icon-class="give-light" style="font-size: 15px" /> 20</span
                >
              </el-space>
            </span>
          </div>
        </div>
      </el-card>
    </el-space>
  </el-col>
  <!--手机端-->
  <el-space class="hidden-md-and-up" direction="vertical" fill size="large">
    <el-carousel :interval="4000" height="210px">
      <el-carousel-item v-for="item in 3" :key="item">
        <el-image src="http://localhost:8089/api/document/upload/image/1/20230401.jpg" />
        <div class="carousel-title">
          <span>这是一个神奇的数据</span>
        </div>
      </el-carousel-item>
    </el-carousel>

    <el-card class="box-card" shadow="hover" :body-style="{ padding: '10px' }">
      <el-scrollbar>
        <el-space wrap class="category">
          <router-link to="/article"> <el-button round>最新文章</el-button></router-link>
          <router-link to="/article"><el-button round>最热文章</el-button></router-link>
          <router-link to="/article"><el-button round>原创文章</el-button></router-link>
          <router-link to="/article"><el-button round>转载文章</el-button></router-link>
        </el-space>
      </el-scrollbar>
    </el-card>

    <el-card
      v-for="i in 10"
      :key="i"
      class="box-card-mobile box-card-hover"
      shadow="hover"
      :body-style="{ padding: '10px' }"
    >
      <div class="article-flex">
        <el-image
          src="http://localhost:8089/api/document/upload/image/1/20230401.jpg"
          class="image"
        />
        <div
          style="
            margin-left: 10px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
          "
        >
          <h2
            class="text-md"
            style="
              overflow: hidden;
              display: -webkit-box;
              -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;
            "
          >
            啦啦啦啦啦啦，对对对对对对对对对对对对对对对对对对
          </h2>
          <span style="display: flex; align-items: center; justify-content: space-between">
            <span style="display: flex; align-items: center">
              <el-space size="small">
                <el-avatar
                  size="small"
                  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                />
                <span class="text-xs">xiaohai</span>
              </el-space>
            </span>

            <el-space alignment="center" size="large">
              <span class="text-xs font-number"
                ><svg-icon icon-class="give-light" style="font-size: 15px" /> 20</span
              >
            </el-space>
          </span>
        </div>
      </div>
    </el-card>
  </el-space>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
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
.box-card {
  max-height: 180px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.box-card-mobile {
  max-height: 120px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
.image {
  border-radius: 10px;
  min-width: 238px;
  height: 140px;
}

.box-card-hover:hover {
  filter: drop-shadow(0 0 0.5em rgb(252, 191, 191));
  transform: scale(1.01);
}
/*手机端*/
@media (max-width: 992px) {
  .image {
    border-radius: 10px;
    min-width: 149px;
    height: 92px;
  }
}
.image:hover {
  transform: scale(1.1);
}
.category {
  height: 40px;
}
.article-flex {
  display: flex;
  flex-direction: row;
}
.article-right-flex {
  display: flex;
  flex-direction: row;
}
.carousel-title {
  cursor: pointer;
  position: absolute;
  z-index: 10;
  bottom: 40px;
  height: 40px;
  width: 100%;
  line-height: 40px;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
  font-size: 22px;
  color: #ecf8ff;
  font-weight: bold;
}
</style>
