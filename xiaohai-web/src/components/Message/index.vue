<template>
  <div class="app-container">
    <!--    <el-card>-->
    <!--      <div slot="header" class="clearfix">-->
    <!--        <span>消息中心</span>-->
    <!--      </div>-->
    <el-tabs v-model="activeName" tab-position="left" :stretch="true" class="tabs" @tab-click="handleClick">
      <el-tab-pane v-for="item in $store.getters.dict.sys_notification_type" :key="item.name" :label="item.dictLabel" :name="item.dictValue">
        <div v-if="activeName==='1'">
          <div v-for="like in alertsLike" :key="like.id">
            <el-badge v-if="like.isRead===0" is-dot class="item" style="position: absolute;right: 0;" />
            <div class="content">
              <div style="display: flex;justify-content: space-between;">
                <div class="content-flex">
                  <el-image :src="header(like.likeDto?like.likeDto.avatar:'')" />
                  <div class="name-header">
                    <div class="name">{{ like.likeDto ? like.likeDto.nickName : '' }}</div>
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
        </div>
        <div v-if="activeName==='2'">
          <div v-for="common in alertsCommon" :key="common.id">
            <el-badge v-if="common.isRead===0" is-dot class="item" style="position: absolute;right: 0;" />
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
                    <div style="font-size: 14px; width: 80%">
                      {{ common.commentDto.content }}
                    </div>
                  </div>
                </div>
                <el-button v-if="common.isRead===0" type="text" class="know-button" @click="handleKnowClick(common)">我知道了</el-button>
              </div>
            </div>
          </div>
        </div>
        <div v-if="activeName==='3'">
          <div v-for="system in alertsSystem" :key="system.id">
            <el-badge v-if="system.isRead===0" is-dot class="item" style="position: absolute;right: 0;" />
            <div class="content">
              <div style="display: flex;justify-content: space-between;">
                <div v-if="system.linkDto" class="content-flex">
                  <el-image :src="logo" />
                  <div class="name-header">
                    <div class="name">{{ system.remark }}</div>
                    <div class="subhead">
                      <span>{{ system.createdTime }}</span>
                    </div>
                    <div style="font-size: 14px;">
                      <el-link class="ellipsis-link" :underline="false" :href="system.linkDto.url" target="_blank">
                        {{ system.linkDto.name }} :  {{ system.linkDto.url }} : {{ system.linkDto.info }}
                      </el-link>
                    </div>
                  </div>
                </div>
                <el-button v-if="system.isRead===0" type="text" class="know-button" @click="handleKnowClick(system)">我知道了</el-button>
              </div>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <pagination
        v-show="total>0"
        :total="total"
        style=" bottom: 20px; right: 20px;  position: fixed;"
        :page.sync="params.pageNum"
        :limit.sync="params.pageSize"
        @pagination="getUnreadList"
      />
    </el-tabs>
    <!--    </el-card>-->
  </div>
</template>

<script>

import { listNotifications, updateNotifications } from '@/api/note/notifications'

export default {
  name: 'Index',
  data() {
    return {
      logo: process.env.VUE_APP_BASE_API_FILE + '/system/favicon.ico',
      url: process.env.VUE_APP_BLOG_WEB_API,
      // 总条数
      total: 0,
      alertsLike: [],
      alertsCommon: [],
      alertsSystem: [],
      activeName: '1',
      params: {
        pageNum: 1,
        pageSize: 10,
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
        this.total = response.data.total
        if (this.activeName === '1') {
          this.alertsLike = response.data.records
        }
        if (this.activeName === '2') {
          this.alertsCommon = response.data.records
        }
        if (this.activeName === '3') {
          this.alertsSystem = response.data.records
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
      like.isRead = 1
      window.open(this.url + '/article/' + like.articleId)
    },
    /**
     * 点击切换
     * @param tab
     * @param event
     */
    handleClick(tab, event) {
      this.getUnreadList()
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
    font-size: 14px;
    font-weight: 800;
    margin-bottom: 0.2rem;
  }

  .subhead {
    font-size: 14px;
    color: #9f9f9f;
    margin-bottom: 0.2rem;
  }

  .name-header {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin-left: 10px;
  }

  .ellipsis-link {
    font-size: 14px;
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

.tabs {
  height: calc(100vh - 190px);
}

.tabs:hover {
  overflow-y: auto;
}

</style>
