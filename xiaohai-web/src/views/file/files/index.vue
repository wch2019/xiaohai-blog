<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <div style="display: flex;justify-content: space-between;flex-direction: row;align-items: center;">
        <div style="display: flex;justify-content: space-between;flex-direction: column;align-items: flex-start;">
          <el-col :span="1.5">
            <el-breadcrumb
              separator-class="el-icon-arrow-right"
              style="padding-bottom: 20px;font-size: 16px;font-weight: bold;"
            >
              <el-breadcrumb-item
                v-for="(item, index) in breadcrumb.pathList"
                :key="index"
                :to="{}"
                @click.native="getList(breadcrumb.pathMap.get(item),true)"
              >
                <span :class="{ 'bold-text': isLastBreadcrumb(index) }">{{ item }}</span></el-breadcrumb-item>
            </el-breadcrumb>
          </el-col>
          <el-col :span="1.5">
            <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
              <span v-if="selectedItems.length===0">共 {{ fileList.length }} 项</span>
              <span v-else>已选 {{ selectedItems.length }} 项</span>
            </el-checkbox>
          </el-col>
        </div>
        <div style="float: right;width: 20%; max-width: 280px;font-size: 15px;color: #999;">
          <span
            style="display: flex;justify-content: space-between;flex-direction: row;align-items: center;"
          >{{ hardDisk.used }} / {{ hardDisk.total }}
            <el-button type="text" @click="diskDetails.show=true">查看</el-button></span>
          <el-progress :percentage="hardDisk.usage" :stroke-width="14" :show-text="false" color="#6f7ad3" />
        </div>
      </div>
    </el-row>

    <el-table
      ref="multipleTable"
      v-el-table-infinite-scroll="loadMoreData"
      tooltip-effect="dark"
      :data="fileList"
      style="width: 100%"
      height="calc(100vh - 150px)"
      :infinite-scroll-disabled="loading"
      @row-click="handle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="50"
      />
      <el-table-column prop="fileName" label="名称">
        <template slot-scope="scope">
          <el-image
            v-if="!scope.row.suffix"
            :src="require(`../../../assets/disk/folder.png`)"
            style="top: 3px;height:18px; width:23px"
          />
          <el-image
            v-else-if="picture(scope.row.suffix)"
            :src="require(`../../../assets/disk/image.png`)"
            style="top: 3px;height:18px; width:23px"
          />
          <el-image
            v-else
            :src="require('../../../assets/disk/document.png')"
            style="top: 3px;height:20px; width:20px"
          />
          {{ scope.row.fileName }}
          <div class="dropdown">
            <el-dropdown trigger="click" @visible-change>
              <el-button
                style="padding: 5px; border: none;"
                class="el-icon-more"
                size="mini"
                @click.native.stop
              />
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  v-if="$store.getters.permission.includes('file:files:update')"
                  @click.native="renameFile(scope.row)"
                >重命名
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row.fileType===0" @click.native="downloadMultipleFiles(scope.row)">下载
                </el-dropdown-item>
                <el-dropdown-item @click.native="dialog(scope.row)">查看详情</el-dropdown-item>
                <el-dropdown-item
                  v-if="$store.getters.permission.includes('file:files:delete')"
                  divided
                  @click.native="handleDelete(scope.row)"
                >删除
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="createdTime" label="创建时间" align="center" width="180" />
      <el-table-column prop="fileSize" label="文件大小" align="center" width="100" />
      <slot />
      <template slot="append">
        <div class="no-more">
          <p v-loading="loading" />
          <p v-if="noMore" style="bottom: 0; width: 100%; text-align: center;">没有更多了</p>
        </div>
      </template>
    </el-table>
    <el-alert
      v-show="alertVisible"
      class="alert-button"
      center
      :closable="false"
      :class="alertClass"
    >
      <el-row :gutter="8" class="mb8">
        <!--        <el-col :span="1.5">-->
        <!--          <el-tooltip class="item" effect="dark" content="下载" placement="top">-->
        <!--            <el-button type="info" icon="el-icon-download" size="mini" @click="downloadMultipleFiles" />-->
        <!--          </el-tooltip>-->
        <!--        </el-col>-->
        <el-col :span="1.5">
          <el-tooltip
            v-if="$store.getters.permission.includes('file:files:delete')&&selectedItems.length!==0"
            class="item"
            effect="dark"
            content="删除"
            placement="top"
          >
            <el-button type="info" icon="el-icon-delete" size="mini" @click="handleDelete" />
          </el-tooltip>
        </el-col>
        <el-col :span="1.5">
          <el-tooltip class="item" effect="dark" content="取消多选" placement="top">
            <el-button type="info" icon="el-icon-circle-close" size="mini" @click="handleCheckAllChange" />
          </el-tooltip>
        </el-col>
      </el-row>
    </el-alert>
    <FileUpload :file-upload="fileUpload" @getList="getList" @hardDiskSize="hardDiskSize" />
    <FileDetails v-if="imageDetails.show" :image-details="imageDetails" />
    <disk-details v-if="diskDetails.show" :disk-details="diskDetails" :hard-disk="hardDisk" />
  </div>
</template>

<script>
import { delFileIds, getFile, renameFile, userHardDiskSize } from '@/api/file/file'
import { downloadFile, getFileAddress, getFileExtension, VerifyIsPictureType } from '@/utils/common'
import FileUpload from '@/views/file/files/components/fileUpload.vue'
import FileDetails from '@/views/file/files/components/fileDetails.vue'
import DiskDetails from '@/views/file/files/components/diskDetails.vue'
import { mapGetters } from 'vuex'

export default {

  name: 'Index',
  components: { DiskDetails, FileDetails, FileUpload },
  data() {
    return {
      // 总条数
      total: 0,
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
      // 文件上传
      fileUpload: {
        path: ''
      },
      // 选中的项
      selectedItems: [],
      checkAll: false,
      isIndeterminate: false,
      // 加载
      loading: false,
      noMore: false,

      alertVisible: false,
      alertClass: '',
      diskDetails: {
        show: false
      },
      hardDisk: {
        total: '',
        free: '',
        used: '',
        usage: 0
      }
    }
  }, computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    this.hardDiskSize()
    this.getList('', true)
  },
  methods: {
    handleSelectionChange(selection) {
      const checkedCount = selection.length
      // 处理行选择事件
      this.selectedItems = selection
      this.checkAll = checkedCount === this.selectedItems.length && checkedCount !== 0
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.fileList.length
      this.showAlert(this.selectedItems.length)
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
      this.showAlert(this.selectedItems.length)
    },
    loadMoreData() {
      console.log(this.noMore)
      const a = Math.ceil(this.total / this.form.pageSize)
      if (this.form.pageNum + 1 >= a) {
        this.noMore = true
      }
      if (this.form.pageNum + 1 <= a) {
        this.form.pageNum = 1 + this.form.pageNum
        this.getList()
      }
    },
    getList(path, newFolder) {
      this.loading = true
      if (newFolder) {
        this.srcList = []
        this.fileList = []
        this.form.path = path
        this.form.pageNum = 1
        this.form.pageSize = 15
      }
      getFile(this.form).then(response => {
        // 过滤出response.data.records中不包含在fileList中的元素
        const newRecords = response.data.records.filter(record => {
          return !this.fileList.some(existingRecord => existingRecord.id === record.id)
        })
        for (const element of response.data.records) {
          const suffix = getFileExtension(element.fileName)
          element.suffix = suffix
          if (VerifyIsPictureType(suffix)) {
            element.filePath = getFileAddress(element.filePath)
            this.srcList.push(element.filePath)
          }
        }
        // 将新的记录添加到fileList中
        this.fileList = [...this.fileList, ...newRecords]
        this.total = response.data.total
        this.getPathList()
        this.fileUpload.path = this.form.path
        this.loading = false
      })
    },
    // 面包屑数据封装
    getPathList() {
      const map = new Map()
      let a = 1
      const pathSegments = this.form.path.split('/').map(segment => {
        if (segment === '') {
          return this.breadcrumb.text
        }
        a++
        // 非管理员用户
        if (!this.roles.includes('admin')) {
          if (a <= 3) {
            return
          }
        }

        return segment
      })
      this.breadcrumb.pathList = pathSegments.filter(segment => segment !== undefined)
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
        this.getList(row.filePath, true)
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
          o.fileName = response.data
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
      const ids = o.id ? [o.id] : this.selectedItems.map(item => item.id)
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
      const ids = o.id ? [o.id] : this.selectedItems.map(item => item.id)
      console.log(ids)
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFileIds(ids).then(response => {
          this.$message.success(response.msg)
          this.fileList = this.fileList.filter(element => !ids.includes(element.id))
          this.hardDiskSize()
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    // 底部状态
    showAlert(size) {
      if (size === 0) {
        this.alertClass = 'alert-slide-down'
        setTimeout(() => {
          this.alertVisible = false
        }, 250) // 根据动画持续时间来调整延迟时间
      } else {
        this.alertVisible = true
        this.alertClass = 'alert-slide-up'
      }
    },
    // 硬盘使用情况
    hardDiskSize() {
      userHardDiskSize().then(response => {
        this.hardDisk = response.data
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

.scroll-container {
  overflow-y: auto; /* 启用垂直滚动条 */
  /* 123 = navbar + tags-view +app-container+el-checkbox= 50 + 34+ 20+ 19 */
  height: calc(100vh - 500px);
}

.alert-button {
  background-color: #909399;
  width: 250px;
  position: fixed; /* 固定定位，使按钮保持在页面右下角 */
  bottom: 80px; /* 距离底部的距离，根据需要调整 */
  left: 50%;
  transform: translateX(-50%);
}

.alert-slide-up {
  animation: slide-up 0.3s ease-out;
}

@keyframes slide-up {
  0% {
    transform: translateY(100%) translateX(-50%);
  }
  100% {
    transform: translateY(0) translateX(-50%);
  }
}

.alert-slide-down {
  animation: slide-down 0.3s ease-out;
}

@keyframes slide-down {
  0% {
    transform: translateY(0) translateX(-50%);
  }
  100% {
    transform: translateY(100%) translateX(-50%);
  }
}
</style>
