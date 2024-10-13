<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-table
        v-loading="loading"
        class="table-height"
        :data="commentList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column class-name="small-padding fixed-width">
          <template slot="header">
            <el-row :gutter="10">
              <span>
                <el-col :span="1.5">
                  <el-button
                    v-if="$store.getters.permission.includes('note:comment:delete')"
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
                    v-model="queryParams.content"
                    placeholder="请输入评论内容"
                    clearable
                    size="small"
                    style="width: 150px;"
                    @input="handleQuery"
                  />
                </el-col>
                <el-col :span="1.5">
                  <el-select
                    v-model="queryParams.source"
                    placeholder="评论来源"
                    clearable
                    size="small"
                    style="width: 100px"
                    @clear="queryParams.source = 0"
                    @change="handleQuery"
                  >
                    <el-option
                      v-for="source in sourceList"
                      :key="source.value"
                      :label="source.label"
                      :value="source.value"
                    />
                  </el-select>
                </el-col>
                <el-col :span="1.5">
                  <UserSelect v-model="queryParams.userId" @change="handleQuery" />
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
                <div class="entity-field-wrapper">
                  <el-avatar v-if="scope.row.avatar" :src="image(scope.row.avatar)" />
                  <el-avatar v-else> {{ scope.row.username }}</el-avatar>
                </div>
                <div class="entity-field-wrapper" style="width: 100px; ">
                  {{ scope.row.username }}
                </div>
                <div class="entity-field-wrapper">
                  <div style="display: flex;">
                    <div v-if="scope.row.title" class="gap-2">
                      <el-tag size="mini">文 章</el-tag>
                      <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                        <el-link class="entity-field-title" :underline="false" @click="articleEdit(scope.row)">
                          {{ scope.row.title }}
                        </el-link>
                      </el-tooltip>
                      <svg-icon class="know-button" icon-class="link" @click="articleView(scope.row.articleId)" />
                    </div>
                    <div v-else>
                      <el-tag size="mini">留 言</el-tag>
                      <svg-icon class="know-button" icon-class="link" @click="messageView()" />
                    </div>
                  </div>
                  <span style="display: flex;">{{ scope.row.content }}</span>
                  <span class="gap-2">
                    <span
                      slot="reference"
                      class="text-xs"
                      style="cursor: pointer;"
                      @click="handleReply(scope.row)"
                    > {{ scope.row.children == null ? 0 : scope.row.children.length }}条回复</span>
                    <span
                      v-if="$store.getters.permission.includes('note:comment:add')"
                      class="text-xs"
                      style="cursor: pointer;"
                      @click="handleAdd(scope.row)"
                    >回复</span>
                  </span>
                </div>
              </div>
              <div class="entity-end text-xs text-color">
                {{ scope.row.createdTime }}
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
                      v-if="$store.getters.permission.includes('note:comment:add')"
                      icon="el-icon-edit"
                      @click.native="handleAdd(scope.row)"
                    >
                      回 复
                    </el-dropdown-item>
                    <template
                      v-if="$store.getters.name.includes(scope.row.username)&&$store.getters.roles.includes('user')"
                    >
                      <el-dropdown-item
                        v-if="$store.getters.permission.includes('note:comment:delete')"
                        divided
                        icon="el-icon-delete"
                        style="color: red;"
                        hover-colo="red"
                        @click.native="handleDelete(scope.row)"
                      >
                        删 除
                      </el-dropdown-item>
                    </template>
                    <template v-if="$store.getters.roles.includes('admin')">
                      <el-dropdown-item
                        v-if="$store.getters.permission.includes('note:comment:delete')"
                        icon="el-icon-delete"
                        divided
                        style="color: red;"
                        hover-colo="red"
                        @click.native="handleDelete(scope.row)"
                      >
                        删 除
                      </el-dropdown-item>
                    </template>
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
      <CommentDialog ref="commentDialog" @closeDialog="closeDialog" />
      <ReplyDialog :reply-dialog="replyDialog" />
    </el-card>
  </div>
</template>

<script>
import { listComment, delComment } from '@/api/note/comment'
import CommentDialog from '@/views/note/comment/componets/commentDialog.vue'
import ReplyDialog from '@/views/note/comment/componets/ReplyDialog.vue'
import { articleEdit, articleView, image, messageView } from '@/utils/common'
import UserSelect from '@/components/Select/UserSelect.vue'

export default {
  name: 'Index',
  components: { UserSelect, CommentDialog, ReplyDialog },
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
      // 评论表格数据
      commentList: [],
      // 评论类型
      discussantList: [{ 'value': 1, 'label': '我的评论' }, { 'value': 2, 'label': '回复我的' }],
      // 评论来源
      sourceList: [{ 'value': 0, 'label': '全部' }, { 'value': 1, 'label': '文章' }, { 'value': 2, 'label': '留言' }],
      // 是否展示文章标题
      title: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        discussant: 1,
        content: null,
        userId: null,
        source: 0
      },
      replyDialog: {
        open: false,
        title: '回复',
        id: null,
        commentList: []
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    articleView,
    messageView,
    articleEdit,
    // 头像展示
    image,
    /** 查询留言列表 */
    getList() {
      this.loading = true
      if (this.queryParams.source === 2) {
        this.title = false
      } else {
        this.title = true
      }
      listComment(this.queryParams).then(response => {
        this.commentList = response.data.records
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
      this.queryParams.title = null
      this.queryParams.categoryId = null
      this.queryParams.tagId = null
      this.queryParams.isOriginal = null
      this.queryParams.isPush = null
      this.queryParams.userId = null
      this.handleQuery()
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    /** 回复按钮操作 */
    handleAdd(row) {
      this.$refs.commentDialog.reset()
      this.$refs.commentDialog.form.parentId = row.id
      this.$refs.commentDialog.form.articleId = row.articleId
      this.$refs.commentDialog.open = true
      this.$refs.commentDialog.title = '回复'
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除用户编号为"' + ids + '"的数据项？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delComment(ids).then(response => {
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
    },
    // /** 查询当前id下所有评论*/
    handleReply(row) {
      this.replyDialog.commentList = row.children
      this.replyDialog.id = row.id
      this.replyDialog.open = true
    }
  }
}
</script>
<style scoped>
.gap-2 {
  display: inline-flex;
  gap: .5rem;
}
</style>
