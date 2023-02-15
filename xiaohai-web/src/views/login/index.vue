<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">点码后台管理系统</h3>
      </div>

      <el-form-item prop="username">

        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        >
          <template #prefix>
          <svg-icon icon-class="user"/>
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        >
          <template #prefix>
            <svg-icon icon-class="password" />
          </template>
          <template #suffix>
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" @click="showPwd"/>
          </template>
        </el-input>

      </el-form-item>
      <div style="margin-bottom: 10px;margin-left: 2px">
        <el-checkbox v-model="loginForm.rememberMe">
          <span style="color: white">记住我</span>
        </el-checkbox>
      </div>
      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin">
        <span v-if="!loading">登 录</span>
        <span v-else>登 录 中...</span>
      </el-button>

    </el-form>
  </div>
</template>

<script>
import {validUsername} from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码不能少于6位数字'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },
      loginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({path: this.redirect || '/'})
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

<!--<style lang="scss">-->
<!--/* 修复input 背景不协调 和光标变色 */-->
<!--/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */-->

<!--$bg: #414e5d;-->
<!--$light_gray:#fff;-->
<!--$cursor: #fff;-->

<!--@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {-->
<!--  .login-container .el-input input {-->
<!--    color: $cursor;-->
<!--  }-->
<!--}-->

<!--/* reset element-ui css */-->
<!--.login-container {-->
<!--  .el-input {-->
<!--    display: inline-block;-->
<!--    height: 47px;-->
<!--    width: 85%;-->

<!--    input {-->
<!--      background: transparent;-->
<!--      border: 0px;-->
<!--      -webkit-appearance: none;-->
<!--      border-radius: 0px;-->
<!--      padding: 12px 5px 12px 15px;-->
<!--      color: $light_gray;-->
<!--      height: 47px;-->
<!--      caret-color: $cursor;-->

<!--      &:-webkit-autofill {-->
<!--        box-shadow: 0 0 0px 1000px $bg inset !important;-->
<!--        -webkit-text-fill-color: $cursor !important;-->
<!--      }-->
<!--    }-->
<!--  }-->

<!--  .el-form-item {-->
<!--    border: 1px solid rgba(255, 255, 255, 0.1);-->
<!--    background: rgba(0, 0, 0, 0.1);-->
<!--    border-radius: 5px;-->
<!--    color: #454545;-->
<!--  }-->
<!--}-->
<!--</style>-->

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  //display: flex;
  //justify-content: center;
  //align-items: center;
  //height: 100%;
  //background-image: url("@/assets/bg.jpg");
  background-size: cover;
  min-height: 100%;
  background-color: $bg;
  width: 100%;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    //margin: 6px 5px 6px 1px;
    text-align: center;
    height: 100%;
    //color: $dark_gray;
    //vertical-align: middle;
    //width: 30px;
    //display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
