<template>
    <el-dialog
      title="文章抓取"
      :visible.sync="reptileInfo.show"
      width="30%"
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="60px">
        <el-form-item label="网站" prop="type" >
          <el-radio-group v-model="form.type">
            <el-radio-button label="CSDN" >CSDN</el-radio-button>
            <el-radio-button label="juejin" >掘金</el-radio-button>
            <el-radio-button label="jianshu" >简书</el-radio-button>
            <el-radio-button label="bokeyuan" >博客园</el-radio-button>
            <el-radio-button label="zhihu" >知乎</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="URL" prop="url">
          <el-input v-model="form.url"></el-input>
        </el-form-item>
        <el-form-item label-width="0">
        <div class="tip">
          <h4 style="margin: 0">描述：</h4>
          1.本脚本仅可能存在不准确，请自行检查数据结果<br>
          2.本脚本仅支持抓取文章内容<br>
          3.抓取成功自动保存到草稿，并添加随机封面<br>
          4.如有其他想获取，请联系作者<br>
        </div>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="reptileInfo.show = false">取 消</el-button>
        <el-button type="success" @click="getReptileArticle">获 取</el-button>
      </span>
    </el-dialog>
</template>
<script>

import {reptileArticle} from "@/api/note/article";

export default {
  name: "ReptileArticle",
  props: ['reptileInfo'],
  data() {
    return {
      form: {
        type: 'CSDN',
        url: ''
      },
      rules: {
        type: [
          { required: true, message: '请选择网站类型', trigger: 'change' },
        ],
        url: [
          { required: true, message: '请输入URL', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
  },
  methods: {
    getReptileArticle() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log(this.form)
          reptileArticle(this.form).then(response => {
            this.reptileInfo.show = false
            this.$message.success(response.msg)
            this.$emit('getList')
          })
        }
      });


    }
  }
}

</script>

<style scoped>
.tip {
  padding: 10px;
  margin: 20px auto 15px;
  background-color: #ecf8ff;
  border-radius: 4px;
  border-left: 5px solid #50bfff;
  color: red;
}
</style>
