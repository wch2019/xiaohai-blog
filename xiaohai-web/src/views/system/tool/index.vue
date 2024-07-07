<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="8" class="box-card-height">
        <el-row>
          <el-card class="box-card min-height">
            <div slot="header" class="clearfix">
              <span>系统备份</span>
            </div>
            <p class="text">支持备份全站数据和数据导出，支持下载到本地.</p>
            <div class="float-right">
              <el-button
                icon="el-icon-upload"
                size="small"
                @click="handleImport"
              >备 份
              </el-button>
            </div>
          </el-card>

        </el-row>
        <el-row>

          <el-card class="box-card min-height">
            <div slot="header" class="clearfix">
              <span>Markdown 文章导出</span>
            </div>
            <p class="text">支持文章数据导出</p>
            <div class="float-right">
              <el-button
                icon="el-icon-upload"
                size="small"
                @click="handleExport"
              >导 出
              </el-button>
            </div>
          </el-card>

        </el-row>
        <el-row>
          <el-card v-if="$store.getters.permission.includes('note:article:import')" class="box-card min-height">
            <div slot="header" class="clearfix">
              <span class="text">Markdown 文章导入</span>
            </div>
            <h2 class="text">1.请严格按照要求导入，只能上传zip文件</h2>
            <h2 class="text">2.支持普通文章数据导入解析</h2>
            <h2 class="text">3.支持Front Matter文章数据导入解析</h2>
            <div class="float-right">
              <el-button
                icon="el-icon-upload"
                size="small"
                @click="handleImport"
              >导 入
              </el-button>
            </div>
          </el-card>
        </el-row>
      </el-col>
      <el-col :span="16" />

    </el-row>
    <import-index v-if="importInfo.drawer" :import-info="importInfo" />
    <export-index v-if="exportInfo.drawer" :export-info="exportInfo" />
  </div>
</template>

<script>
import ImportIndex from '@/views/system/tool/components/importIndex.vue'
import ExportIndex from '@/views/system/tool/components/exportIndex.vue'

export default {
  name: 'Index',
  components: { ImportIndex, ExportIndex },
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
      form: {
        pageNum: 1,
        pageSize: 100
      }

    }
  },
  created() {
  },
  methods: {
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
  font-size: 14px;
}

.float-right {
  float: right;
  bottom: 0
}
</style>
