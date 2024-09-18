<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-table
        v-loading="loading"
        :data="articleList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column align="center" class-name="small-padding fixed-width">
          <template slot="header">
            <el-row :gutter="10">
              <span>
                <el-col :span="1.5">
                  <el-button
                    v-if="$store.getters.permission.includes('note:like:delete')"
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
                    v-model="queryParams.title"
                    placeholder="请输入文章名称"
                    clearable
                    size="small"
                    style="width: 150px;"
                    @input="handleQuery"
                  />
                </el-col>
                <el-col :span="1.5">
                  <el-select
                    v-model="queryParams.tagId"
                    placeholder="标签"
                    clearable
                    size="small"
                    style="width: 100px;"
                    filterable
                    @clear="queryParams.tagId = []"
                    @change="handleQuery"
                  >
                    <el-option
                      v-for="tag in TagsList"
                      :key="tag.id"
                      :label="tag.name"
                      :value="tag.id"
                    />
                  </el-select>
                </el-col>
                <el-col :span="1.5">
                  <el-select
                    v-model="queryParams.categoryId"
                    placeholder="分类"
                    clearable
                    size="small"
                    style="width: 100px;"
                    filterable
                    @clear="queryParams.categoryId = null"
                    @change="handleQuery"
                  >
                    <el-option
                      v-for="tags in CategoryList"
                      :key="tags.id"
                      :label="tags.name"
                      :value="tags.id"
                    />
                  </el-select>
                </el-col>
                <el-col :span="1.5">
                  <el-tooltip class="item" effect="dark" content="刷新" placement="top-start">
                    <el-button icon="el-icon-refresh" size="mini" style="min-width: 0;" circle @click="handleQuery" />
                  </el-tooltip>

                </el-col></span>

            </el-row>
          </template>
          <template slot-scope="scope">
            <div v-loading="loading" class="entity-body">
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
                </div>
                <div class="entity-field-wrapper">
                  <div class="entity-field-title-body">
                    <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                      <el-link class="entity-field-title" :underline="false" @click="articleView(scope.row.id)">{{ scope.row.title }}</el-link>
                    </el-tooltip>
                    <svg-icon v-if="scope.row.categoryId" class="know-button" icon-class="link" @click="articleView(scope.row.id)" />
                  </div>
                  <div class="entity-field-description-body">
                    <span class="entity-field-description-view">
                      <span class="text-xs text-color">访问量 {{ scope.row.pageView }}</span>
                      <span class="text-xs text-color">评论数 {{ scope.row.commentCount }}</span>
                      <span class="text-xs text-color">点赞量 {{ scope.row.likeCount }}</span>
                    </span>
                    <span class="entity-field-description-tag">
                      <template v-for="(item,index) in CategoryList">
                        <el-tag v-if="item.id===scope.row.categoryId" :key="index" size="small" :label="index" border>{{ item.name }}</el-tag>
                      </template>
                      <template v-for="(item,index) in TagsList">
                        <el-tag
                          v-if="scope.row.tags&&scope.row.tags.split(',').map(Number).includes(item.id)"
                          :key="item.id"
                          style="margin-right:4px"
                          type="success"
                          size="small"
                          :label="index"
                          border
                        >{{ item.name }}
                        </el-tag>
                      </template>
                    </span>
                  </div>
                </div>
              </div>
              <div class="entity-end">
                <span>
                  <el-tooltip class="item" effect="dark" :content="scope.row.nickName" placement="top">
                    <el-avatar v-if="scope.row.avatar" size="small" :src="image(scope.row.avatar)" />
                    <el-avatar v-else size="small"> {{ scope.row.nickName }}</el-avatar>
                  </el-tooltip>
                </span>
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
                      v-if="$store.getters.permission.includes('note:like:delete')"
                      icon="el-icon-delete"
                      style="color: red;"
                      hover-colo="red"
                      @click.native="handleDelete(scope.row)"
                    >
                      删 除
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
    </el-card>
  </div>
</template>

<script>
import { optionSelectCategory } from '@/api/note/category'
import { optionSelectTags } from '@/api/note/tags'
import { delLike, listLikes } from '@/api/note/like'
import { articleView, image } from '@/utils/common'

export default {
  name: 'Index',
  data() {
    return {
      url: process.env.VUE_APP_BLOG_WEB_API,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
        tagId: null
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
    image,
    articleView,
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
      listLikes(this.queryParams).then(response => {
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
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除喜欢编码为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delLike(ids).then(response => {
          this.$message.success(response.msg)
          this.getList()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
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
.entity-field-description-body{
  display: inline-flex;
  flex-direction: column;
  gap: .5rem;
}
.entity-field-description-tag{
  display: inline-flex;
  align-items: center;
  gap: .5rem;
}
.entity-field-description-view{
  display: inline-flex;
  gap: .5rem;
}
</style>
