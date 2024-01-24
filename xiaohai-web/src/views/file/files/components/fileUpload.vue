<template>
  <div>
    <div class="fixed-button">
      <el-dropdown trigger="click">
        <el-button
          style="width: 56px;height: 56px"
          type="primary"
          icon="el-icon-plus"
          circle
        />
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="handleImport"><i class="el-icon-upload2"/>上传</el-dropdown-item>
          <el-dropdown-item @click.native="addFolder(fileUpload.path)"><i class="el-icon-folder-add"/>新建文件夹
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-dialog
      title="文件上传"
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
        <i class="el-icon-upload"/>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>

    </el-dialog>
  </div>
</template>
<script>
import {newFolder, uploadFile, uploadImage} from '@/api/file/file'

export default {
  name: 'FileUpload',
  props: ['fileUpload'],
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
      // 根据后台需求数据格式
      const form = new FormData()
      // 文件对象
      form.append('file', file)
      form.append('path', this.fileUpload.path)
      uploadFile(form).then(response => {
        this.$message.success(response.msg)
        this.imageUpload.show = false
        this.$emit('getList')
      })
    },
    // 新建文件夹
    addFolder(path) {
      this.$prompt('', '新建文件夹', {
        iconClass: 'el-icon-folder-add',
        center: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^(?!^(CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])$)[^<>:"/\\|?*]+$/,
        inputErrorMessage: '文件名格式不正确'
      }).then(({value}) => {
        const data = {}
        value = '/' + value
        data.path = path ? path + value : value
        newFolder(data).then(response => {
          this.$message.success(response.msg)
          this.$emit('getList', path, true)
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
