<template>
  <div class="app-container">
    <el-breadcrumb
      separator-class="el-icon-arrow-right"
      style="padding-bottom: 20px;font-size: 16px;font-weight: bold;"
    >
      <el-breadcrumb-item
        v-for="(item, index) in breadcrumb.pathList"
        :key="index"
        :to="{}"
        @click.native="getList(breadcrumb.pathMap.get(item))"
      >
        <span :class="{ 'bold-text': isLastBreadcrumb(index) }">{{ item }}</span></el-breadcrumb-item>
    </el-breadcrumb>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
          <span v-if="selectedItems.length===0">共 {{ fileList.length }} 项</span>
          <span v-else>已选 {{ selectedItems.length }} 项</span>
        </el-checkbox>
      </el-col>
    </el-row>
    <el-table
      ref="multipleTable"
      tooltip-effect="dark"
      :data="fileList"
      style="width: 100%"
      height="550"
      @row-click="handle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="50"
      />
      <el-table-column prop="fileName" label="名称">
        <template slot-scope="scope">
          <i v-if="!scope.row.suffix" class="el-icon-folder-opened" />
          <svg-icon v-if="!scope.row.suffix" icon-class="folder" />
          <i v-else-if="picture(scope.row.suffix)" class="el-icon-picture" />
          <i v-else class="el-icon-document" />
          {{ scope.row.fileName }}
          <div class="dropdown">
            <el-dropdown trigger="click">
              <el-button
                style="padding: 5px; border: none;"
                class="el-icon-more"
                size="mini"
                @click.native.stop
              />
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="renameFile(scope.row)">重命名</el-dropdown-item>
                <el-dropdown-item v-if="scope.row.fileType===0" @click.native="downloadMultipleFiles(scope.row)">下载
                </el-dropdown-item>
                <el-dropdown-item @click.native="dialog(scope.row)">查看详情</el-dropdown-item>
                <el-dropdown-item divided @click.native="handleDelete(scope.row)">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="createdTime" label="创建时间" align="center" width="180" />
      <el-table-column prop="fileSize" label="文件大小" align="center" width="100" />
    </el-table>

    <FileUpload :file-details="fileDetails" @getList="getList" />
    <FileDetails v-if="imageDetails.show" :image-details="imageDetails" />
  </div>
</template>

<script>
import { delFileIds, getFile, renameFile } from '@/api/file/file'
import { downloadFile, getFileAddress, getFileExtension, VerifyIsPictureType } from '@/utils/common'
import FileUpload from '@/views/file/files/components/fileUpload.vue'
import FileDetails from '@/views/file/files/components/fileDetails.vue'

export default {
  name: 'Index',
  components: { FileDetails, FileUpload },
  data() {
    return {
      form: {
        path: '',
        pageNum: 1,
        pageSize: 10
      },
      breadcrumb: {
        text: '全部文件',
        pathMap: {},
        pathList: []
      },
      imageDetails: {
        show: false,
        fileDocument: {},
        breadcrumb: {}
      },
      fileList: [],
      // 预览图片列表
      srcList: [],
      fileDetails: {},
      // 选中的项
      selectedItems: [],
      checkAll: false,
      isIndeterminate: false
    }
  },
  created() {
    this.getList('')
  },
  methods: {
    handleSelectionChange(selection) {
      console.log(selection)
      const checkedCount = selection.length
      // 处理行选择事件
      this.selectedItems = selection
      this.checkAll = checkedCount === this.selectedItems.length && checkedCount !== 0
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.fileList.length
    },
    handleCheckAllChange() {
      // 处理全选事件
      // 在这里可以根据 isSelectedAll 来控制表格中的行选择
      if (this.checkAll) {
        for (const element of this.fileList) {
          this.$refs.multipleTable.toggleRowSelection(element)
        }
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    getList(path) {
      this.form.path = path
      getFile(this.form).then(response => {
        this.srcList = []
        for (const element of response.data.records) {
          const suffix = getFileExtension(element.fileName)
          element.suffix = suffix
          if (VerifyIsPictureType(suffix)) {
            element.filePath = getFileAddress(element.filePath)
            this.srcList.push(element.filePath)
          }
        }
        this.fileList = response.data.records
      })
      this.getPathList()
    },
    // 面包屑数据封装
    getPathList() {
      const map = new Map()
      const pathSegments = this.form.path.split('/').map(segment => {
        if (segment === '') {
          return this.breadcrumb.text
        }
        return segment
      })
      this.breadcrumb.pathList = pathSegments
      let currentPath = ''
      pathSegments.map(segment => {
        if (segment === this.breadcrumb.text) {
          map.set(segment, '')
        } else {
          currentPath += '/' + segment
          map.set(segment, currentPath)
        }
      })
      this.breadcrumb.pathMap = map
    },
    // 面包屑最后一个数据加粗
    isLastBreadcrumb(index) {
      return index === this.breadcrumb.pathList.length - 1
    },
    // 验证是否是图片类型 VerifyIsPictureType
    picture(name) {
      return VerifyIsPictureType(name)
    },
    // 单击行
    handle(row, column, event, cell) {
      // 进入目录
      if (row.fileType === 1) {
        this.getList(row.filePath)
      }
      // 查看照片
      if (this.picture(row.suffix)) {
        this.show(row.filePath)
      }
    },
    // 图片预览
    show(path) {
      const index = this.srcList.indexOf(path)
      this.$viewerApi({
        images: this.srcList,
        options: {
          toolbar: true,
          initialViewIndex: index
        }
      })
    },
    // 重命名
    renameFile(o) {
      let suffix = ''
      if (o.suffix !== null) {
        suffix = '.' + o.suffix
      }
      this.$prompt('', '重命名', {
        iconClass: 'el-icon-edit',
        center: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^(?!^(CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])$)[^<>:"/\\|?*]+$/,
        inputErrorMessage: '文件名格式不正确',
        inputValue: o.fileName.replace(suffix, '')
      }).then(({ value }) => {
        const data = {}
        data.fileName = value + suffix
        data.id = o.id
        renameFile(data).then(response => {
          this.$message.success(response.msg)
          o.fileName = data.fileName
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    // 下载文件
    downloadMultipleFiles(o) {
      const ids = [o.id] || this.selectedItems
      this.fileList.forEach(element => {
        if (ids.includes(element.id)) {
          downloadFile(element.fileName, window.location.origin + element.filePath)
        }
      })
    },
    // 详情
    dialog(o) {
      this.imageDetails.fileDocument = o
      this.imageDetails.breadcrumb = this.breadcrumb
      this.imageDetails.show = true
    },
    // 删除
    handleDelete(o) {
      const ids = [o.id] || this.selectedItems
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFileIds(ids).then(response => {
          this.$message.success(response.msg)
          this.fileList = this.fileList.filter(element => !ids.includes(element.id))
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    }
  }
}
</script>

<style scoped>

.image {
  width: 100%;
  height: 100px;
  display: block;
}

::v-deep .el-table__header-wrapper .el-checkbox {
  visibility: hidden;
}

.dropdown {
  z-index: 1;
  position: absolute;
  top: 12px;
  right: 5px;
  //display: none;
}

.hover-element:hover .dropdown {
  display: block;
}

.bold-text {
  font-weight: bold;
}
</style>
