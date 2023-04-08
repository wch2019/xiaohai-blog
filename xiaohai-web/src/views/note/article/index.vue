<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="文章名称" prop="title">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入文章名称"
          clearable
          size="small"
          style="width: 140px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select
          v-model="queryParams.categoryId"
          placeholder="分类"
          clearable
          size="small"
          style="width: 100px"
          @clear="queryParams.categoryId = null"
        >
          <el-option
            v-for="tags in CategoryList"
            :key="tags.id"
            :label="tags.name"
            :value="tags.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标签" prop="tags">
        <el-select
          v-model="queryParams.tags"
          placeholder="标签"
          clearable
          size="small"
          multiple
          style="width: 100px"
          @clear="queryParams.tags = []"
        >
          <el-option
            v-for="tag in TagsList"
            :key="tag.id"
            :label="tag.name"
            :value="tag.id"
          />
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
          v-if="$store.getters.permission.includes('note:article:add')"
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
          v-if="$store.getters.permission.includes('note:article:update')"
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
          v-if="$store.getters.permission.includes('note:article:delete')"
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      border
      style="margin-top: 10px;width: 100%"
      :row-class-name="tableRowClassName"
      :data="articleList"
      @selection-change="handleSelectionChange"
      @row-dblclick="handle"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="封面" align="center" prop="cover">
        <template slot-scope="scope">
          <el-image :src="scope.row.cover" :preview-src-list="srcList" />
        </template>
      </el-table-column>
      <el-table-column label="文章标题" align="center" prop="title" />
      <el-table-column label="分类" align="center" prop="categoryId">
        <template slot-scope="scope">
          <template v-for="(item,index) in CategoryList">
            <el-tag v-if="item.id===scope.row.categoryId" :key="index" size="small" :label="index" border>{{ item.name }}</el-tag>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="标签" align="center" prop="tags">
        <template slot-scope="scope">
          <template v-for="(item,index) in TagsList">
            <el-tag
              v-if="scope.row.tags.includes(item.id)"
              :key="index"
              style="margin-right:4px"
              type="success"
              size="small"
              :label="index"
              border
            >{{ item.name }}
            </el-tag>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="发布" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.isPush"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="push(scope.row)"
          />
        </template>
      </el-table-column>
      <!--      <el-table-column label="顶置" align="center" prop="isTop" />-->
      <!--      <el-table-column label="原创" align="center" prop="isOriginal" />-->
      <el-table-column label="浏览量" align="center" prop="pageView">
        <template slot-scope="scope">
          <el-tag type="warning"> {{ scope.row.pageView }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createdTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="$store.getters.permission.includes('note:article:update')"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-if="$store.getters.permission.includes('note:article:delete')"
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

  </div>
</template>

<script>
import { listArticle, delArticle, updatePush, updateTop } from '@/api/note/article'
import { optionSelectCategory } from '@/api/note/category'
import { optionSelectTags } from '@/api/note/tags'

export default {
  name: 'Index',
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
      articleList: [],
      // 标签下拉选
      TagsList: [],
      // 分类下拉选
      CategoryList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        categoryId: null,
        tags: []
      },
      srcList: []
    }
  },
  created() {
    this.getCategory()
    this.getTags()
    this.getList()
  },
  methods: {
    /**
     * 查询分类下拉选
     */
    getCategory() {
      optionSelectCategory().then(response => {
        this.CategoryList = response.data
      })
    },
    /**
     * 获取标签选择列表
     */
    getTags() {
      optionSelectTags().then(response => {
        this.TagsList = response.data
      })
    },
    /** 查询标签数据列表 */
    getList() {
      this.loading = true
      listArticle(this.queryParams).then(response => {
        this.articleList = response.data.records
        this.total = response.data.total
        this.loading = false
        // 加载图片
        response.data.records.forEach(key => {
          if (key.cover) {
            key.cover = process.env.VUE_APP_BASE_API_FILE + key.cover
            this.srcList.push(key.cover)
          }
        })
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
      this.$router.push('/note/edit')
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids
      this.$router.push({ path: '/note/edit', query: { id: id }})
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除标签编码为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delArticle(ids).then(response => {
          this.$message.success(response.msg)
        })
        this.getList()
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 顶置颜色样式添加
    tableRowClassName({ row, rowIndex }) {
      if (row.isTop === 1) {
        return 'success-row'
      }
      return ''
    },
    // 是否发布
    push(row) {
      updatePush(row.id).then(response => {
        this.$message.success(response.msg)
      })
    },
    // 双击行顶置
    handle(row, column, event, cell) {
      updateTop(row.id).then(response => {
        this.$message.success(response.msg)
        this.getList()
      })
    }
  }
}
</script>
<style scoped>
::v-deep .el-table .success-row {
  background-color: #f0f9eb
}

.dis {
  border-radius: 100px;
  width: 10px;
  height: 10px;
  background: #39C178;
}
</style>
