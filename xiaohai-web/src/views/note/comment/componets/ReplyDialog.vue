<template>
  <el-dialog
    :title="replyDialog.title"
    :visible.sync="replyDialog.open"
    width="80%"
    :show-close="false"
    append-to-body
    center
  >
    <el-table
      :show-header="false"
      :data="replyDialog.commentList"
    >
      <el-table-column class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <div class="entity-body">
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
                  <span v-if="replyDialog.id!==scope.row.parentId" class="gap-2">
                    <el-tag size="mini"> @{{ scope.row.replyUsername }}</el-tag>
                  </span>
                </div>
                <span style="display: flex;">{{ scope.row.content }}</span>
                <span
                  v-if="$store.getters.permission.includes('note:comment:add')"
                  class="text-xs"
                  style="cursor: pointer;"
                  @click="addComment(scope.row)"
                >回复</span>
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
                    @click.native="addComment(scope.row)"
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
  </el-dialog>

</template>

<script>

import { image, getFormattedDate } from '@/utils/common'
import { addComment, delComment } from '@/api/note/comment'

export default {
  props: {
    replyDialog: {
      type: Object,
      default: () => {
        return {
          open: false,
          title: '回复',
          commentList: [],
          id: ''
        }
      }
    }
  },
  data() {
    return {

    }
  },
  methods: {
    image,
    handleDelete(row) {
      const ids = row.id
      this.$confirm('是否确认删除评论"' + row.content + '"？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delComment(ids).then(response => {
          this.$message.success(response.msg)
          this.replyDialog.commentList = this.replyDialog.commentList.filter(element => !ids.includes(element.id))
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    addComment(row) {
      this.$prompt('', '回复', {
        confirmButtonText: '提交',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        const data = {
          parentId: row.id,
          articleId: row.articleId,
          content: value
        }
        addComment(data).then(response => {
          this.$message.success(response.msg)
          data.id = response.data
          data.createdTime = getFormattedDate()
          data.avatar = this.$store.getters.avatar.replace(process.env.VUE_APP_BASE_API_FILE, '')
          data.username = this.$store.getters.name
          data.replyUsername = row.username
          this.replyDialog.commentList.push(data)
          console.log(this.replyDialog.commentList)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    }
  }

}
</script>
<style scoped>

</style>
