<template>
  <div class="login-container" :style="{backgroundImage:'url('+imgSrc+')'}">
    <div class="loginPart">
      <el-form
        ref="loginForm"
        :model="initialForm"
        :rules="registerRules"
        auto-complete="on"
        label-position="left"
      >
        <h2>DotCode's Blog</h2>
        <el-form-item prop="siteName" class="inputNew">
          <el-input
            ref="email"
            v-model="initialForm.siteName"
            placeholder="请输入站点名称"
            name="email"
            type="text"
            tabindex="1"
            auto-complete="on"
          >
            <template #prefix>
              <svg-icon icon-class="website" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email" class="inputNew">
          <el-input
            ref="email"
            v-model="initialForm.email"
            placeholder="请输入邮箱"
            name="email"
            type="text"
            tabindex="2"
            auto-complete="on"
          >
            <template #prefix>
              <svg-icon icon-class="email" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="username" class="inputNew">
          <el-input
            ref="username"
            v-model="initialForm.username"
            placeholder="请输入用户名"
            name="username"
            type="text"
            tabindex="3"
            auto-complete="on"
          >
            <template #prefix>
              <svg-icon icon-class="user" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="inputNew">
          <el-input
            ref="password"
            v-model="initialForm.password"
            name="password"
            type="password"
            placeholder="密码"
            auto-complete="on"
            tabindex="4"
          >
            <template #prefix>
              <svg-icon icon-class="password" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword" class="inputNew">
          <el-input
            ref="confirmPassword"
            v-model="initialForm.confirmPassword"
            type="password"
            placeholder="确认密码"
            auto-complete="on"
            tabindex="5"
            @keyup.enter.native="handleInitial"
          >
            <template #prefix>
              <svg-icon icon-class="password" />
            </template>
          </el-input>
        </el-form-item>

        <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;"
          @click.native.prevent="handleInitial"
        >
          <span v-if="!loading">初始化</span>
          <span v-else>初 始 中...</span>
        </el-button>
        <div style="text-align: right;color: white;">
          <el-link type="warning" @click="dialogVisible = true">备份还原</el-link>
        </div>
      </el-form>
    </div>
    <!--引入粒子特效-->
    <vue-particles
      color="#fff"
      :particle-opacity="0.7"
      :particles-number="60"
      shape-type="circle"
      :particle-size="4"
      lines-color="#fff"
      :lines-width="1"
      :line-linked="true"
      :line-opacity="0.4"
      :lines-distance="150"
      :move-speed="2"
      :hover-effect="true"
      hover-mode="grab"
      :click-effect="true"
      click-mode="push"
    />
    <el-dialog
      title="备份还原"
      :visible.sync="dialogVisible"
      width="25%"
      :close-on-click-modal="false"
      center
    >
      <el-upload
        class="upload-demo"
        drag
        accept=".zip"
        :headers="{
          'authorization':getToken()
        }"
        :action="fileAction"
        :on-success="handleAvatarSuccess"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import { initial } from '@/api/login'
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth'

export default {
  name: 'Initial',
  data() {
    // 验证是否相同
    const equalToPassword = (rule, value, callback) => {
      if (this.initialForm.password !== value) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    // 邮箱验证
    const validateEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请正确填写邮箱'))
      } else {
        if (value !== '') {
          var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
          if (!reg.test(value)) {
            callback(new Error('请输入有效的邮箱'))
          }
        }
        callback()
      }
    }
    return {
      active: 1,
      imgSrc: require('@/assets/login/3.jpg'),
      initialForm: {
        siteName: '',
        username: '',
        password: '',
        confirmPassword: '',
        email: ''
      },
      registerRules: {
        siteName: [
          { required: true, trigger: 'blur', message: '请输入站点名称' },
          { min: 2, max: 8, message: '站点名称长度必须介于 2 和 8 之间', trigger: 'blur' }
        ],
        username: [
          { required: true, trigger: 'blur', message: '请输入您的账号' },
          { min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入您的密码' },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, trigger: 'blur', message: '请再次输入您的密码' },
          { required: true, validator: equalToPassword, trigger: 'blur' }
        ],
        email: [{ validator: validateEmail, trigger: 'blur' }]
      },
      loading: false,
      dialogVisible: false,
      fileAction: process.env.VUE_APP_BASE_API + '/system/backup/restore/upload'
    }
  },
  created() {
    this.getImg()
  },
  methods: {
    getToken,
    // 背景随机
    getImg() {
      const num = Math.floor(Math.random() * 5 + 1)
      this.imgSrc = require('@/assets/login/' + num + '.jpg')
    },
    handleInitial() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          console.log(this.initialForm)
          initial(this.initialForm).then(res => {
            Message({ message: res.msg, type: 'success', duration: 5 * 1000 })
            this.loading = false
            this.$router.push('/login')
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleAvatarSuccess(res, file) {
      if (res.code === 200) {
        this.$message.success(res.msg)
        this.$router.push('/login')
      } else {
        this.$message.error(res.msg)
      }
    }
  }
}
</script>

<style lang="scss" scoped>

.login-container {
  background-size: cover;
  min-height: 100%;
  max-height: 100%;
  width: 100%;
  overflow: hidden;
}

.loginPart {
  position: absolute;
  /*定位方式绝对定位absolute*/
  top: 50%;
  left: 50%;
  /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
  transform: translate(-50%, -50%);
  /*实现块元素百分比下居中*/
  width: 450px;
  padding: 50px;
  background: rgba(16, 15, 15, 0.5);
  /*背景颜色为黑色，透明度为0.8*/
  box-sizing: border-box;
  /*box-sizing设置盒子模型的解析模式为怪异盒模型，
  将border和padding划归到width范围内*/
  box-shadow: 0px 15px 25px rgba(0, 0, 0, .5);
  /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
  border-radius: 15px;
  /*边框圆角，四个角均为15px*/
}

.loginPart h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
  /*文字居中*/
}

::v-deep .inputNew .el-input__inner {
  width: 100%;
  //padding:10px 0;
  font-size: 16px;
  color: #fff !important;
  letter-spacing: 1px;
  /*字符间的间距1px*/
  margin-bottom: 10px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  /*outline用于绘制元素周围的线
  outline：none在这里用途是将输入框的边框的线条使其消失*/
  background: transparent !important;
  /*背景颜色为透明*/
}
</style>
