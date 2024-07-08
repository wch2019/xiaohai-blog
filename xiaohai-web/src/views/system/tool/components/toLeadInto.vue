<template>
  <div>
    <el-drawer
      title="Markdown 压缩包导入"
      :visible.sync="leadInfo.show"
      :append-to-body="true"
      size="30%"
    >
      <el-card style="min-height: 100%">
        <el-upload
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
        </el-upload>

        <p>请严格按照样例导入，只能上传zip文件  <el-button type="text" @click="deriveFile('markdown导入压缩包模板.zip')">下载模板</el-button></p>
        <p>压缩包中存在两个文件夹， <em style="color: red">image</em>（存放图片）和 <em style="color: red">note</em>（存放Markdown）</p>
        <el-table :data="tableData">
          <el-table-column align="center" label="MarkDown文件Front Matter区域参数">
            <el-table-column prop="key" label="参数" width="90px" />
            <el-table-column prop="value" label="样例" />
            <el-table-column prop="name" label="描述" />
          </el-table-column>
        </el-table>
        <div class="text">
          <pre><code>{{ frontMatter }}</code></pre>
        </div>
      </el-card>
    </el-drawer>
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'
import { downloadFile } from '@/utils/common'

export default {
  name: 'ToleadInfo',
  props: ['leadInfo'],
  data() {
    return {
      frontMatter: `---
title: Hello World
date: 2024-07-05
tags: ["front matter", "example", "html"]
categories: ChatGPT
cover: ../image/1684113802808.jpg
original: https://www.dotcode.top
---`,
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
          name: '封面图，不填写默认获取bing图片'
        }, {
          key: 'original',
          value: 'http://www.dotcode.top',
          name: '转载信息，可不填写'
        }
      ],
      fileAction: process.env.VUE_APP_BASE_API + '/note/article/import/markdown'
    }
  },
  mounted() {
  },
  methods: {
    getToken,
    // 下载模板文件
    deriveFile(name) {
      downloadFile(name, window.location.origin + '/template/markdown导入压缩包模板.zip')
    },
    handleAvatarSuccess(res, file) {
      if (res.code === 200) {
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
pre {
  background-color: #333;
  color: #f8f8f2;
  padding: 20px;
  border-radius: 5px;
  overflow-x: auto;
}

code {
  display: block;
  white-space: pre;
}
</style>
