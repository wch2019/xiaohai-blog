<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="用户名" prop="nickName">
      <el-input v-model="user.username" maxlength="30" />
    </el-form-item>
    <el-form-item label="用户昵称" prop="nickName">
      <el-input v-model="user.nickName" maxlength="30" />
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="user.gender">
        <el-radio
          v-for="dict in $store.getters.dict.sys_user_sex"
          :key="dict.dictValue"
          size="mini"
          border
          :label="dict.dictValue"
          :value="dict.dictValue"
        >
          {{ dict.dictLabel }}
        </el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="手机" prop="mobile">
      <el-input v-model="user.mobile" maxlength="11" />
    </el-form-item>
    <el-form-item label="QQ">
      <el-input v-model="user.qqNumber" />
    </el-form-item>
    <el-form-item label="微信">
      <el-input v-model="user.weChat" />
    </el-form-item>
    <el-form-item label="自我介绍">
      <el-input v-model="user.summary" type="textarea" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUser } from '@/api/system/user'

export default {
  props: {
    user: {}
  },
  data() {
    return {
      // 表单校验
      rules: {
        nickName: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        mobile: [
          { message: '手机号码不能为空', trigger: 'blur' },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          updateUser(this.user).then(response => {
            if (this.user.nickName) {
              this.$store.commit('user/SET_NAME', this.user.nickName)
            }
            this.$message.success(response.msg)
          })
        }
      })
    }
  }
}
</script>
