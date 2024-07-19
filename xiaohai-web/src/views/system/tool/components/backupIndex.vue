<template>
  <el-drawer
    title="系统备份"
    :visible.sync="backupInfo.drawer"
    size="40%"
  >
    <el-container style="height: 100%;">
      <el-main>
        <el-alert
          title="备份时保证硬盘容量充足,历史系统备份文件不会备份"
          type="warning"
          show-icon
        />
        <el-divider>历史系统备份</el-divider>
        <el-row v-loading="loading">
          <el-col :span="24">
            <el-empty v-if="files.length===0" description="没有备份数据" />
            <el-card v-for="file in files" v-else :key="file.id" class="box-card file-card">
              <div class="file-info">
                <div>
                  <el-button class="file-name" type="text" @click.native="downloadMultipleFiles(file)">
                    {{ file.fileName }}
                  </el-button>
                  <div class="file-date">{{ calculateTimeDifference(file.createdTime) }} / {{ file.fileSize }}</div>
                </div>
                <div>
                  <el-button
                    size="mini"
                    type="primary"
                    icon="el-icon-refresh-right"
                    @click="restore(file.fileName)"
                  >还原
                  </el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    @click="deleteFile(file.id)"
                  >删除
                  </el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>

      </el-main>
      <el-footer>
        <el-button type="primary" class="el-icon-download" @click="handleBackup">备 份</el-button>
        <el-button class="el-icon-refresh" @click="getList">刷 新</el-button>
      </el-footer>
    </el-container>
  </el-drawer>
</template>
<script>
import { delFileIds, getBackupFiles, getExportFiles } from '@/api/file/file'
import { calculateTimeDifference, downloadFile, getFileAddress } from '@/utils/common'
import { exportMarkdown } from '@/api/note/article'
import { addBackup, restoreFileName } from '@/api/system/buckup'

export default {
  name: 'ExportIndex',
  props: ['backupInfo'],
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
    // 备份
    handleBackup() {
      this.visible = false
      const loading = this.$loading({
        lock: true,
        text: '正在备份中，请耐心等待',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      addBackup().then(response => {
        loading.close()
        this.$message.success(response.msg)
        this.getList()
      }).catch(error => {
        console.log(error)
        loading.close()
        this.$message.error('备份失败')
        console.error(error)
      })
    },
    getList() {
      this.loading = true
      getBackupFiles().then(response => {
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
    },
    // 还原
    restore(fileName) {
      this.$confirm('是否确认还原', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading({
          lock: true,
          text: '正在还原中，请耐心等待',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        restoreFileName(fileName).then(response => {
          loading.close()
          this.$message.success(response.msg)
          this.getList()
        }).catch(error => {
          console.log(error)
          loading.close()
          this.$message.error('还原失败')
          console.error(error)
        })
      }).catch(() => {
        this.$message.info('已取消还原')
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
