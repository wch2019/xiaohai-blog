<template>
  <div>
    <el-dialog
      title="文章抓取"
      :visible.sync="reptileInfo.show"
      width="30%"
    >
      <el-radio-group v-model="form.type">
        <el-radio :label="1" border>CSDN</el-radio>
        <el-radio :label="2" border>juejin</el-radio>
        <el-radio :label="3" border>jianshu</el-radio>
      </el-radio-group>


    </el-dialog>
  </div>
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
        originalUrl: ''
      },
    }
  },
  mounted() {
  },
  methods: {
    getReptileArticle(res, file) {
      reptileArticle(this.queryParams).then(response => {
        this.reptileInfo.show = false
        this.$message.success(response.msg)
        this.$emit('getList')
      })
    }
  }
}

</script>

<style scoped>

</style>
