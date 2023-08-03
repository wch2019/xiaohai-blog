<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标题名称" prop="title">
        <el-input v-model="form.title" maxlength="20" show-word-limit placeholder="请输入标题名称" />
      </el-form-item>
      <el-form-item label="反馈内容" prop="content">
        <el-input v-model="form.content" maxlength="1000" show-word-limit type="textarea" :autosize="{ minRows: 4}" placeholder="请输入反馈内容" />
      </el-form-item>
      <template v-if="$store.getters.roles.includes('admin')">
        <el-form-item label="审核回复" prop="reason">
          <el-input v-model="form.reason" maxlength="255" show-word-limit type="textarea" :autosize="{ minRows: 4}" placeholder="请输入回复" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in $store.getters.dict.sys_check_state"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{ dict.dictLabel }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </template>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addFeedback, updateFeedback } from '@/api/system/feedback'

export default {
  name: 'FeedbackDialog',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {
        id: '',
        title: '',
        content: '',
        reason: '',
        status: '0'
      },
      // 表单校验
      rules: {
        title: [
          { required: true, message: '标题名称不能为空', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '反馈内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        id: '',
        title: '',
        content: '',
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
            updateFeedback(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addFeedback(this.form).then(response => {
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
