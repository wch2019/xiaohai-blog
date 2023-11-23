<template>
  <div class="app-container">
    <el-card class="box-card">
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
        <el-card shadow="hover" :body-style="{ padding: '0px'}">
          <el-image v-if="o.nameSuffix" fit="cover" :src="trimmedValue(o.path)" class="image" />
          <el-tooltip :content="o.name" placement="top">
            <div style="padding: 14px;">
              <div class="bottom clearfix">
                <span>{{ stateFormat(o.name) }}</span>
                <el-button type="text" class="button" @click="dialog(o)">查看</el-button>
              </div>
            </div>
          </el-tooltip>
        </el-card>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog
      title="详情"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <div style="display: flex;justify-content: flex-start;">
        <div style="object-fit: contain; width: 40%;">
          <el-image
            :src="trimmedValue(fileDocument.path)"
            style="width: 100%"
          />
        </div>
        <div style="width:45%;margin-left: 5%">
          <h4>名称：</h4>
          <span class="title">{{ fileDocument.name }}</span>
          <el-divider />
          <h4>类型：</h4>
          <span class="title">{{ fileDocument.nameSuffix }}</span>
          <el-divider />
          <h4>上传日期：</h4>
          <span class="title">{{ fileDocument.createTime }}</span>
          <el-divider />
          <h4>图片尺寸：</h4>
          <span class="title">{{ fileDocument.imageSize }}</span>
          <el-divider />
          <h4>文件大小：</h4>
          <span class="title">{{ fileDocument.size }}</span>
          <el-divider />
          <h4>普通链接：</h4>
          <el-link :underline="false" type="primary" class="title" style="color: #409eff;" target="_blank" :href="fileDocument.path">
            {{ fileDocument.path }}
          </el-link>
          <el-divider />
          <h4>Markdown 格式：  <font class="text-copy" @click.stop="copy(fileDocument.path)">复制</font></h4>

          <span class="title">![{{ fileDocument.name }}]({{ fileDocument.path }})</span>
          <el-divider />
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog('')">取 消</el-button>
        <el-button type="primary" @click="dialog('')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { markdownImage } from '@/api/file/file'

export default {
  name: 'Index',
  data() {
    return {
      dialogVisible: false,
      fileList: [],
      // 预览图片列表
      srcList: [],
      fileDocument: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      markdownImage().then(response => {
        this.srcList = []
        for (const element of response.data) {
          if (this.picture(element.nameSuffix)) {
            element.path = process.env.VUE_APP_BASE_API_FILE + element.path
            this.srcList.push(element.path)
          }
        }
        this.fileList = response.data
      })
    },
    trimmedValue(inputValue) {
      return inputValue
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
      return !(acceptedImageTypes.indexOf(name) === -1);
    },
    // 双击行
    handle(row, column, event, cell) {
      // 进入目录
      if (!row.nameSuffix) {
        this.getList(row.path)
      }
      // 查看照片
      if (this.picture(row.nameSuffix)) {
        this.show(row.path)
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
    // 弹出窗
    dialog(o) {
      this.fileDocument = o
      if (this.dialogVisible) {
        this.dialogVisible = false
      } else {
        this.dialogVisible = true
      }
    },
    // 复制操作
    copy(context) {
      navigator.clipboard.writeText(context).then(() => { this.$message.success('复制成功') }).catch(() => { this.$message.error('复制失败') })
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
el-divider{
  margin: 0;
}
h4{
  margin: 14px 0;
}
::v-deep .el-divider--horizontal{
  display: block;
  height: 1px;
  width: 100%;
  margin: 15px 0;
}
.text-copy{
  &::after {
    display: inline-block;
    content: '复制'; /* 标签内容*/
    font-size: 14px;
    padding: 0px 3px;color: #fff;
    cursor: pointer;background-color: rgba(#000,0.4);  /* 鼠标滑过复制标签时出现游标*/
    border-radius: 3px;
    transform: scale(0.5); /* 缩小字体*/
  }
}
</style>
