<template>
  <div class="app-container">
    <!--    <div style="padding: 3px 0;float: right;">-->
    <!--      <el-radio-group v-model="form.path" size="medium" @change="getList">-->
    <!--        <el-radio-button label="">全部</el-radio-button>-->
    <!--        <el-radio-button label="/image">图片</el-radio-button>-->
    <!--        <el-radio-button label="/system">系统</el-radio-button>-->
    <!--      </el-radio-group>-->
    <!--    </div>-->
    <!--    <el-page-header style="padding: 10px 0;" :content="form.path" @back="goBack" />-->
    <el-breadcrumb
      separator-class="el-icon-arrow-right"
      style="padding-bottom: 20px;font-size: 18px;"
    >
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item> <span style=" color: #97a8be;cursor: text;">活动管理</span></el-breadcrumb-item>
      <el-breadcrumb-item>活动列表</el-breadcrumb-item>
      <el-breadcrumb-item>活动详情</el-breadcrumb-item>
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
      @row-dblclick="handle"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="50"
      />
      <el-table-column prop="fileName" label="名称">
        <template slot-scope="scope">
          <i v-if="!scope.row.suffix" class="el-icon-folder-opened" />
          <i v-else-if="picture(scope.row.suffix)" class="el-icon-picture" />
          <i v-else class="el-icon-document" />
          {{ scope.row.fileName }}
          <div class="dropdown">
            <el-dropdown trigger="click">
              <el-button
                style="padding: 5px; border: none;"
                class="el-icon-more"
                size="mini"
              />
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="renameFile(o)">重命名</el-dropdown-item>
                <el-dropdown-item @click.native="downloadMultipleFiles(o)">下载</el-dropdown-item>
                <el-dropdown-item @click.native="dialog(o)">查看详情</el-dropdown-item>
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
  </div>
</template>

<script>
import { delFileIds, getFile } from '@/api/file/file'
import { getFileAddress, getFileExtension, VerifyIsPictureType } from '@/utils/common'
import FileUpload from '@/views/file/files/components/fileUpload.vue'

export default {
  name: 'Index',
  components: { FileUpload },
  data() {
    return {
      form: {
        path: '',
        pageNum: 1,
        pageSize: 10
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
    },
    trimmedValue(inputValue) {
      return process.env.VUE_APP_BASE_API_FILE + inputValue
    },
    // 内容过长隐藏展示
    stateFormat(name) {
      if (!name) return ''
      if (name.length > 18) { // 超过长度10的内容隐藏
        return name.slice(0, 18) + '...'
      }
      return name
    },
    // 验证是否是图片类型 VerifyIsPictureType
    picture(name) {
      return VerifyIsPictureType(name)
    },
    // 双击行
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
    // 返回上一级
    goBack() {
      const key = this.form.path
      console.log(key)
      const pathArray = key.split('/') // 将路径按照斜杠分割成数组
      pathArray.pop() // 删除数组最后一个元素（即最后一个斜杠）
      let path = '/'
      for (let i = 0; i < pathArray.length; i++) {
        if (pathArray[i]) {
          path += pathArray[i] + '/'
        }
      }
      this.getList(path.substring(0, path.length - 1))
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

</style>
