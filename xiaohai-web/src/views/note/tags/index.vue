<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">

      <el-row :gutter="10" class="mb8">
        <span>
          <el-col :span="1.5">
            <el-button
              v-if="$store.getters.permission.includes('note:tags:add')"
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-if="$store.getters.permission.includes('note:tags:update')"
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
            >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-if="$store.getters.permission.includes('note:tags:delete')"
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
            >删除
            </el-button>
          </el-col>
        </span>

        <span style="float: right">
          <el-col :span="1.5">
            <el-tooltip class="item" effect="dark" content="清空" placement="top-start">
              <el-button icon="el-icon-delete" size="mini" circle style="min-width: 0;" @click="resetQuery" />
            </el-tooltip>
          </el-col>
          <el-col :span="1.5">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入标签名称"
              clearable
              size="small"
              style="width: 150px;"
              @input="handleQuery"
            />
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
                v-for="dict in $store.getters.dict.sys_normal_disable"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
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
        border
        class="table-margin-top-height"
        :data="tagsList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="标签名称" align="center" prop="name" />
        <el-table-column label="点击次数" align="center" prop="click">
          <template slot-scope="scope">
            <el-tag type="warning"> {{ scope.row.click }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="$store.getters.dict.sys_normal_disable" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column v-if="$store.getters.permission.includes('note:tags:update')||$store.getters.permission.includes('note:tags:delete')" label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              v-if="$store.getters.permission.includes('note:tags:update')"
              size="mini"
              type="text"
              icon="el-icon-edit"
              class="el-button-margin-left"
              @click="handleUpdate(scope.row)"
            >修改
            </el-button>
            <el-button
              v-if="$store.getters.permission.includes('note:tags:delete')"
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

      <TagsDialog ref="tagsDialog" @closeDialog="closeDialog" />
    </el-card>
  </div>
</template>

<script>
import TagsDialog from './componets/tagsDialog.vue'
import { listTags, delTags, getTags } from '@/api/note/tags'

export default {
  name: 'Index',
  components: { TagsDialog },
  data() {
    return {
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
      // 标签表格数据
      tagsList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        status: null
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询标签数据列表 */
    getList() {
      this.loading = true
      listTags(this.queryParams).then(response => {
        this.tagsList = response.data.records
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
      this.$refs.tagsDialog.open({ type: 'add' })
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const dictId = row.id || this.ids
      this.$refs.tagsDialog.open({ dictId: dictId, type: 'update' })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除标签编码为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delTags(ids).then(response => {
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
