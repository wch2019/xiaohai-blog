<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="系统开关">
        <span slot="label">
          <i class="el-icon-open" /> 系统开关
        </span>
        <aside>
          选择可用配置<br>
        </aside>
        <el-form
          ref="form"
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="100px"
        >
          <el-row>
            <el-col :span="6">
              <el-form-item label="系统通知">
                <el-radio-group v-model="form.resource" size="medium">
                  <el-radio border label="开启" />
                  <el-radio border label="关闭" />
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-form></el-tab-pane>
      <el-tab-pane label="邮箱配置">
        <span slot="label">
          <i class="el-icon-message" /> 邮箱配置
        </span>
        <aside>
          使用邮箱发送通知<br>
        </aside>
        <el-form
          ref="form"
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="100px"
        >
          <el-row>
            <el-col :span="6">
              <el-form-item label="邮箱地址" prop="username">
                <el-input v-model="form.emailHost" placeholder="请输入邮箱地址" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">

              <el-form-item label="邮箱发送者" prop="email">
                <el-input v-model="form.emailUsername" placeholder="请输入邮箱发送者" maxlength="50" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="邮箱授权码" prop="email">
                <el-input v-model="form.emailPassword" placeholder="请输入邮箱授权码" maxlength="50" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="邮箱端口" prop="email">
                <el-input v-model="form.emailPort" placeholder="请输入邮箱端口" maxlength="50" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="本地文件存储">
        <span slot="label">
          <i class="el-icon-folder" /> 本地文件存储
        </span>
        <aside>
          使用IO流将文件存储本地磁盘中<br>
        </aside>
        <el-form
          ref="form"
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="100px"
        >
          <el-row>
            <el-col :span="6">
              <el-form-item label="本地文件位置" prop="username">
                <el-input v-model="form.filePath" placeholder="请输入本地文件位置" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-form></el-tab-pane>
      <el-tab-pane label="系统通知">
        <span slot="label">
          <i class="el-icon-edit-outline" /> 系统通知
        </span>
        <aside>
          登录系统后展示弹窗<br>
        </aside>
        <el-form
          ref="form"
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="100px"
        >
          <mavon-editor v-model="form.content" @save="submitForm"/>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getConfig, addConfig, updateConfig } from '@/api/system/config'

export default {
  name: 'Index',
  data() {
    return {
      form: {
        id: '',
        emailHost: '',
        emailUsername: '',
        emailPassword: '',
        emailPort: '',
        filePath: '',
        content: ''
      }
    }
  }, created() {
    this.getConfig()
  },

  methods: {
    /** 查询配置 */
    getConfig() {
      getConfig().then(response => {
        this.form = response.data
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== '') {
            updateConfig(this.form).then(response => {
              this.$message.success(response.msg)
            })
          } else {
            addConfig(this.form).then(response => {
              this.$message.success(response.msg)
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
