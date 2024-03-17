<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>消息中心</span>
      </div>
      <el-tabs v-model="activeName" tab-position="left" :stretch="true" style="height: 200px;">
        <el-tab-pane v-for="item in $store.getters.dict.sys_notification_type" :key="item.name" :label="item.dictLabel" :name="item.dictValue">
          <div v-for="like in alertsLike" v-show="activeName==='1'" :key="like.id" class="content">
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
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>

import { getUnread, updateNotifications } from '@/api/note/notifications'

export default {
  name: 'Index',
  data() {
    return {
      url: process.env.VUE_APP_BLOG_WEB_API,
      alertsLike: [],
      alertsCommon: [],
      alertsSystem: [],
      activeName: '1',
      params: {
        type: '1'
      }
    }
  },
  created() {
    this.getUnreadList()
  },
  methods: {
    getUnreadList() {
      this.params.type = this.activeName
      getUnread(this.params).then(response => {
        this.alertsLike = response.data
        this.alertsCommon = response.data
        this.alertsSystem = response.data
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
      console.log(tab, event)
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
