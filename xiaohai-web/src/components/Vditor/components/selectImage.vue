<template>
  <el-drawer
    style="z-index: 2001;"
    :visible.sync="localVisible"
    :with-header="false"
    :wrapper-closable="true"
    size="60%"
    direction="ltr"
  >
    <div class="content">
      <div style="margin-top:10px">
        <div v-if="fileList.length === 0">
          <el-empty :image-size="200" />
        </div>
      </div>
      <el-row
        v-infinite-scroll="loadMore"
        :gutter="5"
        infinite-scroll-disabled="loading"
        infinite-scroll-distance="4"
        class="scroll"
      >
        <el-col
          v-for="(o,index) in fileList"
          :key="index"
          :span="4"
          style="height: 250px;"
        >
          <el-card shadow="hover" :body-style="{ padding: '0px'}">
            <div style="position: relative" class="hover-element">
              <el-image v-if="o.suffix" fit="cover" :src="o.filePath" :preview-src-list="[o.filePath]" class="image" />
              <el-button type="text" @click="copy(o.fileName, o.filePath)">
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
        <el-col>
          <p v-loading="loading" />
          <p v-if="noMore" style=" bottom: 0; width: 100%; text-align: center;">没有更多了</p>
        </el-col>
      </el-row>
    </div>
  </el-drawer>

</template>

<script>
import { markdownImage } from '@/api/file/file'
import { getFileAddress, getFileExtension, getMarkdownAddress, VerifyIsPictureType } from '@/utils/common'

export default {
  name: 'DrawerComponent',
  props: {
    visible: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      localVisible: this.visible,
      // 总条数
      total: 0,
      // 加载
      loading: false,
      noMore: false,
      // 文件列表
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 24
      }
    }
  },
  watch: {
    visible(newVal) {
      this.localVisible = newVal
      if (newVal) {
        this.queryParams.pageNum = 1
        this.fileList = []
        this.getList()
      }
    },
    localVisible(newVal) {
      this.$emit('update:visible', newVal)
    }
  },
  methods: {
    // 获取图片信息
    getList() {
      this.loading = true
      markdownImage(this.queryParams).then(response => {
        this.total = response.data.total
        if (this.total !== 0) {
          // 过滤出response.data.records中不包含在fileList中的元素
          const newRecords = response.data.records.filter(record => {
            return !this.fileList.some(existingRecord => existingRecord.id === record.id)
          })
          for (const element of response.data.records) {
            const suffix = getFileExtension(element.fileName)
            element.suffix = suffix
            if (VerifyIsPictureType(suffix)) {
              element.filePath = getFileAddress(element.filePath)
            }
          }
          // 将新的记录添加到fileList中
          this.fileList = [...this.fileList, ...newRecords]
        }
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
    // 复制markdown操作
    copy(name, path) {
      navigator.clipboard.writeText(getMarkdownAddress(name, path)).then(() => {
        this.localVisible = false
        this.$message.success('复制markdown路径成功')
      }).catch(() => {
        this.$message.error('复制markdown路径失败')
      })
    }
  }
}
</script>

<style scoped>

.content {
  position: absolute;
  bottom: 0;
  padding: 20px 0 20px 0;
}

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
  height: 18px;
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
  height: calc(100vh - 30px);
}
</style>
