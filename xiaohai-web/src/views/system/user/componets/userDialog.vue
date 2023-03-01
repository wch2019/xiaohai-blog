<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" placeholder="请输入用户名" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="角色" prop="roleIds">
            <el-select v-model="form.roleIds" multiple >
              <el-option
                v-for="item in roleOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in $store.getters.dict.sys_normal_disable"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{ dict.dictLabel }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="form.nickName" placeholder="请输入用户昵称" maxlength="30" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户性别">
            <el-select v-model="form.gender" placeholder="请选择">
              <el-option
                v-for="dict in $store.getters.dict.sys_user_sex"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addUser, updateUser } from '@/api/system/user'

export default {
  name: 'RoleDialog',
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
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {
        id: '',
        username: '',
        email: '',
        roleIds: [],
        status: '0',
        nickName: '',
        gender: ''
      },
      // 角色选择框列表
      roleOptions: [],
      // 表单校验
      rules: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
        email: [{ required: true, validator: validateEmail, trigger: 'blur' }],
        roleIds: [{ required: true, message: '角色不能为空', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        id: '',
        username: '',
        email: '',
        roleIds: [],
        status: '0',
        nickName: '',
        gender: ''
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      console.log(this.form)
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== '') {
            updateUser(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addUser(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
