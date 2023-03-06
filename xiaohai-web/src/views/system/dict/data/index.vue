<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="字典名称" prop="dictType">
        <el-select v-model="queryParams.dictType" size="small">
          <el-option
            v-for="item in typeOptions"
            :key="item.id"
            :label="item.dictName"
            :value="item.dictType"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="字典标签" prop="dictLabel">
        <el-input
          v-model="queryParams.dictLabel"
          placeholder="请输入字典标签"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="状态"
          clearable
          @clear="queryParams.status = null"
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in $store.getters.dict.sys_normal_disable"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery('queryForm')">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-if="$store.getters.permission.includes('dict:data:add')"
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
          v-if="$store.getters.permission.includes('dict:data:update')"
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
          v-if="$store.getters.permission.includes('dict:data:delete')"
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
        <router-link :to="'/system/dictType/'" class="link-type">
          <el-button
            type="warning"
            plain
            icon="el-icon-back"
            size="mini"
          >后退
          </el-button>
        </router-link>
      </el-col>
    </el-row>

    <el-table v-loading="loading" border style="margin-top: 10px" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="字典标签" align="center" prop="dictLabel">
        <template slot-scope="scope">
          <span v-if="scope.row.style === null || scope.row.style === 'default'">{{ scope.row.dictLabel }}</span>
          <el-tag v-else :type="scope.row.style">{{ scope.row.dictLabel }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="字典键值" align="center" prop="dictValue" />
      <el-table-column label="字典备注" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="$store.getters.dict.sys_normal_disable" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdTime" width="180" />
      <el-table-column label="更新时间" align="center" prop="updatedTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="$store.getters.permission.includes('dict:data:update')"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-if="$store.getters.permission.includes('dict:data:delete')"
            size="mini"
            type="text"
            icon="el-icon-delete"
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

    <DictDialog ref="dictDialog" @closeDialog="closeDialog" />
  </div>
</template>

<script>
import DictDialog from './componets/dataDialog.vue'
import { listDictData, delDictData, getDictData } from '@/api/system/dict/data'
import { optionSelect, getDictType } from '@/api/system/dict/type'

export default {
  name: 'Index',
  components: { DictDialog },
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
      // 字典表格数据
      typeList: [],
      // 类型数据字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictType: null,
        dictLabel: null,
        status: null
      }
    }
  },
  created() {
    this.getType()
    this.getTypeList()
  },
  methods: {
    getType() {
      const id = this.$route.params && this.$route.params.id
      if (id) {
        getDictType(id).then(response => {
          this.queryParams.dictType = response.data.dictType
          this.getList()
        })
      } else {
        this.getList()
      }
    },
    /** 查询字典数据列表 */
    getList() {
      this.loading = true
      listDictData(this.queryParams).then(response => {
        this.typeList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    },
    /** 查询字典类型列表 */
    getTypeList() {
      optionSelect().then(response => {
        this.typeOptions = response.data
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery(formName) {
      this.$refs[formName].resetFields()
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.queryParams.dictType !== null) {
        this.$refs.dictDialog.reset()
        this.$refs.dictDialog.form.dictType = this.queryParams.dictType
        this.$refs.dictDialog.open = true
        this.$refs.dictDialog.title = '添加字典数据'
      } else {
        this.$message.error('请选择字典名称')
      }
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
      getDictData(dictId).then(response => {
        if (this.$refs.dictDialog.$refs['form'] !== undefined) {
          this.$refs.dictDialog.$refs['form'].resetFields()
        }
        this.$refs.dictDialog.form = response.data
        this.$refs.dictDialog.open = true
        this.$refs.dictDialog.title = '修改字典数据'
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除字典编码为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delDictData(ids).then(response => {
          this.$message.success(response.msg)
        })
        this.getList()
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
