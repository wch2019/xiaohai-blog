<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-drawer :title="title" :visible.sync="open">
    <!--  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>-->
    <el-container style="height: 100%;">
      <el-main>
        <el-form ref="form" label-position="top" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="网站名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入网站名称" />
          </el-form-item>
          <el-form-item label="网站地址" prop="url">
            <el-input v-model="form.url" placeholder="请输入网站地址" />
          </el-form-item>
          <el-form-item label="站长邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入站长邮箱" />
          </el-form-item>
          <el-form-item label="网站描述" prop="info">
            <el-input v-model="form.info" placeholder="请输入网站描述" />
          </el-form-item>
          <template v-if="$store.getters.roles.includes('admin')">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="form.sort" :min="0" />
            </el-form-item>
            <el-form-item label="审核回复" prop="reason">
              <el-input v-model="form.reason" placeholder="请输入回复" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in $store.getters.dict.sys_check_state"
                  :key="dict.dictValue"
                  border
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </template>
        </el-form>
      </el-main>
      <el-footer>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </el-footer>
    </el-container>
  </el-drawer>
</template>

<script>
import { addLink, updateLink } from '@/api/note/link'

export default {
  name: 'CategoryDialog',
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
        name: '',
        url: '',
        email: '',
        info: '',
        sort: '0',
        reason: '',
        status: '0'
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: '网站名称不能为空', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '网站地址不能为空', trigger: 'blur' }
        ],
        email: [
          { required: true, validator: validateEmail, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        id: '',
        name: '',
        url: '',
        email: '',
        info: '',
        sort: '0',
        reason: '',
        status: '0'
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== '') {
            updateLink(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addLink(this.form).then(response => {
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
.el-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px;
}
</style>
