<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-drawer :title="title" :visible.sync="open">
    <el-container style="height: 100%;">
      <el-main>
        <el-form ref="form" label-position="top" :model="form" :rules="rules">
          <el-form-item label="分类名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入分类名称" />
          </el-form-item>
          <template v-if="$store.getters.roles.includes('admin')">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="form.sort" :min="0" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in $store.getters.dict.sys_normal_disable"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  border
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
import { addCategory, updateCategory } from '@/api/note/category'

export default {
  name: 'CategoryDialog',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {
        id: '',
        name: '',
        sort: '0',
        status: '0'
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: '分类不能为空', trigger: 'blur' }
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
        sort: '0',
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
            updateCategory(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addCategory(this.form).then(response => {
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
