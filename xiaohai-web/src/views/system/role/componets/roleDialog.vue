<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="角色编码" prop="code">
        <el-input v-model="form.code" placeholder="请输入角色编码" />
      </el-form-item>
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="form.name" placeholder="请输入角色名称" />
      </el-form-item>
      <el-form-item label="角色描述">
        <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
      </el-form-item>
      <el-form-item label="菜单权限" prop="menuIds">
        <el-tree
          ref="permsTree"
          :data="menuList"
          show-checkbox
          default-expand-all
          node-key="id"
          highlight-current
          :props="defaultProps"
        />
      </el-form-item>
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addRole, updateRole } from '@/api/system/role'
import { listMenu } from '@/api/system/menu'

export default {
  name: 'RoleDialog',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 菜单表格数据
      menuList: [],
      defaultProps: {
        // 标签显示
        label: 'menuName',
        // 子级
        children: 'children'
      },
      form: {
        id: '',
        name: '',
        code: '',
        status: '0',
        menuIds: [],
        remarks: ''
      },
      // 表单校验
      rules: {
        name: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '角色编码不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询菜单类型列表 */
    getList() {
      listMenu().then(response => {
        // 格式化数据源
        this.menuList = this.formatData(response.data)
      })
    },
    // 格式化数据，递归将空的children置为undefined
    formatData(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          data[i].children = undefined
        } else {
          this.formatData(data[i].children)
        }
      }
      return data
    },
    // 表单重置
    reset() {
      this.form = {
        id: '',
        name: '',
        code: '',
        menuIds: [],
        status: '0',
        remarks: ''
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    /** 提交按钮 */
    submitForm() {
      // 获取父节点 子节点选中了的父节点
      const parentArr = this.$refs.permsTree.getHalfCheckedKeys()
      this.form.menuIds = this.$refs.permsTree.getCheckedKeys()
      parentArr.forEach(item => {
        this.form.menuIds.push(item)
      })
      console.log(this.form.menuIds)

      // this.$refs['form'].validate(valid => {
      //   if (valid) {
      //     if (this.form.id !== '') {
      //       updateRole(this.form).then(response => {
      //         this.$message.success(response.msg)
      //         this.open = false
      //         // 回调父方法
      //         this.$emit('closeDialog')
      //       })
      //     } else {
      //       addRole(this.form).then(response => {
      //         this.$message.success(response.msg)
      //         this.open = false
      //         // 回调父方法
      //         this.$emit('closeDialog')
      //       })
      //     }
      //   }
      // })
    }
  }
}
</script>

<style scoped>

</style>
