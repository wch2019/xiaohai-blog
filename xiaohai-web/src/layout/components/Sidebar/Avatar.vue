<template>
  <div>
    <el-dropdown trigger="click" placement="right">
      <div class="avatar-wrapper">
        <el-avatar v-if="$store.getters.avatar" :src="$store.getters.avatar" />
        <el-avatar v-else> {{ $store.getters.name }}</el-avatar>
        {{ $store.getters.name }}
        <i class="el-icon-setting" />
      </div>
      <el-dropdown-menu slot="dropdown">
        <router-link to="/user/index">
          <el-dropdown-item> 个人信息</el-dropdown-item>
        </router-link>
        <el-dropdown-item divided @click.native="logout">
          <span style="display:block;">退出登录</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <div class="version">{{ title }}</div>
  </div>
</template>

<script>
import variables from '@/styles/variables.scss'

export default {
  name: 'SidebarAvatar',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      title: 'DotCode 1.0.0'
    }
  },
  computed: {
    variables() {
      return variables
    },
    sideTheme() {
      return this.$store.state.settings.sideTheme
    }
  }
}
</script>

<style lang="scss" scoped>

.avatar-wrapper {

}
.version{
  font-size: 12px;
  padding: 15px 10px;
  color: #999;
  text-align: center;
  margin: 0;
}
</style>
