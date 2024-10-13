<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            v-if="$store.getters.permission.includes('system:log:delete')"
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-if="$store.getters.permission.includes('system:log:clean')"
            type="danger"
            plain
            icon="el-icon-edit"
            size="mini"
            @click="handleDeleteAll"
          >清空全部
          </el-button>
        </el-col>
        <span style="float: right">
          <el-col :span="1.5">
            <el-tooltip class="item" effect="dark" content="清空" placement="top-start">
              <el-button icon="el-icon-delete" size="mini" circle style="min-width: 0;" @click="resetQuery" />
            </el-tooltip>
          </el-col>
          <el-col :span="1.5">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入模块名称"
              clearable
              size="small"
              style="width: 140px"
              @input="handleQuery"
            />
          </el-col>
          <el-col :span="1.5">
            <el-select
              v-model="queryParams.requestMethod"
              placeholder="请求方式"
              clearable
              size="small"
              style="width: 140px;"
              @clear="queryParams.requestMethod = null"
              @change="handleQuery"
            >
              <el-option
                v-for="dict in $store.getters.dict.sys_request_method"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-col>
          <el-col :span="1.5">
            <el-select
              v-model="queryParams.status"
              placeholder="状态"
              clearable
              size="small"
              style="width: 100px;"
              @clear="queryParams.status = null"
              @change="handleQuery"
            >
              <el-option
                v-for="dict in states"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-col>
          <el-col :span="1.5">
            <el-tooltip class="item" effect="dark" content="刷新" placement="top-start">
              <el-button icon="el-icon-refresh" size="mini" style="min-width: 0;" circle @click="handleQuery" />
            </el-tooltip>
          </el-col>
        </span>
      </el-row>

      <el-table
        v-loading="loading"
        class="table-margin-top-height"
        :data="logList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="模块名称" align="center" prop="title" :show-overflow-tooltip="true" width="200" />
        <el-table-column label="请求方式" align="center" prop="requestMethod" width="90">
          <template slot-scope="scope">
            <dict-tag :options="$store.getters.dict.sys_request_method" :value="scope.row.requestMethod" />
          </template>
        </el-table-column>
        <el-table-column label="ip地址" align="center" prop="operIp" :show-overflow-tooltip="true" />
        <el-table-column label="状态" align="center" prop="status" width="90">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status==1" type="danger">异常</el-tag>
            <el-tag v-if="scope.row.status==0" type="success">正常</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作人" align="center" prop="createdBy" width="120" />
        <el-table-column label="创建时间" align="center" prop="createdTime" width="160" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="140">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              class="el-button-margin-left"
              @click="handleUpdate(scope.row)"
            >查看
            </el-button>
            <el-button
              v-if="$store.getters.permission.includes('system:role:delete')"
              size="mini"
              type="text"
              icon="el-icon-delete"
              class="el-button-margin-left"
              @click="handleDelete(scope.row)"
            >删除
            </el-button>
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

      <RoleDialog ref="userDialog" @closeDialog="closeDialog" />
    </el-card>
  </div>

</template>

<script>
import RoleDialog from './componets/logDialog.vue'
import { listLog, delLog, getLog, delLogAll } from '@/api/system/log'

export default {
  name: 'Index',
  components: { RoleDialog },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 日志表格数据
      logList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        status: null,
        requestMethod: null
      },
      // 状态
      states: [
        {
          value: '0',
          label: '正常'
        },
        {
          value: '1',
          label: '异常'
        }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询日志类型列表 */
    getList() {
      this.loading = true
      listLog(this.queryParams).then(response => {
        this.logList = response.data.records
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
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    /** 查看按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids
      console.log(this.ids)
      getLog(id).then(response => {
        this.$refs.userDialog.form = response.data
        this.$refs.userDialog.open = true
        this.$refs.userDialog.title = '查看日志'
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除日志编号为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delLog(ids).then(response => {
          this.$message.success(response.msg)
        })
        this.getList()
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    /** 删除全部按钮操作 */
    handleDeleteAll() {
      this.$confirm('是否确认清空全部的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delLogAll().then(response => {
          this.$message.success(response.msg)
          this.getList()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    /** 回调*/
    closeDialog() {
      this.getList()
    }
  }
}
</script>
