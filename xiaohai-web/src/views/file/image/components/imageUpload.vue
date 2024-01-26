<template>
  <div>
    <div class="fixed-button">
      <el-button
        v-if="$store.getters.permission.includes('file:image:upload')"
        style="width: 56px;height: 56px"
        type="primary"
        icon="el-icon-plus"
        circle
        @click="handleImport"
      />
    </div>

    <el-dialog
      title="图片上传"
      :visible.sync="imageUpload.show"
      width="30%"
    >
      <el-upload
        class="upload-demo"
        drag
        action="#"
        :show-file-list="false"
        :http-request="uploadSectionFile"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>

    </el-dialog>
  </div>
</template>
<script>
import { uploadImage } from '@/api/file/file'

export default {
  name: 'ImageUpload',
  data() {
    return {
      imageUpload: {
        show: false
      }
    }
  },
  mounted() {
  },
  methods: {
    handleImport() {
      this.imageUpload.show = true
    },
    // 覆盖默认的上传行为
    uploadSectionFile(params) {
      const file = params.file
      const fileType = file.type
      const isImage = fileType.indexOf('image') !== -1
      const isLt2M = file.size / 1024 / 1024 < 2
      // 这里常规检验，看项目需求而定
      if (!isImage) {
        this.$message.error('只能上传图片格式png、jpg、gif!')
        return
      }
      if (!isLt2M) {
        this.$message.error('只能上传图片大小小于2M')
        return
      }
      // 根据后台需求数据格式
      const form = new FormData()
      // 文件对象
      form.append('file', file)
      uploadImage(form).then(response => {
        this.$message.success(response.msg)
        this.imageUpload.show = false
        this.$emit('getList')
      })
    }
  }
}
</script>
<style scoped>
/* 定义右下角按钮容器的样式 */
.fixed-button {
  position: fixed; /* 固定定位，使按钮保持在页面右下角 */
  bottom: 80px; /* 距离底部的距离，根据需要调整 */
  left: 95%;
  transform: translateX(-95%);
}

::v-deep .el-upload,
::v-deep .el-upload-dragger {
  width: 100%;
}
</style>
