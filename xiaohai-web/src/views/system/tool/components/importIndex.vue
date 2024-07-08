<template>
  <el-drawer
    title="Markdown 文章导入"
    :visible.sync="importInfo.drawer"
    size="40%"
  >
    <el-container style="height: 100%;">
      <el-main>
        <el-divider>历史文章导入</el-divider>
        <el-row>
          <el-col :span="24">
            <el-empty v-if="files.length===0" description="没有导入数据" />
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
      <el-footer style="">
        <el-button type="primary" class="el-icon-upload2" @click="handleImport">导 入</el-button>
        <el-button class="el-icon-refresh" @click="getList">刷 新</el-button>
      </el-footer>
    </el-container>
    <to-lead-into v-if="leadInfo.show" :lead-info="leadInfo" @getList="getList" />
  </el-drawer>
</template>
<script>
import ToLeadInto from '@/views/system/tool/components/toLeadInto.vue'
import { delFileIds, getImportFiles } from '@/api/file/file'
import { calculateTimeDifference, downloadFile, getFileAddress } from '@/utils/common'

export default {
  name: 'ImportIndex',
  components: { ToLeadInto },
  props: ['importInfo'],
  data() {
    return {
      files: [],
      leadInfo: {
        show: false
      }
    }
  },
  mounted() {
  },
  created() {
    this.getList()
  },
  methods: {
    calculateTimeDifference,
    // 导入
    handleImport() {
      this.leadInfo.show = true
    },
    getList() {
      this.loading = true
      getImportFiles().then(response => {
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
