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
          :title="type(alert.type)"
          close-text="知道了"
          type="success"
          @close="close"
        >
          <template slot="title">
            <div>
              {{ alert }}
              <el-link v-if="alert.type===1" :underline="false" @click="onClick(alert.articleId)">{{ alert.title }}</el-link>
            </div>

          </template>
        </el-alert>

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
      url: process.env.VUE_APP_BLOG_WEB_API,
      count: 0,
      alerts: []
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
        this.alerts = response.data
        // for (const result of data) {
        //   const alert = { description: '', title: '' }
        //   alert.description = result.title
        //
        //   for (const dict of this.$store.getters.dict.sys_notification_type) {
        //     if (result.type === dict.dictValue) {
        //       alert.title = dict.dictLabel
        //     }
        //   }
        //   this.alerts.push(alert)
        // }
      })
    },
    type(type) {
      for (const dict of this.$store.getters.dict.sys_notification_type) {
        if (type === dict.dictValue) {
          console.log(dict.dictLabel)
          return dict.dictLabel
        }
      }
    },
    close() {
      this.$message.success('已读')
    },
    onClick(row) {
      window.open(this.url + '/article/' + row.id)
    }
  }
}
</script>

<style scoped>
.message {
  margin: 4px 0 4px 0;
}
</style>
