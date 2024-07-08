<template>
  <el-drawer
    title="Markdown 文章导出"
    :visible.sync="exportInfo.drawer"
    size="40%"
  >
    <el-container style="height: 100%;">
      <el-main>
        <el-divider>历史文章导出</el-divider>
        <el-row v-loading="loading">
          <el-col :span="24">
            <el-empty v-if="files.length===0" description="没有导出数据" />
            <el-card v-for="file in files" v-else :key="file.id" class="box-card file-card">
              <div class="file-info">
                <div>
                  <el-button class="file-name" type="text" @click.native="downloadMultipleFiles(file)">
                    {{ file.fileName }}
                  </el-button>
                  <div class="file-date">{{ calculateTimeDifference(file.createdTime) }} / {{ file.fileSize }}</div>
                </div>
                <el-button
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="deleteFile(file.id)"
                >删除
                </el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>

      </el-main>
      <el-footer>
        <el-popover v-model="visible" placement="top">
          <p>是否同时为 Markdown 文档生成 Front Matter？</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="handleExport(0)">否</el-button>
            <el-button type="primary" size="mini" @click="handleExport(1)">是</el-button>
          </div>
          <el-button slot="reference" type="primary" class="el-icon-upload2">导 出</el-button>
        </el-popover>

        <el-button class="el-icon-refresh" @click="getList">刷 新</el-button>
      </el-footer>
    </el-container>
  </el-drawer>
</template>
<script>
import { delFileIds, getExportFiles } from '@/api/file/file'
import { calculateTimeDifference, downloadFile, getFileAddress } from '@/utils/common'
import { exportMarkdown } from '@/api/note/article'

export default {
  name: 'ExportIndex',
  props: ['exportInfo'],
  data() {
    return {
      files: [],
      visible: false,
      loading: false
    }
  },
  mounted() {
  },
  created() {
    this.getList()
  },
  methods: {
    calculateTimeDifference,
    // 导出
    handleExport(status) {
      this.visible = false
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      exportMarkdown(status).then(response => {
        loading.close()
        this.$message.success(response.msg)
        this.getList()
      }).catch(error => {
        console.log(error)
        loading.close()
        this.$message.error('导出失败')
        console.error(error)
      })
    },
    getList() {
      this.loading = true
      getExportFiles().then(response => {
        for (const element of response.data) {
          if (element.fileType === 0) {
            element.filePath = getFileAddress(element.filePath)
          }
        }
        this.files = response.data
        this.loading = false
      })
    },
    // 下载文件
    downloadMultipleFiles(file) {
      if (file.fileType === 0) {
        downloadFile(file.fileName, window.location.origin + file.filePath)
      }
    },
    // 删除
    deleteFile(id) {
      this.$confirm('是否确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFileIds(id).then(response => {
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
.el-footer {
  display: flex;
  justify-content: flex-end;
  padding: 10px;
}

.file-card {
  margin-bottom: 20px;
}

.file-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.file-name {
  font-size: 16px;

}

.file-date {
  font-size: 14px;
  color: #888;
}
</style>
