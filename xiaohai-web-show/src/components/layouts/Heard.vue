<template>
  <el-card class="box-card" shadow="hover">
    <el-row :gutter="20">
      <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
      <!--小于1200尺寸隐藏-->
      <el-col class="hidden-md-and-down" :md="16" :lg="15" :xl="13">
        <div class="nav-title">
          <div class="blog-title">
            <router-link to="/">DotCode</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/"> 首页</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/links"> 分类</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/links"> 标签</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/links"> 归档</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/message"> 留言</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/message"> 友链</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/about"> 关于</router-link>
          </div>
        </div>
      </el-col>
      <el-col class="hidden-md-and-down" :md="8" :lg="7" :xl="5">
        <div class="nav-title">
          <el-col :span="8"></el-col>
          <el-col :span="4">
            <div class="menus-item">
              <el-icon size="18">
                <Search />
              </el-icon>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="menus-item">
              <el-icon size="18" @click="toggleDark()">
                <Sunny v-if="value" />
                <Moon v-else />
              </el-icon>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="menus-item">
              <el-avatar
                size="default"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
            </div>
          </el-col>
        </div>
      </el-col>
      <!--小于1200尺寸应用-->
      <el-col class="hidden-lg-and-up">
        <div class="nav-title">
          <el-col :span="18">
            <div class="blog-title">
              <router-link to="/">DotCode</router-link>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="menus-item">
              <el-icon size="18">
                <Search />
              </el-icon>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="menus-item">
              <el-icon size="18" @click="toggleDark()">
                <Sunny v-if="value" />
                <Moon v-else />
              </el-icon>
            </div>
          </el-col>
          <el-col :span="2">
            <div class="menus-item">
              <el-icon size="18" @click="drawer = true"><Fold /></el-icon>
            </div>
          </el-col>
        </div>
      </el-col>
      <el-col class="hidden-md-and-down" :lg="1" :xl="3"></el-col>
    </el-row>
  </el-card>
  <!-- 侧边菜单-->
  <el-drawer v-model="drawer" direction="ltr" :with-header="false">
    <router-link to="/">
      <el-card class="drawer-menus"> 首 页 </el-card>
    </router-link>
    <router-link to="/">
      <el-card class="drawer-menus"> 分 类 </el-card>
    </router-link>
    <router-link to="/links">
      <el-card class="drawer-menus"> 标 签 </el-card>
    </router-link>
    <router-link to="/links">
      <el-card class="drawer-menus"> 归 档 </el-card>
    </router-link>
    <router-link to="/message">
      <el-card class="drawer-menus"> 留 言 </el-card>
    </router-link>
    <router-link to="/message">
      <el-card class="drawer-menus"> 友 链 </el-card>
    </router-link>
    <router-link to="/about">
      <el-card class="drawer-menus"> 关 于 </el-card>
    </router-link>
  </el-drawer>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import { toggleDark, isDark } from '@/utils/dark'

const value = ref(isDark.value)
const drawer = ref(false)
</script>

<style scoped>
.box-card {
  /*去掉弧度*/
  border-radius: 0;
  border: 1px solid transparent;
  /* 主要内容 */
  background-color: rgba(255, 255, 255, 0.1);
  /* 模糊大小就是靠的blur这个函数中的数值大小 */
  backdrop-filter: blur(5px);
}

.blog-title,
.nav-title {
  display: flex;
  align-items: center;
  height: 100%;
}

.blog-title a {
  width: 150px;
  font-size: 28px;
  font-weight: bold;
  background: linear-gradient(to bottom, red, #fcbfbf);
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
}

.menus-item {
  position: relative;
  display: inline-block;
  margin: 0 0 0 0.875rem;
}

.menus-item a {
  display: block;
  color: #fd5a5a;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  text-shadow: 2px 2px 5px #fcbfbf;
  transition: all 0.2s;
}

.menu-btn:hover:after {
  width: 100%;
}

.menus-item a:after {
  position: absolute;
  bottom: -5px;
  left: 0;
  z-index: -1;
  width: 0;
  height: 3px;
  background-color: #80c8f8;
  content: '';
  transition: all 0.3s ease-in-out;
}

.menus-item:hover {
  display: block;
  transition: all 0.2s;
  color: #409eff;
  text-shadow: 2px 2px 5px #409eff;
}
.drawer-menus {
  border-radius: 8px;
  border: 1px solid transparent;
  margin-top: 10px;
  display: block;
  color: #fd5a5a;
  text-align: center;
  text-decoration: none;
  text-shadow: 2px 2px 5px #fcbfbf;
  transition: all 0.2s;
}
</style>
