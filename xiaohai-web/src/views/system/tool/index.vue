<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="5">
        <el-card class="box-card min-height">
          <div slot="header" class="clearfix">
            <span>系统备份</span>
          </div>
          <p class="text">支持 Front Matter 数据导入解析</p>
          <div class="float-right">
            <el-button
              icon="el-icon-upload"
              size="small"
              @click="handleImport"
            >导 入
            </el-button>
          </div></el-card>
      </el-col>
      <el-col :span="5">
        <el-card class="box-card min-height">
          <div slot="header" class="clearfix">
            <span>Markdown 文章导出</span>
          </div>
          <p class="text">支持 Front Matter 数据导入解析</p>
          <div class="float-right">
            <el-button
              icon="el-icon-upload"
              size="small"
              @click="handleImport"
            >导 入
            </el-button>
          </div></el-card>
      </el-col>
      <el-col :span="5">
        <el-card v-if="$store.getters.permission.includes('note:article:import')" class="box-card min-height">
          <div slot="header" class="clearfix">
            <span>Markdown 文章导入</span>
          </div>
          <div>
            <p class="text">支持 Front Matter 数据导入解析</p>
          </div>
          <div class="float-right">
            <el-button
              icon="el-icon-upload"
              size="small"
              @click="handleImport"
            >导 入
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <to-lead-into v-if="leadInfo.show" :lead-info="leadInfo" />
  </div>
</template>

<script>
import ToLeadInto from '@/views/system/tool/components/toLeadInto.vue'

export default {
  name: 'Index',
  components: { ToLeadInto },
  data() {
    return {
      leadInfo: {
        show: false
      }
    }
  },
  created() {

  },
  methods: {
    // 导入
    handleImport() {
      this.leadInfo.show = true
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
  text-align: center;
  font-size: 14px;
}
.float-right {
  float: right;
  bottom: 0
}
</style>
