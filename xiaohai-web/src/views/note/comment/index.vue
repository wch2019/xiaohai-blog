<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
        <el-form-item label="评论类型" prop="discussant">
          <el-select
            v-model="queryParams.discussant"
            placeholder="评论类型"
            clearable
            size="small"
            style="width: 130px"
            @clear="queryParams.discussant = null"
          >
            <el-option
              v-for="discussant in discussantList"
              :key="discussant.value"
              :label="discussant.label"
              :value="discussant.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评论用户" prop="username">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入评论用户"
            clearable
            size="small"
            style="width: 150px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="评论内容" prop="content">
          <el-input
            v-model="queryParams.content"
            placeholder="请输入评论内容"
            clearable
            size="small"
            style="width: 160px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="评论来源" prop="source">
          <el-select
            v-model="queryParams.source"
            placeholder="评论来源"
            clearable
            size="small"
            style="width: 130px"
            @clear="queryParams.source = 0"
          >
            <el-option
              v-for="source in sourceList"
              :key="source.value"
              :label="source.label"
              :value="source.value"
            />
          </el-select>
        </el-form-item>
        <el-select
          v-model="queryParams.userId"
          placeholder="作者"
          clearable
          size="small"
          style="width: 100px;"
          filterable
          @clear="queryParams.userId = null"
          @change="handleQuery"
        >
          <el-option
            v-for="user in this.$store.getters.users"
            :key="user.id"
            :label="user.nickName"
            :value="user.id"
          >
            <span
              class="text-xs users"
            >
              <el-avatar v-if="user.avatar" size="small" :src="image(user.avatar)" />
              <el-avatar v-else size="small"> {{ user.nickName }}</el-avatar>
              <span>  {{ user.nickName }}</span>
            </span>

          </el-option>
        </el-select>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery('queryForm')">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
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
      </el-row>
      <el-table v-loading="loading" style="margin-top: 10px" :data="commentList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!--        <el-table-column label="评论人头像" align="center" width="120" prop="avatar">-->
        <!--          <template slot-scope="scope">-->
        <!--            <el-avatar v-if="scope.row.avatar" shape="square" :src="image(scope.row.avatar)" />-->
        <!--            <el-avatar v-else shape="square"> {{ scope.row.username }}</el-avatar>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <!--        <el-table-column label="评论人" align="center" prop="username" :show-overflow-tooltip="true" />-->
        <!--        <el-table-column label="回复人头像" align="center" width="120" prop="avatar">-->
        <!--          <template slot-scope="scope">-->
        <!--            <el-avatar v-if="scope.row.replyAvatar" shape="square" :src="image(scope.row.replyAvatar)" />-->
        <!--            <el-avatar v-else shape="square"> {{ scope.row.replyUsername }}</el-avatar>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <!--        <el-table-column label="回复人" align="center" prop="replyUsername" :show-overflow-tooltip="true">-->
        <!--          <template slot-scope="scope">-->
        <!--            <div>{{ scope.row.replyUsername || '无' }}</div>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <!--        <el-table-column v-if="title" label="文章" align="center" prop="title" :show-overflow-tooltip="true">-->
        <!--          <template slot-scope="scope">-->
        <!--            <el-link :underline="false" @click="articleView(scope.row)">{{ scope.row.title }}</el-link>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
        <!--        <el-table-column label="评论内容" align="center" prop="content" :show-overflow-tooltip="true" />-->
        <el-table-column align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <div v-loading="loading" class="entity-body">
              <div class="entity-start">
                <div class="entity-field-wrapper">
                  <el-avatar v-if="scope.row.avatar" :src="image(scope.row.avatar)" />
                  <el-avatar v-else> {{ scope.row.username }}</el-avatar>
                  {{ scope.row.username }}
                </div>
                <div class="entity-field-wrapper">
                  <div v-if="scope.row.title">
                    <el-tag size="mini">文 章</el-tag>
                    <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                      <el-link class="entity-field-title" :underline="false" @click="articleEdit(scope.row)">{{ scope.row.title }}</el-link>
                    </el-tooltip>
                    <svg-icon class="know-button" icon-class="link" @click="articleView(scope.row)" />
                  </div>
                  <div v-else>
                    <el-tag size="mini">留 言</el-tag>
                    <svg-icon class="know-button" icon-class="link" @click="articleView(scope.row)" />
                  </div>
                  <span style="display: flex;" class="gap-2">{{ scope.row.content }}</span>
                  <span style="display: flex;" class="gap-2">
                    <span class="text-xs" style="cursor: pointer;">{{ scope.row.contentCount==null?0: scope.row.contentCount }}条回复</span>
                    <span v-if="$store.getters.permission.includes('note:comment:add')" class="text-xs" style="cursor: pointer;" @click="handleAdd(scope.row)">回复</span>
                  </span>

                </div>
              </div>
              <div class="entity-end">
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
                    <template v-if="$store.getters.name.includes(scope.row.username)&&$store.getters.roles.includes('user')">
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
    </el-card>
  </div>
</template>

<script>
import { listComment, delComment, getComment } from '@/api/note/comment'
import CommentDialog from '@/views/note/comment/componets/commentDialog.vue'
import { articleEdit, articleView, image } from '@/utils/common'

export default {
  name: 'Index',
  components: { CommentDialog },
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
        username: null,
        content: null,
        source: 0
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    articleView,
    articleEdit,
    // 头像展示
    image,
    /** 查询留言列表 */
    getList() {
      this.loading = true
      if (this.queryParams.source == 2) {
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
    resetQuery(formName) {
      this.$refs[formName].resetFields()
      this.handleQuery()
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
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
    /** 查询当前id下所有评论*/
    f(id) {
      getComment(id).then(response => {
        this.commentList = response.data.records
        this.total = response.data.total
        this.loading = false
      })
    }
  }
}
</script>
<style scoped>
.gap-2 {
  gap: .5rem;
}
</style>
