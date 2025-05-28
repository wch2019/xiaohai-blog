<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-row type="flex" justify="space-between">
        <el-col :span="7">
          <ActionButtons
            :buttons="actionButtons"
            :single="single"
            :multiple="multiple"
            @add="handleAdd"
            @update="handleUpdate"
            @delete="handleDelete"
          />
        </el-col>
        <el-col :span="12">
          <QueryFilters
            :query-params="queryParams"
            :filters="queryFilters"
            @reset="resetQuery"
            @query="handleQuery"
          />
        </el-col>
      </el-row>
      <el-table
        v-loading="loading"
        class="table-margin-top-height"
        :data="roleList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" :selectable="judgeSelect" align="center" width="55"/>
        <el-table-column label="头像" align="center" prop="avatar" width="120">
          <template slot-scope="scope">
            <el-avatar v-if="scope.row.avatar" shape="square" :src="image(scope.row)"/>
            <el-avatar v-else shape="square"> {{ scope.row.nickName }}</el-avatar>
          </template>
        </el-table-column>
        <el-table-column label="用户名" align="center" prop="username" :show-overflow-tooltip="true" width="120"/>
        <el-table-column label="用户昵称" align="center" prop="nickName" :show-overflow-tooltip="true" width="120"/>
        <el-table-column label="用户性别" align="center" prop="gender" width="120">
          <template slot-scope="scope">
            <dict-tag :options="$store.getters.dict.sys_user_sex" :value="scope.row.gender"/>
          </template>
        </el-table-column>
        <el-table-column label="角色" align="center" prop="roleIds" width="120">
          <template slot-scope="scope">
            <template v-for="(item, index) in roleOptions">
              <el-tag
                v-if="scope.row.roleIds.includes(item.id)"
                :key="item.id"
                :index="index"
              >
                {{ item.name }}
              </el-tag>
            </template>
          </template>
        </el-table-column>
        <el-table-column label="容量" align="center" prop="disk" min-width="150">
          <template slot-scope="scope">
            <span style="font-size: 12px">{{ scope.row.disk.used }} / {{ scope.row.disk.total }}</span>
            <el-progress :percentage="scope.row.disk.usage" :stroke-width="14" :show-text="false" color="#6f7ad3"/>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status" width="80">
          <template slot-scope="scope">
            <dict-tag :options="$store.getters.dict.sys_normal_disable" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createdTime" width="160"/>
        <el-table-column label="最后登录时间" align="center" prop="loginDate" width="160"/>
        <el-table-column
          v-if="$store.getters.permission.includes('system:user:delete')||$store.getters.permission.includes('system:user:update')"
          label="操作" align="center" class-name="small-padding fixed-width" fixed="right" min-width="140"
        >
          <template slot-scope="scope">
            <el-button
              v-if="$store.getters.permission.includes('system:user:update')"
              size="mini"
              type="text"
              icon="el-icon-edit"
              class="el-button-margin-left"
              @click="handleUpdate(scope.row)"
            >修改
            </el-button>
            <template v-if="scope.row.id!==1">
              <el-button
                v-if="$store.getters.permission.includes('system:user:delete')"
                size="mini"
                type="text"
                icon="el-icon-delete"
                class="el-button-margin-left"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
      <UserDialog ref="userDialog" @closeDialog="closeDialog"/>
    </el-card>
  </div>

</template>

<script>
import UserDialog from './componets/userDialog.vue'
import { listUser, delUser, getUser } from '@/api/system/user'
import { optionSelect } from '@/api/system/role'
import ActionButtons from '@/components/Toolbar/ActionButtons.vue'
import QueryFilters from '@/components/Toolbar/QueryFilters.vue'

export default {
  name: 'Index',
  components: { QueryFilters, ActionButtons, UserDialog },
  data() {
    return {
      actionButtons: [
        { type: 'add', label: '新增', icon: 'el-icon-plus', permission: 'system:user:add' },
        { type: 'update', label: '修改', icon: 'el-icon-edit', permission: 'system:user:update' },
        { type: 'delete', label: '删除', icon: 'el-icon-delete', permission: 'system:user:delete' }
      ],
      queryFilters: [
        { type: 'input', prop: 'username', placeholder: '请输入用户名' },
        { type: 'input', prop: 'nickName', placeholder: '请输入昵称' },
        { type: 'select', prop: 'status', placeholder: '状态', dict: 'sys_normal_disable' }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 角色选择框列表
      roleOptions: [],
      // 用户表格数据
      roleList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: null,
        nickName: null,
        status: null
      }
    }
  },
  created() {
    this.getRoleList()
    this.getList()
  },
  methods: {
    /** 获取角色选择框列表 */
    getRoleList() {
      optionSelect().then(response => {
        this.roleOptions = response.data
      })
    },
    /** 查询用户类型列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.roleList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = Object.assign({}, this.$options.data().queryParams)
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.userDialog.reset()
      this.$refs.userDialog.roleOptions = this.roleOptions
      this.$refs.userDialog.open = true
      this.$refs.userDialog.title = '添加用户'
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 多选框可选择的判断*/
    judgeSelect(row) {
      return row.id !== 1 // 返回true该行可选，返回false则不可选
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const dictId = row?.id || this.ids?.[0]
      if (!dictId) {
        this.$message.warning('请先选择一条数据进行修改')
        return
      }
      getUser(dictId).then(response => {
        const dialog = this.$refs.userDialog
        if (!dialog) {
          this.$message.error('用户弹窗组件未注册或引用失败')
          return
        }
        const formRef = dialog.$refs?.form
        if (formRef) formRef.resetFields()

        dialog.roleOptions = this.roleOptions
        dialog.form = response.data
        dialog.open = true
        dialog.title = '修改用户'
      }).catch(() => {
        this.$message.error('获取用户信息失败')
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row?.id || this.ids
      this.$confirm('是否确认删除用户编号为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delUser(ids).then(response => {
          this.$message.success(response.msg)
          this.getList()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 头像展示
    image(row) {
      return process.env.VUE_APP_BASE_API_FILE + row.avatar
    },
    /** 回调*/
    closeDialog() {
      this.getList()
    }
  }
}
</script>

<style scoped>

</style>
