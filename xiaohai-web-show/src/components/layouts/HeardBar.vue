<template>
  <el-card class="box-card" shadow="hover" body-style="padding:10px">
    <el-row :gutter="20">
      <el-col class="hidden-md-and-down" :lg="3" :xl="4"></el-col>
      <!--小于1200尺寸隐藏-->
      <el-col class="hidden-md-and-down" :md="16" :lg="14" :xl="11">
        <div class="nav-title">
          <div class="blog-title">
            <router-link to="/">DotCode</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/"> 首页</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/category"> 分类</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/tags"> 标签</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/back"> 归档</router-link>
          </div>
          <div class="menus-item">
            <router-link class="menu-btn" to="/message"> 留言</router-link>
          </div>
          <!--          <div class="menus-item">-->
          <!--            <router-link class="menu-btn" to="/links"> 友链</router-link>-->
          <!--          </div>-->
          <div class="menus-item">
            <router-link class="menu-btn" to="/about"> 关于</router-link>
          </div>
        </div>
      </el-col>
      <el-col class="hidden-md-and-down" :md="8" :lg="4" :xl="5">
        <div style="display: flex; justify-content: space-evenly; align-items: center">
          <div class="menus-item">
            <SearchModel></SearchModel>
          </div>
          <div class="menus-item">
            <svg-icon
              @click="isLight"
              v-if="value"
              icon-class="lightning-dark"
              class="lightning-dark"
              style="font-size: 20px"
            ></svg-icon>
            <svg-icon
              @click="isLight"
              v-else
              icon-class="lightning-light"
              class="lightning-light"
              style="font-size: 18px"
            ></svg-icon>
          </div>
          <div class="menus-item">
            <el-dropdown>
              <el-avatar
                size="default"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
              <template #dropdown>
                <el-dropdown-menu v-if="hasToken === undefined">
                  <el-dropdown-item @click="adminClick"> 登 录 </el-dropdown-item>
                </el-dropdown-menu>
                <el-dropdown-menu v-else>
                  <el-dropdown-item @click="manageClick">后台管理</el-dropdown-item>
                  <el-dropdown-item divided @click="exit">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-col>
      <!--小于1200尺寸应用-->
      <el-col class="hidden-lg-and-up">
        <div class="nav-title">
          <el-col :span="15">
            <div class="blog-title">
              <router-link to="/">DotCode</router-link>
            </div>
          </el-col>
          <el-col :span="9">
            <div style="display: flex; justify-content: space-evenly; align-items: center">
              <div class="menus-item">
                <SearchModel></SearchModel>
              </div>
              <div class="menus-item">
                <svg-icon
                  @click="isLight"
                  v-if="value"
                  icon-class="lightning-dark"
                  class="lightning-dark"
                  style="font-size: 20px"
                ></svg-icon>
                <svg-icon
                  @click="isLight"
                  v-else
                  icon-class="lightning-light"
                  class="lightning-light"
                  style="font-size: 20px"
                ></svg-icon>
              </div>
              <div class="menus-item">
                <svg-icon
                  @click="drawer = true"
                  icon-class="more"
                  class="more"
                  style="font-size: 20px"
                ></svg-icon>
              </div>
            </div>
          </el-col>
        </div>
      </el-col>
      <el-col class="hidden-md-and-down" :lg="3" :xl="4"></el-col>
    </el-row>
  </el-card>
  <!-- 侧边菜单-->
  <el-drawer
    v-model="drawer"
    direction="ltr"
    size="40%"
    :style="isDarkBackground()"
    :with-header="false"
  >
    <el-card class="drawer-menus" shadow="hover" @click="cancelClick('/')"> 首 页</el-card>
    <el-card class="drawer-menus" shadow="hover" @click="cancelClick('/category')">分 类</el-card>
    <el-card class="drawer-menus" shadow="hover" @click="cancelClick('/tags')"> 标 签</el-card>
    <el-card class="drawer-menus" shadow="hover" @click="cancelClick('/back')"> 归 档</el-card>
    <el-card class="drawer-menus" shadow="hover" @click="cancelClick('/message')"> 留 言</el-card>
    <!--    <el-card class="drawer-menus" shadow="hover" @click="$router.push('/links')"> 友 链</el-card>-->
    <el-card class="drawer-menus" shadow="hover" @click="cancelClick('/about')"> 关 于</el-card>
  </el-drawer>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { toggleDark, isDark } from '@/utils/dark'
import { getToken, removeToken } from '@/utils/auth'
import { logout } from '@/api/user'
import SearchModel from '@/components/seach/SearchModel.vue'

const value = ref(isDark.value)
const drawer = ref(false)

// 亮暗
const isLight = () => {
  toggleDark()
  value.value = isDark.value
}

// 内容背景色根据主题色调整
function isDarkBackground() {
  if (!isDark.value) {
    return 'background-color: #e9e9eb'
  }
  return ''
}
const router = useRouter()
function cancelClick(path: any) {
  router.push(path)
  drawer.value = false
}
// 跳转登录
function adminClick() {
  window.open(
    `${import.meta.env.VITE_APP_BLOG_WEB_API}/#/login?show=${router.currentRoute.value.fullPath}`,
    '_self'
  )
}
// 跳转管理页
function manageClick() {
  window.open(`${import.meta.env.VITE_APP_BLOG_WEB_API}/#/dashboard`, '_self')
}

// 获取token
const hasToken = getToken()
console.log(hasToken, 'aaaaa')
// 退出登录
function exit() {
  logout().then(() => {
    removeToken()
    // 如果发生变化重新载入
    window.location.reload()
  })
}
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
  background-color: #fcbfbf;
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
