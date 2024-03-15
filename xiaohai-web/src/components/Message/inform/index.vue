<template>
  <div>
    <el-popover
      placement="bottom"
      width="300"
      trigger="click"
      @show="getUnreadList"
    >
      <!--      <div style="min-height: 200px">-->
      <!--        <el-alert-->
      <!--          v-for="(alert, index) in displayedAlerts"-->
      <!--          :key="index"-->
      <!--          class="message"-->
      <!--          :title="type(alert.type)"-->
      <!--          close-text="知道了"-->
      <!--          type="success"-->
      <!--          @close="close"-->
      <!--        >-->
      <!--          <template slot="title">-->
      <!--            <div>-->
      <!--              {{ alert }}-->
      <!--              <el-link v-if="alert.type===1" :underline="false" @click="onClick(alert.articleId)">{{ alert.title }}</el-link>-->
      <!--            </div>-->

      <!--          </template>-->
      <!--        </el-alert>-->

      <!--      </div>-->
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane v-for="item in activeList" :label="item.label" :name="item.value"></el-tab-pane>
      </el-tabs>
      <div v-for="item in alerts" style="margin-bottom: 10px">
        <div class="content-flex">
          <img src="../../../assets/login/1.jpg">
          <div class="name-header">
            <div class="name">名字</div>
            <div class="subhead">
              <span>赞了你的文章</span>
              <span>2024-1-3</span>
            </div>
          </div>
        </div>
        <div class="content">
          文章文章文章文章文章文章文章文章文章文章文章文章文章文章文章文章文章文章
        </div>
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
      alerts: [],
      activeName: 'first',
      activeList: [
        { label: '点赞', value: 'first' },
        { label: '评论', value: 'second' },
        { label: '私信', value: 'third' }
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
        this.alerts = response.data
        console.log(this.alerts,'this.alerts')
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
    },
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }
}
</script>

<style scoped>
.message {
  margin: 4px 0 4px 0;
}
.content-flex{
  display: flex;
  img{
    width: 35px;
    height: 35px;
    border-radius: 50%;
  }
  .name{
    font-weight: 800;
  }
  .subhead{
    font-size: 12px;
  }
  .name-header{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 10px;
  }
}
.content{
  margin-left: 45px;
  background-color: #F5F6F9;
  font-size: 12px;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}
</style>
