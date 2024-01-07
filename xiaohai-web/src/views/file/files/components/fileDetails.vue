<script>
import { getMarkdownAddress, VerifyIsPictureType } from '@/utils/common'

export default {
  name: 'ImageDetails',
  props: ['imageDetails'],
  data() {
    return {
      fileDocument: this.imageDetails.fileDocument,
      breadcrumb: this.imageDetails.breadcrumb
    }
  },
  mounted() {

  },
  methods: {
    VerifyIsPictureType
    // // Markdown路径
    // getMarkdown(name, path) {
    //   return getMarkdownAddress(name, path)
    // },
    // // 复制操作
    // copy(context) {
    //   navigator.clipboard.writeText(context).then(() => {
    //     this.$message.success('复制成功')
    //   }).catch(() => {
    //     this.$message.error('复制失败')
    //   })
    // },
    // // 取消按钮
    // cancel() {
    //   this.imageDetails.show = false
    //   this.reset()
    // }
  }
}
</script>

<template>
  <div>
    <el-dialog
      :title="fileDocument.fileName "
      :visible.sync="imageDetails.show"
      width="50%"
    >
      <div style="display: flex;justify-content: flex-start; overflow-y: auto;">
        <div style="object-fit: contain; width: 40%; text-align: center">
          <el-image
            v-if="VerifyIsPictureType(fileDocument.suffix)"
            :src="imageDetails.fileDocument.filePath"
            style="width: 100%"
          />
          <el-image v-else-if="fileDocument.fileType===1" :src="require(`../../../../assets/disk/folder.png`)" height="180" width="230" />
          <el-image v-else :src="require('../../../../assets/disk/document.png')" height="80" width="80" />
        </div>
        <div style="width:45%;margin-left: 5%;">
          <h4>详细信息</h4>
          <span class="title">{{ fileDocument.fileName }}</span>
          <h4>{{ fileDocument.fileSize }}</h4>
          <span class="title">文件位置</span>
          <h4>
            <el-breadcrumb
              separator-class="el-icon-arrow-right"
              style="margin: 0;"
              class="title"
            >
              <el-breadcrumb-item
                v-for="(item, index) in breadcrumb.pathList"
                :key="index"
                :to="{}"
              >
                <span style="font-weight: bold;">{{ item }}</span>
              </el-breadcrumb-item>
            </el-breadcrumb>
          </h4>
          <span class="title">创建时间</span>
          <h4>{{ fileDocument.createdTime }}</h4>
          <span class="title">更新时间</span>
          <h4>{{ fileDocument.updatedTime }}</h4>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
el-divider {
  margin: 0;
}

.title {
  color: rgb(204, 205, 206)
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

::v-deep .el-breadcrumb__separator[class*=icon] {
  margin: 0;
  font-weight: 400;
}
</style>
