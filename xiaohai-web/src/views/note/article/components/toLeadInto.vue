<template>
  <div>
    <el-dialog
      title="Markdown 压缩包导入"
      :visible.sync="leadInfo.show"
      width="30%"
    >
      <el-upload
        class="upload-demo"
        drag
        accept=".zip"
        :limit="1"
        :headers="{
          'authorization':getToken()
        }"
        :action="fileAction"
        :on-success="handleAvatarSuccess"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip">
          <p>压缩包中存在两个文件夹， <em style="color: red">image</em>（存放图片）和 <em style="color: red">note</em>（存放Markdown）</p>
          <el-table size="mini" :data="tableData">
            <el-table-column align="center" label="MarkDown文件最上方以 --- 分隔的区域参数">
              <el-table-column prop="key" label="参数" width="90px" />
              <el-table-column prop="value" label="样例" />
              <el-table-column prop="name" label="描述" />
            </el-table-column>
          </el-table>
          请严格按照模板导入，只能上传zip文件，且不超过10M
          <el-button type="text" @click="deriveFile('markdown导入压缩包模板.zip')">下载模板</el-button>
        </div>
      </el-upload>

    </el-dialog>
  </div>
</template>
<script>
import { deriveFile } from '@/utils'
import { getToken } from '@/utils/auth'

export default {
  name: 'ToleadInfo',
  props: ['leadInfo'],
  data() {
    return {
      tableData: [
        {
          key: 'title',
          value: 'Chrome书签手动同步方法',
          name: '文章标题'
        },
        {
          key: 'date',
          value: '2023-04-10 21:55:09',
          name: '写作时间'
        },
        {
          key: 'tags',
          value: '[浏览器,Chrome]',
          name: '标签列表'
        },
        {
          key: 'categories',
          value: '浏览器',
          name: '分类属性仅为一个'
        },
        {
          key: 'cover',
          value: '../image/1684113802808.jpg',
          name: '封面图，不上传默认获取bing图片'
        }
      ],
      fileAction: process.env.VUE_APP_BASE_API + '/note/article/markdown'
    }
  },
  mounted() {
  },
  methods: {
    getToken,
    deriveFile,
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        this.$message.success(res.msg)
        this.leadInfo.show = false
        this.$emit('getList')
      } else {
        this.$message.error(res.msg)
      }
    }
  }
}
</script>
<style scoped>
::v-deep .el-upload,
::v-deep .el-upload-dragger {
  width: 100%;
}
</style>
