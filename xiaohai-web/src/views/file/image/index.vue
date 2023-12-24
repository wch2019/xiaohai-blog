<template>
  <div class="app-container">
    <el-card class="box-card">

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            v-if="$store.getters.permission.includes('note:article:import')&&selectedItems.length===0"
            type="success"
            plain
            icon="el-icon-upload"
            size="mini"
            @click="handleImport"
          >上传
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-if="$store.getters.permission.includes('note:article:add')&&selectedItems.length!==0"
            type="primary"
            plain
            icon="el-icon-check"
            size="mini"
            @click="checkAll"
          >全选
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-if="$store.getters.permission.includes('note:article:delete')&&selectedItems.length!==0"
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            @click="handleDelete"
          >删除
          </el-button>
        </el-col>
      </el-row>
      <div style="margin-top:10px">
        <div v-if="fileList.length === 0">
          <el-empty :image-size="200" />
        </div>
      </div>

      <el-row :gutter="5">
        <el-checkbox-group v-model="selectedItems">
          <el-col
            v-for="(o,index) in fileList"
            :key="index"
            :span="4"
            style="height: 250px;"
          >
            <el-card shadow="hover" :body-style="{ padding: '0px'}">
              <div style="position: relative">
                <el-checkbox
                  :label="o.id"
                  class="selected-item"
                ><br></el-checkbox>
                <el-image v-if="o.suffix" fit="cover" :src="o.filePath" class="image" />
                <el-button type="text" @click="dialog(o)">
                  <el-tooltip :content="o.fileName" placement="top">
                    <div style="padding: 14px;" class="title" @click="dialog(o)">
                      <span>{{ o.fileName }}</span>
                    </div>
                  </el-tooltip>
                </el-button>
              </div>

            </el-card>

          </el-col>
        </el-checkbox-group>

      </el-row>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-card>

    <image-upload v-if="imageUpload.show" :image-upload="imageUpload" @getList="getList" />
    <image-details v-if="imageDetails.show" :image-details="imageDetails" />
  </div>
</template>

<script>
import { markdownImage, delFileIds } from '@/api/file/file'
import { getFileExtension, getFileAddress } from '@/utils/common'
import ImageUpload from '@/views/file/image/components/imageUpload.vue'
import ImageDetails from '@/views/file/image/components/imageDetails.vue'

export default {
  name: 'Index',
  components: { ImageUpload, ImageDetails },
  data() {
    return {
      // 总条数
      total: 0,
      // 文件列表
      fileList: [],
      // 选中的项
      selectedItems: [],
      // 预览图片列表
      srcList: [],
      // fileDocument: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      imageUpload: {
        show: false
      },
      imageDetails: {
        show: false,
        fileDocument: {}
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      markdownImage(this.queryParams).then(response => {
        this.fileList = response.data.records
        this.total = response.data.total
        this.srcList = []
        for (const element of response.data.records) {
          const suffix = getFileExtension(element.fileName)
          element.suffix = suffix
          if (this.picture(suffix)) {
            element.filePath = getFileAddress(element.filePath)
            this.srcList.push(element.filePath)
          }
        }
      })
    },
    // 验证是否是图片类型
    picture(name) {
      const acceptedImageTypes = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
      return !(acceptedImageTypes.indexOf(name) === -1)
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
    handleItemClick(item) {
      console.log(this.selectedItems)
      // 切换选中状态
      if (this.isSelected(item.id)) {
        // 如果已经选中，就取消选中
        this.selectedItems = this.selectedItems.filter((i) => i !== item)
      } else {
        // 否则，将其添加到选中的项中
        this.selectedItems.push(item)
      }
    },
    isSelected(item) {
      // 检查项目是否被选中
      return this.selectedItems.includes(item.id)
    },
    // 全选
    checkAll() {
      if (this.selectedItems.length === this.fileList.length) {
        this.selectedItems = []
      } else {
        this.selectedItems = []
        for (const element of this.fileList) {
          this.selectedItems.push(element.id)
        }
      }
    },
    // 删除
    handleDelete() {
      const ids = this.selectedItems
      this.$confirm('删除可能会导致文章图片无法正常加载，是否确认删除选中图片？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFileIds(ids).then(response => {
          this.$message.success(response.msg)
          this.getList()
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
  height: 150px;
  display: block;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

/* 设置标题的字体大小为13像素 */
.title {
  font-size: 13px;

  /* 设置标题文本颜色为灰色 (#999) */
  color: #999;

  /* 当标题文本超出容器高度时，将其隐藏 */
  overflow: hidden;

  /* 使用WebKit引擎的Flexbox布局来显示元素 */
  display: -webkit-box;

  /* 设置文本最多显示2行 */
  -webkit-line-clamp: 2;

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

.button {
  padding: 0;
  float: right;
}

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

.selected-item {
  position: absolute;
  top: 5px;
  right: 0;
}

/* 去掉el-checkbox的高度 */
::v-deep .el-checkbox.is-bordered {
  //height: auto;
}
</style>
