<template>
  <div>

    <el-popover
      placement="bottom"
      title="消息"
      width="300"
      trigger="hover"
      @show="getUnreadList"
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
      <el-badge v-if="count!==0" slot="reference" :value="count" class="item el-icon-bell" />
      <el-badge v-else slot="reference" class="item el-icon-bell" />
    </el-popover>
  </div>
</template>

<script>
import { EventSourcePolyfill } from 'event-source-polyfill'
import { getToken } from '@/utils/auth'
import { getUnread } from '@/api/note/notifications'

export default {
  name: 'Website',
  data() {
    return {
      count: 0,
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
  mounted() {
    this.createSSE()
  },
  methods: {
    createSSE() {
      if (window.EventSource) {
        // 根据环境的不同，变更url
        const url = process.env.VUE_APP_BASE_API
        console.log(url)
        this.eventSource = new EventSourcePolyfill(
          `${url}/notifications/news`, {
            // 设置重连时间
            heartbeatTimeout: 60 * 60 * 1000,
            // 添加token
            headers: {
              'Authorization': `${getToken()}`
            }
          })
        this.eventSource.onopen = (e) => {
          console.log('已建立SSE连接~')
        }
        this.eventSource.onmessage = (e) => {
          if (e.data !== 'heartbeat') {
            this.count = e.data
          }
        }
        this.eventSource.onerror = (e) => {
          if (e.readyState === EventSource.CLOSED) {
            console.log('SSE连接关闭')
          } else if (this.eventSource.readyState === EventSource.CONNECTING) {
            console.log('SSE正在重连')
            // 重新设置token
            this.eventSource.headers = {
              'Authorization': `${getToken()}`
            }
          } else {
            console.log('error', e)
          }
        }
      } else {
        console.log('你的浏览器不支持SSE~')
      }
    },
    getUnreadList() {
      getUnread().then(response => {
        console.log(response.data)
      })
    },
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
