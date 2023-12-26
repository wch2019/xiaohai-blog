<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
          <span v-if="selectedItems.length===0">共 {{ fileList.length }} 项</span>
          <span v-else>已选 {{ selectedItems.length }} 项</span>
        </el-checkbox>
      </el-col>
    </el-row>
    <div style="margin-top:10px">
      <div v-if="fileList.length === 0">
        <el-empty :image-size="200"/>
      </div>
    </div>
    <el-row
      :gutter="5"
      v-infinite-scroll="loadMore"
      infinite-scroll-disabled="loading"
      class="scroll"
    >
      <el-checkbox-group v-model="selectedItems" @change="handleCheckedCitiesChange">
        <el-col
          v-for="(o,index) in fileList"
          :key="index"
          :span="4"
          style="height: 250px;"
        >
          <el-card shadow="hover" :body-style="{ padding: '0px'}">
            <div style="position: relative" class="hover-element">
              <el-checkbox
                v-if="selectedItems.length!==0"
                :label="o.id"
                class="selected-item-block"
              ><br></el-checkbox>
              <el-checkbox
                v-else
                :label="o.id"
                class="selected-item"
              ><br></el-checkbox>
              <div class="dropdown">
                <el-dropdown trigger="click">
                  <span style="width: 20px;background-color: rgba(255,255,255,0.78);border: 1px solid #DCDFE6;"
                        class="el-dropdown-link">
                  <i class="el-icon-more"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="renameFile(o)">重命名</el-dropdown-item>
                    <el-dropdown-item @click.native="downloadMultipleFiles(o)">下载</el-dropdown-item>
                    <el-dropdown-item @click.native="dialog(o)">查看详情</el-dropdown-item>
                    <el-dropdown-item @click.native="handleDelete(o)" divided>删除</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <el-image v-if="o.suffix" fit="cover" :src="o.filePath" class="image"/>
              <el-button type="text" @click="dialog(o)">
                <el-tooltip :content="o.fileName" placement="top">
                  <div>
                    <span class="title">{{ o.fileName }}</span>
                    <div class="title-time">{{ o.createdTime }}</div>
                  </div>
                </el-tooltip>
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-checkbox-group>
      <el-col>
        <p v-loading="loading"></p>
        <p style=" bottom: 0; width: 100%; text-align: center;" v-if="noMore">没有更多了</p>
      </el-col>

    </el-row>

    <div class="fixed-button">
      <el-button
        v-if="$store.getters.permission.includes('note:article:import')"
        style="width: 56px;height: 56px"
        type="primary"
        icon="el-icon-plus"
        circle
        @click="handleImport"
      />
    </div>
    <el-alert
      v-show="alertVisible"
      class="alert-button"
      center
      :closable="false"
      :class="alertClass"
    >
      <el-row :gutter="8" class="mb8">
        <el-col :span="1.5">
          <el-tooltip class="item" effect="dark" content="下载" placement="top">
            <el-button type="info" icon="el-icon-download" size="mini" @click="downloadMultipleFiles"/>
          </el-tooltip>
        </el-col>
        <el-col :span="1.5">
          <el-tooltip
            v-if="$store.getters.permission.includes('note:article:delete')&&selectedItems.length!==0"
            class="item"
            effect="dark"
            content="删除"
            placement="top">
            <el-button type="info" icon="el-icon-delete" size="mini" @click="handleDelete"/>
          </el-tooltip>
        </el-col>
        <el-col :span="1.5">
          <el-tooltip class="item" effect="dark" content="取消多选" placement="top">
            <el-button type="info" icon="el-icon-circle-close" size="mini" @click="deselectAll"/>
          </el-tooltip>
        </el-col>
      </el-row>
    </el-alert>
    <image-upload v-if="imageUpload.show" :image-upload="imageUpload" @getList="getList"/>
    <image-details v-if="imageDetails.show" :image-details="imageDetails"/>
  </div>
</template>

<script>
import {markdownImage, delFileIds} from '@/api/file/file'
import {getFileExtension, getFileAddress, downloadFile} from '@/utils/common'
import ImageUpload from '@/views/file/image/components/imageUpload.vue'
import ImageDetails from '@/views/file/image/components/imageDetails.vue'

export default {
  name: 'Index',
  components: {ImageUpload, ImageDetails},
  data() {
    return {
      // 总条数
      total: 0,
      //加载
      loading: false,
      noMore: false,
      // 文件列表
      fileList: [],
      // 选中的项
      selectedItems: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 6
      },
      imageUpload: {
        show: false
      },
      imageDetails: {
        show: false,
        fileDocument: {}
      },
      checkAll: false,
      isIndeterminate: false,
      alertVisible: false,
      alertClass: ''
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      markdownImage(this.queryParams).then(response => {
        this.total = response.data.total
        // 过滤出response.data.records中不包含在fileList中的元素
        const newRecords = response.data.records.filter(record => {
          return !this.fileList.some(existingRecord => existingRecord.id === record.id);
        });
        for (const element of response.data.records) {
          const suffix = getFileExtension(element.fileName)
          element.suffix = suffix
          if (this.picture(suffix)) {
            element.filePath = getFileAddress(element.filePath)
          }
        }
        // 将新的记录添加到fileList中
        this.fileList = [...this.fileList, ...newRecords];
        this.loading = false
      })
    },
    // 加载更多
    loadMore() {
      const a = Math.ceil(this.total / this.queryParams.pageSize)
      if (this.queryParams.pageNum + 1 >= a) {
        this.noMore = true
      }
      if (this.queryParams.pageNum + 1 <= a) {
        this.queryParams.pageNum = 1 + this.queryParams.pageNum
        this.getList()
      }
    },
    // 验证是否是图片类型
    picture(name) {
      const acceptedImageTypes = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
      return acceptedImageTypes.indexOf(name) !== -1
    },
    // 弹出窗
    dialog(o) {
      this.imageDetails.fileDocument = o
      this.imageDetails.show = true
    },
    // 导入
    handleImport() {
      this.imageUpload.show = true
    },
    // 全选
    handleCheckAllChange(val) {
      if (val) {
        for (const element of this.fileList) {
          this.selectedItems.push(element.id)
        }
      }
      this.selectedItems = val ? this.selectedItems : []
      this.isIndeterminate = false
      this.showAlert(this.selectedItems.length)
    },
    // 取消全选
    deselectAll() {
      this.selectedItems = []
      this.isIndeterminate = false
      this.checkAll = false
      this.showAlert(this.selectedItems.length)
    },
    // 单个状态
    handleCheckedCitiesChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.selectedItems.length && checkedCount !== 0
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.fileList.length
      this.showAlert(this.selectedItems.length)
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
    // 删除
    handleDelete(o) {
      const ids = [o.id] || this.selectedItems
      this.$confirm('删除可能会导致文章图片无法加载，是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFileIds(ids).then(response => {
          this.$message.success(response.msg)
          this.fileList = this.fileList.filter(element => !ids.includes(element.id));
        })
      }).catch(() => {
        this.$message.info('已取消删除')
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
    // 重命名
    renameFile(o) {
      let suffix = "";
      if (o.suffix !== null) {
        suffix = "." + o.suffix
      }
      this.$prompt('请输入文件名', '重命名', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^(?!^(CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])$)[^<>:"/\\|?*]+$/,
        inputErrorMessage: '文件名格式不正确',
        inputValue: o.fileName.replace(suffix, "")
      }).then(({value}) => {
        this.$message({
          type: 'success',
          message: '你的邮箱是: ' + value + suffix
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }
  }
}
</script>

<style scoped>

.image {
  border-radius: 10px;
  top: 15px;
  width: 90%;
  left: 5%;
  height: 150px;
  display: block;
}

/* 设置标题的字体大小为13像素 */
.title {
  height: 16px;
  margin: 10px;
  font-size: 16px;

  /* 设置标题文本颜色为灰色 (#999) */
  color: #999;

  /* 当标题文本超出容器高度时，将其隐藏 */
  overflow: hidden;

  /* 使用WebKit引擎的Flexbox布局来显示元素 */
  display: -webkit-box;

  /* 设置文本最多显示1行 */
  -webkit-line-clamp: 1;

  /* 设置Flexbox容器的方向为垂直，以便文本可以垂直排列 */
  -webkit-box-orient: vertical;

  /* 当标题文本溢出时，用省略号（ellipsis）来表示被截断的部分 */
  text-overflow: ellipsis;

  /* 指定如何处理元素内的空白字符，这里设置为“normal”表示使用默认的处理方式 */
  white-space: normal;

  /* 允许长单词或URL中的换行，以防止它们破坏布局 */
  word-wrap: break-word;

  /* 在单词内部允许文本换行，以便将长单词拆分到多行 */
  word-break: break-all;
}

.title-time {
  height: 12px;
  margin: 5px;
  font-size: 12px;

  /* 设置标题文本颜色为灰色 (#999) */
  color: #999;

  /* 当标题文本超出容器高度时，将其隐藏 */
  overflow: hidden;

  /* 使用WebKit引擎的Flexbox布局来显示元素 */
  display: -webkit-box;

  /* 设置文本最多显示1行 */
  -webkit-line-clamp: 1;

  /* 设置Flexbox容器的方向为垂直，以便文本可以垂直排列 */
  -webkit-box-orient: vertical;

  /* 当标题文本溢出时，用省略号（ellipsis）来表示被截断的部分 */
  text-overflow: ellipsis;

  /* 指定如何处理元素内的空白字符，这里设置为“normal”表示使用默认的处理方式 */
  white-space: normal;

  /* 允许长单词或URL中的换行，以防止它们破坏布局 */
  word-wrap: break-word;

  /* 在单词内部允许文本换行，以便将长单词拆分到多行 */
  word-break: break-all;
}

.scroll {
  overflow: auto;
  /* 123 = navbar + tags-view +app-container+el-checkbox= 50 + 34+ 20+ 19 */
  height: calc(100vh - 123px);
}

.selected-item-block {
  position: absolute;
  top: 5px;
  left: 5px;
}

.selected-item {
  position: absolute;
  top: 5px;
  left: 5px;
  display: none;
}

.hover-element:hover .selected-item {
  display: block;
}

.dropdown {
  z-index: 1;
  position: absolute;
  top: 5px;
  right: 5px;
  display: none;
}

.hover-element:hover .dropdown {
  display: block;
}


/* 定义右下角按钮容器的样式 */
.fixed-button {
  position: fixed; /* 固定定位，使按钮保持在页面右下角 */
  bottom: 80px; /* 距离底部的距离，根据需要调整 */
  left: 95%;
  transform: translateX(-95%);
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
