<template>
  <div>
    <svg-icon icon-class="editPassword" style="vertical-align: -0.15em;height: 2.5em;cursor: pointer" @click="handleOpen()"/>
     <div>
      <el-dialog width="500px" title="修改密码" :visible.sync="dialogFormVisible" :modal-append-to-body="false" >
        <el-form ref="passwordForm" label-width="130px" :rules="rule" :model="form">
          <el-form-item prop="oldPassword" label="旧密码" :label-width="formLabelWidth">
            <el-input style="width: 250px" v-model="form.oldPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="newPassword" label="新密码" :label-width="formLabelWidth">
            <el-input style="width: 250px" v-model="form.newPassword" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword" label="确认密码" :label-width="formLabelWidth">
            <el-input style="width: 250px" v-model="form.confirmPassword" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="edit">确 定</el-button>
        </div>
      </el-dialog>
     </div>
  </div>
</template>

<script>
// import {editPassword} from "@/api/user"
export default {
  name: 'DotCodePassword',
  data() {
    return {
      formLabelWidth: '120px',
      dialogFormVisible: false,
      form: {
        oldPassword: null,
        newPassword: null,
        confirmPassword: null
      },
      rule: {
        oldPassword: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入', trigger: 'blur' },
          { pattern: /^[0-9a-zA-Z_]+$/, message: '请輸入字码、数字、下划线格式', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleOpen() {
      this.dialogFormVisible = true
      this.form = {}
    },
    edit() {
      this.$refs['passwordForm'].validate((valid) => {
        if (valid) {
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message.error('俩次密码输入不一致')
            return false
          }
          // editPassword(this.form).then(res =>{
          //   this.$notify({
          //     title: '成功',
          //     message: res.message,
          //     type: 'success'
          //   });
          //   this.dialogFormVisible=false
          // }).catch(err =>{
          //   console.log(err)
          // })
        } else {
          console.log('no')
          return false
        }
      })
    }
  }
}
</script>
<style scoped>

</style>
