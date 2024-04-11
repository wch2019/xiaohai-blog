<template>
  <div class="login-container" :style="{backgroundImage:'url('+imgSrc+')'}">
    <div class="loginPart">
      <el-form
        ref="loginForm"
        :model="registerForm"
        :rules="registerRules"
        auto-complete="on"
        label-position="left"
      >
        <h2>DotCode's Blog</h2>
        <el-steps :active="active" finish-status="success" process-status="wait" style="margin-bottom:30px;">
          <el-step title="步骤 1" />
          <el-step title="步骤 2" />
          <el-step title="步骤 3" />
        </el-steps>

        <el-form-item prop="username" class="inputNew">
          <el-input
            ref="username"
            v-model="registerForm.username"
            placeholder="请输入用户名"
            name="username"
            type="text"
            tabindex="1"
            auto-complete="on"
          >
            <template #prefix>
              <svg-icon icon-class="user" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="email" class="inputNew">
          <el-input
            ref="email"
            v-model="registerForm.email"
            placeholder="请输入邮箱"
            name="email"
            type="text"
            tabindex="2"
            auto-complete="on"
          >
            <template #prefix>
              <svg-icon icon-class="email" />
            </template>
            <el-link v-if="captchaEnabled" slot="suffix" :underline="false" type="warning" @click="getCode">发送验证码</el-link>
            <span v-else slot="suffix">{{ count }}s后重新获取</span>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="inputNew">
          <el-input
            ref="password"
            v-model="registerForm.password"
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

        <el-button
          :loading="loading"
          type="primary"
          style="width:100%;margin-bottom:30px;"

          @click="next"
        >
          <span v-if="!loading">下一步</span>
          <span v-else>注 册 中...</span>
        </el-button>

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
  </div>
</template>

<script>
import { sendEmailCode, register } from '@/api/login'
import { Message } from 'element-ui'
export default {
  name: 'Register',
  data() {
    return {
      active: 1,
      count: '',
      timer: null,
      captchaEnabled: true,
      imgSrc: require('@/assets/login/3.jpg'),
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        code: '',
        email: ''
      },
      registerRules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入您的账号' },
          { min: 2, max: 20, message: '用户账号长度必须介于 2 和 20 之间', trigger: 'blur' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入您的密码' },
          { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' }
        ]
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getImg()
  },
  methods: {
    // 背景随机
    getImg() {
      const num = Math.floor(Math.random() * 5 + 1)
      this.imgSrc = require('@/assets/login/' + num + '.jpg')
    },
    next() {
      if (this.active++ > 2) this.active = 1
    },
    handleRegister() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          register(this.registerForm).then(res => {
            Message({ message: res.msg, type: 'success', duration: 5 * 1000 })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
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
