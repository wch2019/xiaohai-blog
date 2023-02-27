<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="字典类型">
        <el-input v-model="form.dictType" :disabled="true" />
      </el-form-item>
      <el-form-item label="数据标签" prop="dictLabel">
        <el-input v-model="form.dictLabel" placeholder="请输入数据标签" />
      </el-form-item>
      <el-form-item label="数据键值" prop="dictValue">
        <el-input v-model="form.dictValue" placeholder="请输入数据键值" />
      </el-form-item>
      <el-form-item label="回显样式" prop="listClass">
        <el-select v-model="form.style">
          <el-option
            v-for="item in listClassOptions"
            :key="item.value"
            :label="item.label + '(' + item.value + ')'"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="显示排序" prop="dictSort">
        <el-input-number v-model="form.dictSort" controls-position="right" :min="0" />
      </el-form-item>
      <!--        <el-form-item label="状态" prop="status">-->
      <!--          <el-radio-group v-model="form.status">-->
      <!--            <el-radio-->
      <!--              v-for="dict in dict.type.sys_normal_disable"-->
      <!--              :key="dict.value"-->
      <!--              :label="dict.value"-->
      <!--            >{{ dict.label }}</el-radio>-->
      <!--          </el-radio-group>-->
      <!--        </el-form-item>-->
      <el-form-item label="备注">
        <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addDictData, updateDictData } from '@/api/system/dict/data'

export default {
  name: 'DictDialog',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {
        id: '',
        dictName: '',
        dictType: '',
        status: '0',
        style: '',
        remark: ''
      },
      // 数据标签回显样式
      listClassOptions: [
        {
          value: 'default',
          label: '默认'
        },
        {
          value: 'success',
          label: '成功'
        },
        {
          value: 'info',
          label: '信息'
        },
        {
          value: 'warning',
          label: '警告'
        },
        {
          value: 'danger',
          label: '危险'
        }
      ],
      // 表单校验
      rules: {
        dictLabel: [
          { required: true, message: '数据标签不能为空', trigger: 'blur' }
        ],
        dictValue: [
          { required: true, message: '数据键值不能为空', trigger: 'blur' }
        ],
        dictSort: [
          { required: true, message: '数据顺序不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        id: '',
        dictLabel: '',
        dictValue: '',
        dictType: '',
        dictSort: 0,
        status: '0',
        style: '',
        remark: ''
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
            updateDictData(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addDictData(this.form).then(response => {
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
