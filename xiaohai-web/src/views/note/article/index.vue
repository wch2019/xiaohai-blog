<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-form ref="queryForm" :model="queryParams" :inline="true">
        <el-form-item label="标题" prop="title">
          <el-input
            v-model="queryParams.title"
            placeholder="请输入文章名称"
            clearable
            size="small"
            style="width: 250px;"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select
            v-model="queryParams.categoryId"
            placeholder="分类"
            clearable
            size="small"
            style="width: 100px;"
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
        <el-form-item label="标签" prop="tagId">
          <el-select
            v-model="queryParams.tagId"
            placeholder="标签"
            clearable
            size="small"
            style="width: 100px;"
            @clear="queryParams.tagId = []"
          >
            <el-option
              v-for="tag in TagsList"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="发布" prop="isPush">
          <el-select
            v-model="queryParams.isPush"
            placeholder="发布"
            clearable
            size="small"
            style="width: 100px;"
            @clear="queryParams.isPush = null"
          >
            <el-option
              v-for="(item,index) in isPush"
              :key="index"
              :label="item"
              :value="index"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="isOriginal">
          <el-select
            v-model="queryParams.isOriginal"
            placeholder="类型"
            clearable
            size="small"
            style="width: 100px;"
            @clear="queryParams.isOriginal = null"
          >
            <el-option
              v-for="(item,index) in isOriginal"
              :key="index"
              :label="item"
              :value="index"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery('queryForm')">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="loading"

        style="margin-top: 10px;width: 100%;"
        :row-class-name="tableRowClassName"
        :data="articleList"
        @selection-change="handleSelectionChange"
        @row-dblclick="handle"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column align="center" class-name="small-padding fixed-width">
          <template slot="header">
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
              <el-col :span="1.5">
                <el-button
                  v-if="$store.getters.permission.includes('note:article:reptile')"
                  type="info"
                  plain
                  icon="el-icon-attract"
                  size="mini"
                  @click="handleReptile"
                >抓取
                </el-button>
              </el-col>
            </el-row>
          </template>
          <template slot-scope="scope">
            <div class="entity-body">
              <div class="entity-start">
                <div style="position: relative; width: 130px">
                  <el-image :src="scope.row.cover" style="border-radius:4px" :preview-src-list="srcList">
                    <div
                      slot="error"
                      style="display: flex;
                             justify-content: center;
                             align-items: center;
                             font-size: 14px;
                             color: #c0c4cc;
                             vertical-align: middle;
                             height: 70px"
                    >
                      <i class="el-icon-picture-outline" />
                    </div>
                  </el-image>
                  <svg-icon v-if="scope.row.isTop===1" icon-class="top" style="position: absolute;top: 0;right: 0; font-size: 40px" />
                  <svg-icon v-if="scope.row.isOriginal===1 " icon-class="original" style="position: absolute; bottom: 7px; right: 0;font-size: 10px;" />
                </div>
                <div class="entity-field-wrapper">
                  <div class="entity-field-title-body">
                    <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                      <el-link class="entity-field-title" :underline="false" @click="handleUpdate(scope.row)">{{ scope.row.title }}</el-link>
                    </el-tooltip>
                    <svg-icon v-if="scope.row.categoryId" class="know-button" icon-class="link" @click="onClick(scope.row)" />
                  </div>
                  <div class="entity-field-description-body">
                    <template v-for="(item,index) in CategoryList">
                      <el-tag v-if="item.id===scope.row.categoryId" :key="index" size="small" :label="index" border>{{ item.name }}</el-tag>
                    </template>
                    <template v-for="(item,index) in TagsList">
                      <el-tag
                        v-if="scope.row.tags&&scope.row.tags.split(',').map(Number).includes(item.id)"
                        :key="index"
                        style="margin-right:4px"
                        type="success"
                        size="small"
                        :label="index"
                        border
                      >{{ item.name }}
                      </el-tag>
                    </template>
                  </div>
                </div>
              </div>
              <div class="entity-end">
                <span class="text-xs text-color">访问量 {{ scope.row.pageView }}</span>
                <span>
                  <el-tooltip class="item" effect="dark" :content="scope.row.nickName" placement="top">
                    <el-avatar v-if="scope.row.avatar" size="small" :src="image(scope.row.avatar)" />
                    <el-avatar v-else size="small"> {{ scope.row.nickName }}</el-avatar>
                  </el-tooltip>
                </span>
                <span class="text-xs text-color">{{ scope.row.isPush?"已发布":"未发布" }}</span>
                <span class="text-xs text-color">{{ scope.row.createdTime }}</span>
              </div>
              <div class="entity-dropdown">
                <el-dropdown trigger="click" @visible-change>
                  <el-button
                    style="min-width:5px;padding: 5px; border: none;"
                    class="el-icon-more"
                    size="mini"
                    @click.native.stop
                  />
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item
                      v-if="scope.row.categoryId && !scope.row.isPush"
                      icon="el-icon-s-promotion"
                      @click.native="push(scope.row)"
                    >
                      发 布
                    </el-dropdown-item>
                    <el-dropdown-item
                      v-if="$store.getters.permission.includes('note:article:update')"
                      icon="el-icon-edit"
                      @click.native="handleUpdate(scope.row)"
                    >
                      编 辑
                    </el-dropdown-item>
                    <el-dropdown-item
                      v-if="$store.getters.permission.includes('note:article:delete')"
                      divided
                      icon="el-icon-delete"
                      style="color: red;"
                      hover-colo="red"
                      @click.native="handleDelete(scope.row)"
                    >
                      删 除
                    </el-dropdown-item>
                    <el-dropdown-item
                      v-if="scope.row.categoryId && scope.row.isPush"
                      icon="el-icon-delete"
                      style="color: red;"
                      hover-colo="red"
                      @click.native="push(scope.row)"
                    >
                      取消发布
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
            </div>
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
      <reptile-article v-if="reptileInfo.show" :reptile-info="reptileInfo" @getList="getList" />
    </el-card>
  </div>
</template>

<script>
import { listArticle, delArticle, updatePush, updateTop } from '@/api/note/article'
import { optionSelectCategory } from '@/api/note/category'
import { optionSelectTags } from '@/api/note/tags'
import ReptileArticle from '@/views/note/article/components/ReptileArticle.vue'
import { image } from '@/utils/common'

export default {
  name: 'Index',
  components: { ReptileArticle },
  data() {
    return {
      url: process.env.VUE_APP_BLOG_WEB_API,
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
      isOriginal: ['原创', '转载'],
      isPush: ['草稿', '发布'],
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
        tagId: null
      },
      srcList: [],
      reptileInfo: {
        show: false
      }
    }
  },
  created() {
    this.getCategory()
    this.getTags()
    this.getList()
  },
  methods: {
    image,
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
          if (key.isPush === 1) {
            key.isPush = true
            this.srcList.push(key.isPush)
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
      this.$router.push('/basic/edit')
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
      this.$router.push({ path: '/basic/edit', query: { id: id }})
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
          this.getList()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 抓取
    handleReptile() {
      this.reptileInfo.show = true
    },
    // 顶置颜色样式添加
    tableRowClassName({ row, rowIndex }) {
      // if (row.isTop === 1) {
      //   return 'success-row'
      // }
      return ''
    },
    // 是否发布
    push(row) {
      updatePush(row.id).then(response => {
        this.$message.success(response.msg)
        this.getList()
      })
    },
    renderHeader(h, { column, $index }) {
      return [
        '封面',
        h(
          'el-tooltip',
          {
            props: {
              content: '双击行顶置',
              placement: 'top'
            }
          },
          [
            h('span', {
              class: {
                'el-icon-question': true
              }
            })
          ]
        )
      ]
    },
    // 双击行顶置
    handle(row, column, event, cell) {
      updateTop(row.id).then(response => {
        this.$message.success(response.msg)
        this.getList()
      })
    },
    // 跳转展示文章页
    onClick(row) {
      window.open(this.url + '/article/' + row.id)
    }
  }
}
</script>
<style scoped>
::v-deep .el-table .success-row {
  background-color: #f0f9eb
}

.entity-body{
  display: flex;
  width: 100%;
}
.entity-start{
  display: flex;
  flex: 1 1 0%;
  align-items: center;
  gap: 1rem;
}
.entity-field-wrapper{
  display: inline-flex;
  max-width: 30rem;
  flex-direction: column;
  gap: .25rem;
}
.entity-field-title{
  margin-right: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: .875rem;
  line-height: 1.25rem;
  font-weight: 500;
  --tw-text-opacity: 1;
}
.entity-field-title-body{
  display: inline-flex;
  flex-direction: row;
  align-items: center;
}
.entity-field-description-body{
  display: inline-flex;
  align-items: center;
  gap: .5rem;
}
.entity-end{
  display: inline-flex;
  flex-direction: row;
  align-items: center;
  gap: 1.5rem;
}
.entity-dropdown{
   margin-left: 1rem;
   margin-right: 1rem;
   display: flex;
   align-items: center;
}
.know-button {
  font-size: 10px;
  margin-left: 10px;
  display: none; /* 默认隐藏按钮 */
  cursor: pointer;
}

.entity-body:hover .know-button {
  display: inline-block; /* 鼠标悬停时显示按钮 */
}
</style>
