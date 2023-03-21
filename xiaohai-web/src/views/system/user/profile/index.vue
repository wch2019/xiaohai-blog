<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div class="text-center">
              <userAvatar :user="user"/>
            </div>
            <ul class="list-group list-group-striped">
              <li class="list-group-item">
                <svg-icon icon-class="user"/>
                用户名称
                <div class="pull-right">{{ user.nickName }}</div>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="gender-0" />
                性别
                <template v-for="(item) in $store.getters.dict.sys_user_sex">
                  <div v-if="user.gender==item.dictValue" :key="item.id" class="pull-right">{{ item.dictLabel }}</div>
                </template>
              </li>
              <li class="list-group-item">
                <svg-icon icon-class="email"/>
                用户邮箱
                <div class="pull-right">{{ user.email }}</div>
              </li>
              <li class="list-group-item" >
                <svg-icon icon-class="wechat" />
                微信
                <div class="pull-right">{{ user.weChat }}</div>
              </li>
              <li class="list-group-item" >
                <svg-icon icon-class="qq"/>
                QQ
                <div class="pull-right">{{ user.qqNumber }}</div>
              </li>
              <li class="list-group-item" v-if="user.summary">
                {{ user.summary }}
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span>基本资料</span>
          </div>
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本资料" name="userinfo">
              <userInfo :user="user"/>
            </el-tab-pane>
            <el-tab-pane label="修改密码" name="resetPwd">
              <resetPwd :user="user"/>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from './userAvatar'
import userInfo from './userInfo'
import resetPwd from './resetPwd'
import { getUser } from '@/api/system/user'
import { optionSelect } from '@/api/system/role'

export default {
  name: 'Profile',
  components: { userAvatar, userInfo, resetPwd },
  data() {
    return {
      // 角色选择框列表
      roleOptions: [],
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: 'userinfo'
    }
  },
  created() {
    this.getUser()
    this.getRoleList()
  },
  methods: {
    getUser() {
      getUser(this.$store.getters.userId).then(response => {
        this.user = response.data
      })
    },
    /** 获取角色选择框列表 */
    getRoleList() {
      optionSelect().then(response => {
        this.roleOptions = response.data
      })
    }
  }
}
</script>

<style scoped>

::v-deep .list-group {
  padding-left: 0px;
  list-style: none;
}

::v-deep .list-group-item {
  border-bottom: 1px solid #e7eaec;
  border-top: 1px solid #e7eaec;
  margin-bottom: -1px;
  padding: 11px 0px;
  font-size: 13px;
}

::v-deep .pull-right {
  float: right !important;
}

</style>
