<template>
  <!-- 添加或修改参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="上级菜单" prop="parentId">
            <!--              <treeselect-->
            <!--                v-model="form.parentId"-->
            <!--                :options="menuOptions"-->
            <!--                :normalizer="normalizer"-->
            <!--                :show-count="true"-->
            <!--                placeholder="选择上级菜单"-->
            <!--              />-->
            <!--            <el-tree :data="menuOptions" :props="defaultProps"  @node-click="handleNodeClick"></el-tree>-->
            <select-tree v-model="form.parentId" :options="menuOptions" :props="defaultProps" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="菜单类型" prop="menuType">
            <el-radio-group v-model="form.menuType">
              <el-radio label="M">目录</el-radio>
              <el-radio label="C">菜单</el-radio>
              <el-radio label="F">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col v-if="form.menuType !== 'F'" :span="24">
          <el-form-item label="菜单图标" prop="icon">
            <el-popover
              placement="bottom-start"
              width="460"
              trigger="click"
              @show="$refs['iconSelect'].reset()"
            >
              <IconSelect ref="iconSelect" @selected="selected" />
              <el-input slot="reference" v-model="form.icon" placeholder="点击选择图标" readonly>
                <svg-icon v-if="form.icon && form.icon.indexOf('el-icon')== -1" slot="prefix" :icon-class="form.icon"/>
                <i v-else-if="form.icon" slot="prefix" :class="form.icon"/>
                <i v-else slot="prefix" class="el-icon-search" />
              </el-input>
            </el-popover>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.menuSort" controls-position="right" :min="0" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.menuType !== 'F'" :span="24">
          <el-form-item prop="path">
            <span slot="label">
              <el-tooltip content="访问的路由地址，如：`user`" placement="top">
                <i class="el-icon-question" />
              </el-tooltip>
              路由地址
            </span>
            <el-input v-model="form.path" placeholder="请输入路由地址" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.menuType === 'C'" :span="24">
          <el-form-item prop="component">
            <span slot="label">
              <el-tooltip content="路径，如：`system/user/index" placement="top">
                <i class="el-icon-question" />
              </el-tooltip>
              组件路径
            </span>
            <el-input v-model="form.component" placeholder="请输入组件路径" />
          </el-form-item>
        </el-col>
        <el-col v-if="form.menuType !== 'M'" :span="24">
          <el-form-item prop="perms">
            <el-input v-model="form.perms" placeholder="请输入权限标识" maxlength="100" />
            <span slot="label">
              <el-tooltip
                content="权限字符，如：system:user:list"
                placement="top"
              >
                <i class="el-icon-question" />
              </el-tooltip>
              权限字符
            </span>
          </el-form-item>
        </el-col>
        <el-col v-if="form.menuType !== 'F'" :span="24">
          <el-form-item prop="status">
            <span slot="label">
              <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                <i class="el-icon-question" />
              </el-tooltip>
              菜单状态
            </span>
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
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import IconSelect from '@/components/IconSelect'
import SelectTree from '@/components/SelectTree'
import { addMenu, updateMenu } from '@/api/system/menu'

export default {
  name: 'MenuDialog',
  components: {
    IconSelect, SelectTree
  },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {
        id: '',
        parentId: '',
        icon: '',
        menuSort: '',
        menuName: '',
        menuType: '',
        path: '',
        component: '',
        perms: '',
        status: '0'
      },
      // 菜单选择框列表
      menuOptions: [],
      defaultProps: {
        // 父级唯一标识
        parent: 'parentId',
        // 唯一标识
        value: 'id',
        // 标签显示
        label: 'menuName',
        // 子级
        children: 'children'
      },
      // 表单校验
      rules: {
        menuName: [
          { required: true, message: '菜单名称不能为空', trigger: 'blur' }
        ],
        menuSort: [
          { required: true, message: '菜单顺序不能为空', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '路由地址不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 选择图标
    selected(name) {
      this.$set(this.form, 'icon', name)
    },
    // 表单重置
    reset() {
      this.form = {
        id: '',
        parentId: '',
        icon: '',
        menuSort: '',
        menuName: '',
        menuType: '',
        path: '',
        component: '',
        perms: '',
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
            updateMenu(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addMenu(this.form).then(response => {
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
