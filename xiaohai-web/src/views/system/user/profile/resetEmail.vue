<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="新邮箱" prop="newEmail">
      <el-input v-model="user.newEmail" placeholder="请输入新邮箱">
        <el-link v-if="captchaEnabled" :underline="false" slot="suffix" type="primary" @click="getCode">发送验证码</el-link>
        <span v-else slot="suffix">{{ count }}s后重新获取</span>
      </el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="code">
      <el-input v-model="user.code" placeholder="请输入邮箱验证码"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateEmail } from '@/api/system/user'
import { sendEmailCode } from '@/api/login'
import { Message } from 'element-ui'

export default {
  data() {
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
      count: '',
      timer: null,
      captchaEnabled: true,
      user: {
        newEmail: '',
        code: ''
      },
      // 表单校验
      rules: {
        newEmail: [{ required: true, validator: validateEmail, trigger: 'blur' }],
        code: [{ required: true, message: '邮箱验证码不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          updateEmail(this.user).then(
            response => {
              this.$message.success(response.msg)
            }
          )
        }
      })
    },
    // 获取邮箱验证码
    getCode() {
      this.$refs.form.validateField('newEmail', (val) => {
        if (!val) {
          const data = {
            email: this.user.newEmail
          }
          sendEmailCode(data).then(res => {
            console.log(res)
            Message({ message: res.msg, type: 'success', duration: 5 * 1000 })
          })
          const TIME_COUNT = 60
          if (!this.timer) {
            this.count = TIME_COUNT
            this.captchaEnabled = false
            this.timer = setInterval(() => {
              if (this.count > 0 && this.count <= TIME_COUNT) {
                this.count--
              } else {
                this.captchaEnabled = true
                clearInterval(this.timer)
                this.timer = null
              }
            }, 1000)
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
