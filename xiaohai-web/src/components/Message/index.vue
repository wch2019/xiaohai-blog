<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix">
        <span>消息中心</span>
      </div>
      <el-tabs v-model="activeName" tab-position="left" :stretch="true" style="height: calc(100vh - 190px);">
        <el-tab-pane v-for="item in $store.getters.dict.sys_notification_type" :key="item.name" :label="item.dictLabel" :name="item.dictValue">
          <div v-for="like in alertsLike" v-show="activeName==='1'" :key="like.id">
            <el-badge v-if="like.isRead===0" is-dot class="item" style="position: absolute;right: 0;" />
            <div class="content">
              <div style="display: flex;justify-content: space-between;">
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
                <el-button v-if="like.isRead===0" type="text" class="know-button" @click="handleKnowClick(like)">我知道了</el-button>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>

import { listNotifications, updateNotifications } from '@/api/note/notifications'

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
      listNotifications(this.params).then(response => {
        this.alertsLike = response.data.records
        this.alertsCommon = response.data.records
        this.alertsSystem = response.data.records
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
      like.isRead = 1
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
    },
    handleKnowClick(like) {
      this.read(like.id)
      like.isRead = 1
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
}
}

  .content {
  margin: 1px 0;
}

  .content :hover {
  background-color: #F5F6F9;
  border-radius: 4px; /* 添加圆角 */
}

  .know-button {
    margin-right: 10px;
    display: none; /* 默认隐藏按钮 */
  }

  .content:hover .know-button {
    display: inline-block; /* 鼠标悬停时显示按钮 */
  }

</style>
