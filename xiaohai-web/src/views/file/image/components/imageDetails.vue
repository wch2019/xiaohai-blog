<script >
import { getMarkdownAddress } from '@/utils/common'

export default {
  name: 'ImageDetails',
  props: ['imageDetails'],
  data() {
    return {
      fileDocument: this.imageDetails.fileDocument
    }
  },
  mounted() {

  },
  methods: {
    // Markdown路径
    getMarkdown(name, path) {
      return getMarkdownAddress(name, path)
    },
    // 复制操作
    copy(context) {
      navigator.clipboard.writeText(context).then(() => {
        this.$message.success('复制成功')
      }).catch(() => {
        this.$message.error('复制失败')
      })
    },
    // 取消按钮
    cancel() {
      this.imageDetails.show = false
    }
  }
}
</script>

<template>
  <div>
    <el-dialog
      title="详情"
      :visible.sync="imageDetails.show"
      width="50%"
    >
      <div style="display: flex;justify-content: flex-start; overflow-y: auto;">
        <div style="object-fit: contain; width: 40%;">
          <el-image
            :src="imageDetails.fileDocument.filePath"
            style="width: 100%"
          />
        </div>
        <div style="width:45%;margin-left: 5%;">
          <h4>名称：</h4>
          <span class="title">{{ fileDocument.fileName }}</span>
          <el-divider />
          <h4>文件大小：</h4>
          <span class="title">{{ fileDocument.fileSize }}</span>
          <el-divider />
          <h4>上传日期：</h4>
          <span class="title">{{ fileDocument.createdTime }}</span>
          <el-divider />
          <h4>更新日期：</h4>
          <span class="title">{{ fileDocument.updatedTime }}</span>
          <el-divider />
          <h4>普通链接：
            <el-button class="el-icon-document-copy" type="text" @click="copy(fileDocument.filePath)" />
          </h4>
          <el-link
            :underline="false"
            type="primary"
            class="title"
            style="color: #409eff;"
            target="_blank"
            :href="fileDocument.filePath"
          >
            {{ fileDocument.filePath }}
          </el-link>
          <el-divider />
          <h4>Markdown 格式：
            <el-button class="el-icon-document-copy" type="text" @click="copy(getMarkdown(fileDocument.fileName, fileDocument.filePath))" />
          </h4>
          <span class="title">{{ getMarkdown(fileDocument.fileName, fileDocument.filePath) }}</span>
          <el-divider />
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="cancel()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<style scoped>
el-divider {
  margin: 0;
}

h4 {
  margin: 14px 0;
}

::v-deep .el-divider--horizontal {
  display: block;
  height: 1px;
  width: 100%;
  margin: 15px 0;
}
</style>
