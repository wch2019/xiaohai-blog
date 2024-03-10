<template>
  <div>

    <el-popover
      placement="bottom"
      title="消息"
      width="300"
      trigger="hover"
    >
      <div style="min-height: 200px">
        <el-alert
          v-for="(alert, index) in displayedAlerts"
          :key="index"
          class="message"
          :title="alert.title"
          :description="alert.description"
          type="success"
          @close="close"
        />
      </div>
      <div style="text-align: right; margin: 0">
        <router-link to="/message/index">
          <el-button size="small" type="text">查看全部</el-button>
        </router-link>
      </div>
      <el-badge v-if="displayedAlerts.length!==0" slot="reference" :value="12" class="item el-icon-bell" />
      <el-badge v-else slot="reference" class="item el-icon-bell" />
    </el-popover>
  </div>
</template>

<script>
export default {
  name: 'Website',
  data() {
    return {
      alerts: [
        { description: '文字说明1', title: '1' },
        { description: '文字说明2', title: '1' },
        { description: '文字说明3', title: '1' },
        { description: '文字说明4', title: '1' },
        { description: '文字说明5', title: '1' },
        { description: '文字说明6', title: '1' },
        { description: '文字说明7', title: '1' }
      ]
    }
  },
  computed: {
    displayedAlerts() {
      // 获取前 5 条警报
      return this.alerts.slice(0, 5)
    }
  },
  methods: {
    close() {
      this.$message.success('已读')
    }
  }
}
</script>

<style scoped>
.message {
  margin: 4px 0 4px 0;
}
</style>
