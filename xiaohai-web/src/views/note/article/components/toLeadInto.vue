<template>
  <div>
    <el-dialog
      title="文件上传"
      :visible.sync="leadInfo.show"
      width="30%">
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
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          只能上传zip文件，且不超过10M
          <el-button type="text" @click="deriveFile('markdown导入压缩包模板.zip')">下载模板</el-button>
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>
<script>
import {deriveFile} from "@/utils";
import {getToken} from "@/utils/auth";

export default {
  name: 'ToleadInfo',
  props:['leadInfo'],
  data(){
    return{
      fileAction:process.env.VUE_APP_BASE_API + '/note/article/markdown'
    }
  },
  mounted() {
  },
  methods:{
    getToken,
    deriveFile,
    handleAvatarSuccess(res, file) {
      if (res.code == 200){
        this.$message.success(res.msg)
        this.leadInfo.show = false
        this.$emit('getList')
      }else{
        this.$message.error(res.msg)
      }
    }
  }
}
</script>
<style scoped>
::v-deep .el-upload,
::v-deep .el-upload-dragger{
  width: 100%;
}
</style>
