<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-button
        v-if="$store.getters.permission.includes('note:article:import')"
        type="success"
        plain
        icon="el-icon-upload"
        size="mini"
        @click="handleImport"
      >上传
      </el-button>
      <div v-if="fileList.length === 0">
        <el-empty :image-size="200" />
      </div>
      <el-row :gutter="5">
        <el-col
          v-for="(o,index) in fileList"
          :key="index"
          :span="4"
          style="height: 250px;"
        >
          <el-button type="text" @click="dialog(o)">
            <el-card shadow="hover" :body-style="{ padding: '0px'}">
              <el-image v-if="o.suffix" fit="cover" :src="o.filePath" class="image" />
              <el-tooltip :content="o.fileName" placement="top">
                <div style="padding: 14px;">
                  <span>{{ stateFormat(o.fileName) }}</span>
                </div>
              </el-tooltip>
            </el-card>
          </el-button>
        </el-col>
      </el-row>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-card>

    <el-dialog
      title="详情"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <div style="display: flex;justify-content: flex-start;">
        <div style="object-fit: contain; width: 40%;">
          <el-image
            :src="fileDocument.filePath"
            style="width: 100%"
          />
        </div>
        <div style="width:45%;margin-left: 5%">
          <h4>名称：</h4>
          <span class="title">{{ fileDocument.fileName }}</span>
          <el-divider />
          <h4>类型：</h4>
          <span class="title">{{ fileDocument.suffix }}</span>
          <el-divider />
          <h4>上传日期：</h4>
          <span class="title">{{ fileDocument.createdTime }}</span>

          <el-divider />
          <h4>文件大小：</h4>
          <span class="title">{{ fileDocument.fileSize }}</span>
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
        <el-button @click="dialog('')">取 消</el-button>
        <el-button type="primary" @click="dialog('')">确 定</el-button>
      </span>
    </el-dialog>
    <image-upload v-if="imageUpload.show" :image-upload="imageUpload" @getList="getList" />
  </div>
</template>

<script>
import { markdownImage, delFileIds } from '@/api/file/file'
import { getFileExtension, getFileAddress, getMarkdownAddress } from '@/utils/common'
import ImageUpload from '@/views/file/image/components/imageUpload.vue'

export default {
  name: 'Index',
  components: { ImageUpload },
  data() {
    return {
      // 总条数
      total: 0,
      dialogVisible: false,
      fileList: [],
      // 预览图片列表
      srcList: [],
      fileDocument: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      imageUpload: {
        show: false
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
    // 内容过长隐藏展示
    stateFormat(name) {
      if (!name) return ''
      if (name.length > 20) { // 超过长度10的内容隐藏
        return name.slice(0, 20) + '...'
      }
      return name
    },
    // 验证是否是图片类型
    picture(name) {
      const acceptedImageTypes = ['jpeg', 'png', 'gif', 'bmp', 'jpg']
      return !(acceptedImageTypes.indexOf(name) === -1)
    },
    // 弹出窗
    dialog(o) {
      this.fileDocument = o
      if (this.dialogVisible) {
        this.dialogVisible = false
      } else {
        this.dialogVisible = true
      }
    },
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
    // 导入
    handleImport() {
      this.imageUpload.show = true
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

.title {
  font-size: 13px;
  color: #999;
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

.text-copy {
  &::after {
    display: inline-block;
    content: '复制'; /* 标签内容*/
    font-size: 14px;
    padding: 0px 3px;
    color: #fff;
    cursor: pointer;
    background-color: rgba(#000, 0.4); /* 鼠标滑过复制标签时出现游标*/
    border-radius: 3px;
    transform: scale(0.5); /* 缩小字体*/
  }
}
</style>
