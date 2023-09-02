<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <div style="padding: 3px 0;float: right;">
          <el-radio-group v-model="form.path" size="medium" @change="getList">
            <el-radio-button label="/">全部</el-radio-button>
            <el-radio-button label="/image/">图片</el-radio-button>
            <el-radio-button label="/system/">系统</el-radio-button>
          </el-radio-group>
        </div>
        <el-page-header style="padding: 10px 0;" :content="form.path" @back="goBack" />
      </div>
      <div v-if="fileList.length==0">
        <el-empty :image-size="200" />
      </div>
      <el-col
        v-for="o in fileList"
        :key="o"
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
          <h4>创建时间：</h4>
          <span class="title">{{ fileDocument.createTime }}</span>
          <el-divider />
          <h4>更新时间：</h4>
          <span class="title">{{ fileDocument.updateTime }}</span>
          <el-divider />
          <h4>类型：</h4>
          <span class="title">{{ fileDocument.nameSuffix }}</span>
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
        <el-button @click="dialog(null)">取 消</el-button>
        <el-button type="primary" @click="dialog(null)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getFile, markdownImage } from '@/api/file/file'

export default {
  name: 'Index',
  data() {
    return {
      form: {
        path: '/'
      },
      dialogVisible: false,
      fileList: [],
      // 预览图片列表
      srcList: [],
      fileDocument: {}
    }
  },
  created() {
    this.getList('/files/1/markdown')
  },
  methods: {
    getList(path) {
      this.form.path = path
      getFile(this.form).then(response => {
        this.srcList = []
        for (let i = 0; i < response.data.length; i++) {
          if (this.picture(response.data[i].nameSuffix)) {
            response.data[i].path = process.env.VUE_APP_BASE_API_FILE + response.data[i].path
            this.srcList.push(response.data[i].path)
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
      if (acceptedImageTypes.indexOf(name) === -1) {
        return false
      }
      return true
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
