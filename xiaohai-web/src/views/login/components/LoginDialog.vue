<template>
  <el-dialog title="重新登录" :visible.sync="visible" width="20%" @close="handleClose">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules">
      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密 码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        >
          <template #suffix>
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" @click="showPwd" />
          </template>
        </el-input>
      </el-form-item>
      <div style="margin-bottom: 10px;margin-left: 2px">
        <el-tooltip class="item" effect="dark" content="7天有效" placement="right">
          <el-checkbox v-model="loginForm.rememberMe">
            <span>记住我</span>
          </el-checkbox>
        </el-tooltip>
      </div>
      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-bottom:30px;"
        @click.native.prevent="handleLogin"
      >
        <span v-if="!loading">登 录</span>
        <span v-else>登 录 中...</span>
      </el-button>
    </el-form>
  </el-dialog>
</template>

<script>
import store from '@/store'
export default {
  data() {
    return {
      visible: false,
      loginForm: {
        username: '',
        password: '',
        rememberMe: false
      },
      passwordType: 'password',
      loginRules: {
        username: [{ required: true, trigger: 'blur', message: '请输入您的用户名' }],
        password: [{ required: true, trigger: 'blur', message: '请输入您的密码' }]
      },
      loading: false
    }
  },
  methods: {
    open() {
      this.visible = true
    },
    handleClose() {
      this.visible = false
    },
    // 是否显示输入内容
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
    // 登录
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          store.dispatch('user/login', this.loginForm).then(res => {
            this.$message.success('登录成功')
            this.visible = false
            this.loading = false
          }).catch(() => {
            this.$message.error('登录失败')
            this.loading = false
            this.visible = false
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
