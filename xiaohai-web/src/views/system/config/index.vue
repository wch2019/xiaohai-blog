<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="邮箱配置">
        <aside>
          使用邮箱发送通知<br>
        </aside>
        <el-form ref="form" label-width="80px" :model="form" :rules="rules"   >
          <el-col :span="6">
            <el-form-item label="邮箱地址" prop="username">
              <el-input v-model="form.username" placeholder="请输入邮箱地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="邮箱发送者" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱发送者" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="邮箱授权码" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱授权码" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="邮箱端口" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱端口" maxlength="50"/>
            </el-form-item>
          </el-col>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="配置管理">配置管理</el-tab-pane>
      <el-tab-pane label="角色管理">角色管理</el-tab-pane>
      <el-tab-pane label="定时任务补偿">定时任务补偿</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      form: {
        id: '',
        username: '',
        email: '',
        roleIds: [],
        status: '0',
        nickName: '',
        gender: ''
      },
      // 表单校验
      rules: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }]
      }
    }
  }
}
</script>

<style scoped>

</style>
