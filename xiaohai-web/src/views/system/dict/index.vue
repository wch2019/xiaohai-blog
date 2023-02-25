<template>
  <div class="app-container">
    <!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">-->
    <!--      <el-form-item label="字典名称" prop="dictName">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.dictName"-->
    <!--          placeholder="请输入字典名称"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          style="width: 240px"-->
    <!--          @keyup.enter.native="handleQuery"-->
    <!--        />-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="字典类型" prop="dictType">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.dictType"-->
    <!--          placeholder="请输入字典类型"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          style="width: 240px"-->
    <!--          @keyup.enter.native="handleQuery"-->
    <!--        />-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="状态" prop="status">-->
    <!--        <el-select-->
    <!--          v-model="queryParams.status"-->
    <!--          placeholder="字典状态"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          style="width: 240px"-->
    <!--        >-->
    <!--          <el-option-->
    <!--            v-for="dict in dict.type.sys_normal_disable"-->
    <!--            :key="dict.value"-->
    <!--            :label="dict.label"-->
    <!--            :value="dict.value"-->
    <!--          />-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="创建时间">-->
    <!--        <el-date-picker-->
    <!--          v-model="dateRange"-->
    <!--          size="small"-->
    <!--          style="width: 240px"-->
    <!--          value-format="yyyy-MM-dd"-->
    <!--          type="daterange"-->
    <!--          range-separator="-"-->
    <!--          start-placeholder="开始日期"-->
    <!--          end-placeholder="结束日期"-->
    <!--        ></el-date-picker>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item>-->
    <!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
    <!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
    <!--      </el-form-item>-->
    <!--    </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
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
          type="danger"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefreshCache"
        >刷新缓存
        </el-button>
      </el-col>
      <!--      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />-->
    </el-row>

        <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="字典编号" align="center" prop="id" />
          <el-table-column label="字典名称" align="center" prop="dictName" :show-overflow-tooltip="true" />
          <el-table-column label="字典类型" align="center" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <router-link :to="'/system/dict-data/index/' + scope.row.dictId" class="link-type">
                <span>{{ scope.row.dictType }}</span>
              </router-link>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" prop="status">
<!--            <template slot-scope="scope">-->
<!--              <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status" />-->
<!--            </template>-->
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
          <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
<!--            <template slot-scope="scope">-->
<!--              <el-button-->
<!--                v-hasPermi="['system:dict:edit']"-->
<!--                size="mini"-->
<!--                type="text"-->
<!--                icon="el-icon-edit"-->
<!--                @click="handleUpdate(scope.row)"-->
<!--              >修改</el-button>-->
<!--              <el-button-->
<!--                v-hasPermi="['system:dict:remove']"-->
<!--                size="mini"-->
<!--                type="text"-->
<!--                icon="el-icon-delete"-->
<!--                @click="handleDelete(scope.row)"-->
<!--              >删除</el-button>-->
<!--            </template>-->
          </el-table-column>
        </el-table>

    <!--    <pagination-->
    <!--      v-show="total>0"-->
    <!--      :total="total"-->
    <!--      :page.sync="queryParams.pageNum"-->
    <!--      :limit.sync="queryParams.pageSize"-->
    <!--      @pagination="getList"-->
    <!--    />-->
    <DictDialog ref="dictDialog" @closeDialog="closeDialog" />
  </div>

</template>

<script>
import DictDialog from './componets/dictDialog.vue'
import { listDictType, addDictType, delDictType, updateDictType, getDictType, refreshDict } from '@/api/system/dict'

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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 字典表格数据
      typeList: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictName: '',
        dictType: '',
        status: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询字典类型列表 */
    getList() {
      this.loading = true
      listDictType(this.queryParams).then(response => {
          console.log(response)
        this.typeList = response.data.records
        this.total = response.data.total
        this.loading = false
      }
      )
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.dictDialog.open = true
      this.$refs.dictDialog.title = '添加字典类型'
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const dictId = row.dictId || this.ids
      getDictType(dictId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改字典类型'
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const dictIds = row.dictId || this.ids
      this.$modal.confirm('是否确认删除字典编号为"' + dictIds + '"的数据项？').then(function() {
        return delDictType(dictIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 刷新缓存按钮操作 */
    handleRefreshCache() {
      refreshDict().then(response => {
        this.$message.success(response.msg)
      })
    },
    closeDialog(val) {
      console.log(val, 'val')
      console.log('列表')
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
