<template>
  <!-- 参数配置对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="60%" :lock-scroll="false" append-to-body>
    <div class="test-1">
      <el-descriptions :column="3" border>
        <el-descriptions-item label="模块名称">{{ form.title }}</el-descriptions-item>
        <el-descriptions-item label="操作人">{{ form.createdBy }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ form.createdTime }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag v-if="form.status==1" type="danger">异常</el-tag>
          <el-tag v-if="form.status==0" type="success">正常</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="请求方式">{{ form.requestMethod }}</el-descriptions-item>
        <el-descriptions-item label="请求url">{{ form.operUrl }}</el-descriptions-item>
        <el-descriptions-item label="主机地址">{{ form.operIp }}</el-descriptions-item>
        <el-descriptions-item label="系统">{{ form.operOs }}</el-descriptions-item>
        <el-descriptions-item label="浏览器">{{ form.operBrowser }}</el-descriptions-item>
        <el-descriptions-item label="方法名称">{{ form.method }}</el-descriptions-item>
      </el-descriptions>
      <el-descriptions direction="vertical" :column="1" border>
        <el-descriptions-item label="请求参数">
          <json-viewer
            v-if="form.operParam"
            :value="JSON.parse(form.operParam)"
            :expand-depth="1"
            copyable
            boxed
            sort
          >
            <template slot="copy" slot-scope="scope">
              <el-button v-if="!scope.copied" type="text">复制</el-button>
              <el-button v-else type="text" disabled>复制成功</el-button>
            </template>
          </json-viewer>
        </el-descriptions-item>
        <el-descriptions-item label="返回参数">
          <json-viewer
            v-if="form.jsonResult"
            :value="JSON.parse(form.jsonResult)"
            :expand-depth="1"
            copyable
            boxed
            sort
          >
            <template slot="copy" slot-scope="scope">
              <el-button v-if="!scope.copied" type="text">复制</el-button>
              <el-button v-else type="text" disabled>复制成功</el-button>
            </template>
          </json-viewer>
        </el-descriptions-item>
        <el-descriptions-item label="错误消息">{{ form.errorMsg }}</el-descriptions-item>
      </el-descriptions>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'LogDialog',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      form: {}
    }
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.test-1 {
  height: 500px;
  overflow-y: scroll;
}
</style>
