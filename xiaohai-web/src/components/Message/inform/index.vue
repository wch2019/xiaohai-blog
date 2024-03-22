<template>
  <div>
    <el-popover
      placement="bottom-end"
      width="400"
      trigger="click"
      @show="getUnreadList"
    >
      <div style="min-height: 200px">
        <el-tabs v-model="activeName" :stretch="true" @tab-click="handleClick">
          <el-tab-pane v-for="item in $store.getters.dict.sys_notification_type" :key="item.name" :label="item.dictLabel" :name="item.dictValue" />
          <div v-if="activeName==='1'">
            <div v-for="like in alertsLike" :key="like.id" class="content">
              <div class="content-flex">
                <el-image :src="header(like.likeDto?like.likeDto.avatar:'')" />
                <div class="name-header">
                  <div class="name">{{ like.likeDto?like.likeDto.nickName:'' }}</div>
                  <div class="subhead">
                    <span>赞了你的文章</span>
                    <span>{{ like.createdTime }}</span>
                  </div>
                  <el-tooltip :content="like.title" placement="bottom">
                    <el-link class="ellipsis-link" :underline="false" @click="onClick(like)">
                      《{{ like.title }}》
                    </el-link>
                  </el-tooltip>
                </div>
              </div>
            </div>
          </div>
          <div v-if="activeName==='2'">
            <div v-for="common in alertsCommon" :key="common.id">
              <div v-if="common.commentDto" class="content">
                <div style="display: flex;justify-content: space-between;">
                  <div class="content-flex">
                    <div style="width: 35px">
                      <el-image :src="header(common.commentDto.avatar)" />
                    </div>
                    <div class="name-header">
                      <div class="name">{{ common.commentDto.username }}</div>
                      <div class="subhead">
                        <span v-if="common.commentDto.replyUsername">@ {{ common.commentDto.replyUsername }}</span>
                        <span v-if="common.articleId">
                          评论文章
                          <el-tooltip :content="common.title" placement="bottom">
                            <el-link class="ellipsis-link" :underline="false" @click="onClick(common)">
                              《{{ common.title }}》
                            </el-link>
                          </el-tooltip>
                        </span>
                        <span v-else>
                          留言
                        </span>
                        <span>{{ common.commentDto.createdTime }}</span>
                      </div>
                      <div style="font-size: 14px;">
                        {{ common.commentDto.content }}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-tabs>

        <!--        </div>-->
        <!--        <div v-for="system in alertsSystem" v-show="activeName==='3'" :key="system.id" class="content">-->
        <!--          <div class="content-flex">-->
        <!--            <el-image :src="header(alert.likeDto.avatar)" />-->
        <!--            <div class="name-header">-->
        <!--              <div class="name">{{ alert.likeDto.nickName }}</div>-->
        <!--              <div class="subhead">-->
        <!--                <span>赞了你的文章</span>-->
        <!--                <span>{{ alert.createdTime }}</span>-->
        <!--              </div>-->
        <!--              <el-tooltip :content="alert.title" placement="bottom">-->
        <!--                <el-link class="ellipsis-link" :underline="false" @click="onClick(alert.articleId)">-->
        <!--                  《{{ alert.title }}》-->
        <!--                </el-link>-->
        <!--              </el-tooltip>-->
        <!--            </div>-->
        <!--          </div>-->

        <!--        </div>-->
      </div>
      <div style="text-align: right; margin: 0">
        <router-link to="/message/index">
          <el-button size="small" type="text">查看全部</el-button>
        </router-link>
      </div>
      <el-badge v-if="count!=0" slot="reference" :value="count" class="item el-icon-bell" />
      <el-badge v-else slot="reference" class="item el-icon-bell" />

    </el-popover>
  </div>
</template>

<script>
import { EventSourcePolyfill } from 'event-source-polyfill'
import { getToken } from '@/utils/auth'
import { getUnread, updateNotifications } from '@/api/note/notifications'

export default {
  name: 'Website',
  data() {
    return {
      url: process.env.VUE_APP_BLOG_WEB_API,
      count: 0,
      alertsLike: [],
      alertsCommon: [],
      alertsSystem: [],
      activeName: '1',
      params: {
        type: '1'
      }
    }
  },
  computed: {
    // displayedAlerts() {
    // 获取前 5 条警报
    // return this.alerts.slice(0, 5)
    // }
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
      this.params.type = this.activeName
      getUnread(this.params).then(response => {
        if (this.activeName === '1') {
          this.alertsLike = response.data
        }
        if (this.activeName === '2') {
          this.alertsCommon = response.data
        }
        if (this.activeName === '3') {
          this.alertsSystem = response.data
        }
      })
    },
    /**
     *已读
     */
    read(ids) {
      updateNotifications(ids).then(response => {
        console.log(response, 'response')
      })
    },
    onClick(like) {
      this.read(like.id)
      this.alertsLike = this.alertsLike.filter(item => item.id !== like.id)
      window.open(this.url + '/article/' + like.articleId)
    },
    handleClick(tab, event) {
      this.getUnreadList()
    },
    /**
     * 头像
     */
    header(avatar) {
      return process.env.VUE_APP_BASE_API_FILE + avatar
    }
  }
}
</script>

<style scoped>

.content-flex {
  display: flex;
  padding: 10px;

  img {
    width: 35px;
    height: 35px;
    border-radius: 4px;
  }

  .name {
    font-weight: 800;
  }

  .subhead {
    font-size: 12px;
    color: #ccc;
  }

  .name-header {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 10px;
  }

  .ellipsis-link {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: inline-block;
    max-width: 300px; /* 可根据需要调整最大宽度 */
  }
}

.content {
  margin: 1px 0;
}

.content :hover {
  background-color: #F5F6F9;
  border-radius: 4px; /* 添加圆角 */
}
</style>
