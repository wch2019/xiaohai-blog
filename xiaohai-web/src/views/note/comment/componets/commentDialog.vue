<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item prop="content">
        <el-input v-model="form.content" type="textarea" placeholder="请输入回复消息" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addComment } from '@/api/note/comment'

export default {
  name: 'CommentDialog',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {
        parentId: 0,
        articleId: undefined,
        content: ''
      },
      // 表单校验
      rules: {
        content: [
          { required: true, message: '回复消息不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        parentId: 0,
        articleId: undefined,
        content: ''
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
          addComment(this.form).then(response => {
            this.$message.success(response.msg)
            this.open = false
            // 回调父方法
            this.$emit('closeDialog')
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
