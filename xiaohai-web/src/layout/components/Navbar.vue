<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <!--        <search id="header-search" class="right-menu-item" />-->

        <el-tooltip content="全屏" effect="dark" placement="bottom">
          <ScreenFull id="screen-full" class="right-menu-item hover-effect" />
        </el-tooltip>

        <el-tooltip content="门户地址" effect="dark" placement="bottom">
          <Website id="website" class="right-menu-item hover-effect" />
        </el-tooltip>

        <Message class="right-menu-item hover-effect" />

      </template>

      <!--      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">-->
      <!--        <div class="avatar-wrapper">-->
      <!--          <el-avatar v-if="$store.getters.avatar" shape="square" :src="$store.getters.avatar" />-->
      <!--          <el-avatar v-else shape="square"> {{ $store.getters.name }} </el-avatar>-->
      <!--        </div>-->
      <!--        <el-dropdown-menu slot="dropdown">-->
      <!--          <router-link to="/user/index">-->
      <!--            <el-dropdown-item> 个人信息</el-dropdown-item>-->
      <!--          </router-link>-->
      <!--          <el-dropdown-item divided @click.native="logout">-->
      <!--            <span style="display:block;">退出登录</span>-->
      <!--          </el-dropdown-item>-->
      <!--        </el-dropdown-menu>-->
      <!--      </el-dropdown>-->
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ScreenFull from '@/components/Screenfull'
import Search from '@/components/HeaderSearch'
import Website from '@/components/DotCode/Website'
import Message from '@/components/Message/inform'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ScreenFull,
    Website,
    Search,
    Message
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  padding-top: 20px;
  height: 70px;
  overflow: hidden;
  position: relative;
  /* 主要内容 */
  background-color: rgba(255, 255, 255, 0.1);
  /* 模糊大小就是靠的blur这个函数中的数值大小 */
  backdrop-filter: blur(5px);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;
  }

  .breadcrumb-container {
    float: left;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    margin-right: 30px;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 12px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        //transition: background .3s;

        &:hover {
          //background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 20px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
