<template>
  <div>
    <div v-if="collapse" class="sidebar-avatar">
      <router-link to="/user/index">
        <el-avatar v-if="$store.getters.avatar" :src="$store.getters.avatar" />
        <el-avatar v-else> {{ $store.getters.name }}</el-avatar>
      </router-link>
    </div>
    <div v-else class="avatar-wrapper">
      <div>
        <router-link to="/user/index">
          <el-avatar v-if="$store.getters.avatar" :src="$store.getters.avatar" />
          <el-avatar v-else> {{ $store.getters.name }}</el-avatar>
        </router-link>
      </div>
      <span class="name">{{ $store.getters.name }}</span>
      <svg-icon icon-class="s-operation" class="exit" />
      <svg-icon icon-class="exit" class="exit" @click="logout()" />
    </div>

    <div v-if="!collapse" class="version">{{ title }}</div>
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
  },
  methods: {
    async logout() {
      this.$confirm('<strong>我们会很想你的哦！</strong>', '真的要退出吗？😜', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '是的，我要走了',
        cancelButtonText: '继续留下'
      }).then(async() => {
        this.$message({
          type: 'success',
          center: true,
          message: '唉，真是伤心呢...再见啦！😢'
        })
        await this.$store.dispatch('user/logout')
        this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      }).catch(() => {
        this.$message({
          type: 'success',
          center: true,
          message: '太好了！我们就知道你舍不得走！😊'
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>

.avatar-wrapper {
  margin: 0 10px 0 10px;
  padding: 2px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px solid #c0ccda;
  border-radius: 38px;
}
.sidebar-avatar {
  text-align: center;
}
.exit {
  cursor:pointer;
  font-size: 18px;
}

.name {
  font-size: 14px;
  font-weight: 600;
  padding: 15px 10px;
}

.version {
  font-size: 12px;
  padding: 10px 10px;
  color: #999;
  text-align: center;
  margin: 0;
}
</style>
