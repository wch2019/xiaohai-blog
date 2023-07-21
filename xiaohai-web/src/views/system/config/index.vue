<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="网站信息">
        <span slot="label">
          <i class="el-icon-s-platform" /> 网站信息
        </span>
        <el-form
          ref="form"
          style="margin-left: 20px;"
          label-position="left"
          :model="form"
          label-width="100px"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item label="LOGO" prop="LOGO">
                <el-upload
                  class="avatar-uploader"
                  action="https://jsonplaceholder.typicode.com/posts/"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                >
                  <img v-if="imageUrl" :src="imageUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon" />
                </el-upload>
                <el-input v-model="form.logo" placeholder="LOGO" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :sm="12" :md="10" :lg="8" :xl="6">
              <el-form-item label="网站名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入网站名称" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :sm="12" :md="10" :lg="8" :xl="6">
              <el-form-item label="关键字" prop="keywords">
                <el-input v-model="form.keywords" placeholder="请输入关键字" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :sm="12" :md="10" :lg="8" :xl="6">
              <el-form-item label="网站描述" prop="description">
                <el-input v-model="form.description" placeholder="请输入网站描述" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :sm="12" :md="10" :lg="8" :xl="6">
              <el-form-item label="备案号" prop="recordNum">
                <el-input v-model="form.recordNum" placeholder="请输入备案号" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button v-if="$store.getters.permission.includes('system:config:save')" type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
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
            <el-button v-if="$store.getters.permission.includes('system:config:save')" type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
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
                <el-input v-model="form.emailHost" placeholder="请输入邮箱地址(smtp.qq.com)" />
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
                <el-input v-model="form.emailPort" placeholder="请输入邮箱端口(587)" maxlength="50" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button v-if="$store.getters.permission.includes('system:config:save')" type="primary" @click="submitForm">保存</el-button>
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
              <el-form-item label="本地文件位置">
                <el-input v-model="form.profile" :disabled="true" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="获取图片位置">
                <el-input v-model="form.imagePath" :disabled="true" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="获取头像位置">
                <el-input v-model="form.avatarPath" :disabled="true" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>
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
          <mavon-editor
            ref="md"
            v-model="form.content"
            font-size="18px"
            @save="submitForm"
            @imgAdd="imgAdd"
            @imgDel="imgDel"
          />
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="测试展示">
        <div v-highlight class="markdown-body" v-html="content" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getConfig, addConfig, updateConfig } from '@/api/system/config'
import { uploadImage, delImage } from '@/api/file/file'
import { marked } from 'marked'
import 'github-markdown-css'

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
        content: ''
      }
    }
  },
  computed: {
    content() {
      return marked(this.form.content)
    }
  },
  created() {
    this.getConfig()
  },
  methods: {
    /** 查询配置 */
    getConfig() {
      getConfig().then(response => {
        this.form = response.data
        this.form.content = this.form.content.replaceAll('../image', process.env.VUE_APP_BASE_API_FILE + '/image')
      })
    },
    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      var data = new FormData()
      data.append('file', $file)
      uploadImage(data).then(response => {
        this.$message.success(response.msg)
        /**
         * $vm 指为mavonEditor实例，可以通过如下两种方式获取
         * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
         * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
         */
        this.$refs.md.$img2Url(pos, process.env.VUE_APP_BASE_API_FILE + response.data)
      })
    },
    // 删除图片
    imgDel(filename) {
      const name = filename[0].split('/')
      console.log(name[name.length - 1])
      delImage(name[name.length - 1]).then(response => {
        this.$message.success(response.msg)
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.form.content = this.form.content.replaceAll(process.env.VUE_APP_BASE_API_FILE, '..')
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
      this.form.content = this.form.content.replaceAll('../image', process.env.VUE_APP_BASE_API_FILE + '/image')
    }
  }
}
</script>

<style scoped>

</style>
