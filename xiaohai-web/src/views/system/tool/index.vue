<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="box-card min-height">
          <div slot="header" class="clearfix">
            <span>系统备份</span>
          </div>
          <div class="text">支持备份全站数据和数据导出</div>
          <div class="text">快速迁移系统</div>
          <div class="text">支持下载到本地</div>
          <div class="float-right">
            <el-button
              icon="el-icon-download"
              size="small"
              @click="handleBackup"
            >备 份
            </el-button>
          </div>
        </el-card>

      </el-col>
      <el-col :span="6">

        <el-card class="box-card min-height">
          <div slot="header" class="clearfix">
            <span>Markdown 文章导出</span>
          </div>
          <div class="text">支持文章源数据导出</div>
          <div class="text">支持文章Front Matter数据导出</div>
          <div class="text">支持下载到本地</div>
          <div class="float-right">
            <el-button
              icon="el-icon-download"
              size="small"
              @click="handleExport"
            >导 出
            </el-button>
          </div>
        </el-card>

      </el-col>
      <el-col :span="6">
        <el-card v-if="$store.getters.permission.includes('note:article:import')" class="box-card min-height">
          <div slot="header" class="clearfix">
            <span class="text">Markdown 文章导入</span>
          </div>
          <div class="text">请严格按照要求导入，只能上传zip文件</div>
          <div class="text">支持普通文章数据导入解析</div>
          <div class="text">支持Front Matter文章数据导入解析</div>
          <div class="float-right">
            <el-button
              icon="el-icon-upload2"
              size="small"
              @click="handleImport"
            >导 入
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <import-index v-if="importInfo.drawer" :import-info="importInfo" />
    <export-index v-if="exportInfo.drawer" :export-info="exportInfo" />
    <backup-index v-if="backupInfo.drawer" :backup-info="backupInfo" />
  </div>
</template>

<script>
import ImportIndex from '@/views/system/tool/components/importIndex.vue'
import ExportIndex from '@/views/system/tool/components/exportIndex.vue'
import BackupIndex from '@/views/system/tool/components/backupIndex.vue'

export default {
  name: 'Index',
  components: { ImportIndex, ExportIndex, BackupIndex },
  data() {
    return {
      leadInfo: {
        show: false
      },
      importInfo: {
        drawer: false
      },
      exportInfo: {
        drawer: false
      },
      backupInfo: {
        drawer: false
      },
      form: {
        pageNum: 1,
        pageSize: 100
      }

    }
  },
  created() {
  },
  methods: {
    // 备份
    handleBackup() {
      this.backupInfo.drawer = true
    },
    // 导出
    handleExport() {
      this.exportInfo.drawer = true
    },
    // 导入
    handleImport() {
      this.importInfo.drawer = true
    }
  }
}
</script>

<style scoped>
.min-height {
  min-height: 50px;
  padding-bottom: 10px;
  margin-bottom: 20px
}

.text {
  margin-bottom: 2px;
  font-size: 14px;
}

.float-right {
  float: right;
  bottom: 0
}
</style>
