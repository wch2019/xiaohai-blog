<template>
  <div class="app-container">
    <el-card class="box-card box-card-height">
      <el-tabs>
        <el-tab-pane label="网站信息">
          <span slot="label">
            <i class="el-icon-s-platform" /> 网站信息
          </span>
          <aside>
            网站信息维护<br>
          </aside>
          <el-form
            ref="form"
            style="margin-left: 20px;"
            label-position="left"
            :model="form"
            label-width="100px"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="LOGO" prop="logo">
                  <uploadImg
                    ref="uploadImg"
                    action-url="/api/file/logo"
                    :image-url="form.logo"
                    :img-width="100"
                    :img-height="100"
                    file-name="logoFile"
                    @getImgUrl="getImgUrl"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :sm="12" :md="10" :lg="8" :xl="6">
                <el-form-item label="网站名称" prop="name">
                  <el-input v-model="form.name" maxlength="8" show-word-limit placeholder="请输入网站名称" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :sm="12" :md="10" :lg="8" :xl="6">
                <el-form-item label="标题" prop="title">
                  <el-input v-model="form.title" maxlength="20" show-word-limit placeholder="请输入网站标题" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :sm="12" :md="10" :lg="8" :xl="6">
                <el-form-item label="关键字" prop="keywords">
                  <el-input
                    v-model="form.keywords"
                    maxlength="255"
                    show-word-limit
                    type="textarea"
                    :autosize="{ minRows: 4}"
                    placeholder="请输入关键字"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :sm="12" :md="10" :lg="8" :xl="6">
                <el-form-item label="网站描述" prop="description">
                  <el-input
                    v-model="form.description"
                    maxlength="255"
                    show-word-limit
                    type="textarea"
                    :autosize="{ minRows: 4}"
                    placeholder="请输入网站描述"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :sm="12" :md="10" :lg="8" :xl="6">
                <el-form-item label="ICP备案号" prop="recordNum">
                  <el-input v-model="form.recordNum" maxlength="50" show-word-limit placeholder="请输入ICP备案号" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :sm="12" :md="10" :lg="8" :xl="6">
                <el-form-item label="公安备案号" prop="recordNum">
                  <el-input
                    v-model="form.securityRecordNum"
                    maxlength="50"
                    show-word-limit
                    placeholder="请输入公安备案号"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button
                v-if="$store.getters.permission.includes('system:config:save')"
                type="primary"
                @click="submitForm"
              >保存
              </el-button>
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
                <el-form-item label="消息邮箱通知">
                  <el-radio-group v-model="form.emailMessage" size="medium">
                    <el-radio label="0" border>开启</el-radio>
                    <el-radio label="1" border>关闭</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button
                v-if="$store.getters.permission.includes('system:config:save')"
                type="primary"
                @click="submitForm"
              >保存
              </el-button>
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
              <el-button
                v-if="$store.getters.permission.includes('system:config:save')"
                type="primary"
                @click="submitForm"
              >保存
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="本地文件存储">
          <span slot="label">
            <i class="el-icon-folder" /> 文件存储
          </span>
          <aside>
            <el-card style="margin: 30px 0 30px 0">
              <p class="mb-3">存储空间共 <strong>{{ hardDisk.total }}</strong></p>
              <el-progress
                :percentage="hardDisk.usage"
                :stroke-width="14"
                :show-text="false"
                color="#6f7ad3"
                class="mb-3"
              />
              <div class="row">
                <div>
                  <span class="legend-primary me-2" />
                  <span>已使用</span>
                  <span class="font-size-primary ms-2 me-2">{{ hardDisk.used }}</span>
                </div>
                <div>
                  <span class="legend me-2" />
                  <span>空闲</span>
                  <span class="font-size-primary ms-2">{{ hardDisk.free }}</span>
                </div>
              </div>
            </el-card>
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
                <el-form-item label="用户默认容量" class="input-with-select">
                  <el-input v-model="form.disk" oninput="value=value.replace(/[^\d.]/g,'')">
                    <el-select slot="append" v-model="form.diskProperty" placeholder="请选择">
                      <el-option label="B" value="B" />
                      <el-option label="KB" value="KB" />
                      <el-option label="MB" value="MB" />
                      <el-option label="GB" value="GB" />
                      <el-option label="TB" value="TB" />
                    </el-select>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="本地文件位置">
                  <el-input v-model="form.profile" :disabled="true" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item>
              <el-button
                v-if="$store.getters.permission.includes('system:config:save')"
                type="primary"
                @click="submitForm"
              >保存
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="关于信息">
          <span slot="label">
            <i class="el-icon-edit-outline" /> 关于信息
          </span>
          <Vditor
            :height="calculateHeight()"
            :value="form.content"
            style="margin-top: 30px"
            @fileRead="handleFileRead"
            @save="submitForm"
          />
        </el-tab-pane>
        <el-tab-pane label="测试展示">
          <Preview :md="form.content" />
        </el-tab-pane>
      </el-tabs>
    </el-card>

  </div>
</template>

<script>
import { addConfig, getConfig, updateConfig } from '@/api/system/config'
import { hardDiskSize } from '@/api/file/file'
import uploadImg from '@/components/uploadImg'
import Vditor from '@/components/Vditor'
import Preview from '@/components/Vditor/preview.vue'
import { marked } from 'marked'
import 'github-markdown-css'
import { formatFileSize, markdownImageFile, parseFileSize } from '@/utils'

export default {
  name: 'Index',
  components: { uploadImg, Vditor, Preview },
  data() {
    return {
      form: {
        id: '',
        emailMessage: '1',
        emailHost: '',
        emailUsername: '',
        emailPassword: '',
        emailPort: '',
        content: '',
        diskSize: '',
        disk: 0,
        diskProperty: 'B'
      },
      hardDisk: {
        total: '',
        free: '',
        used: '',
        usage: 0
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
    this.hardDiskSize()
  },
  methods: {
    /** 查询配置 */
    getConfig() {
      getConfig().then(response => {
        this.form = response.data
        this.$refs.uploadImg.getimgUrl(this.form.logo)
        this.form.content = this.form.content.replaceAll(markdownImageFile(name), process.env.VUE_APP_BASE_API_FILE + markdownImageFile('..'))
        const formattedSize = formatFileSize(this.form.diskSize)
        this.$set(this.form, 'disk', formattedSize.value)
        this.$set(this.form, 'diskProperty', formattedSize.unit)
      })
    },
    getImgUrl(imgUrl) {
      this.form.logo = imgUrl
    },
    /** 提交按钮 */
    submitForm(text) {
      if (typeof text === 'string') {
        this.form.content = text
      }
      this.form.content = this.form.content.replaceAll(process.env.VUE_APP_BASE_API_FILE, '..')
      const formattedSize = { value: this.form.disk, unit: this.form.diskProperty }
      this.form.diskSize = parseFileSize(formattedSize)
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== '') {
            updateConfig(this.form).then(response => {
              this.$message.success(response.msg)
              this.form.content = this.form.content.replaceAll(markdownImageFile(name), process.env.VUE_APP_BASE_API_FILE + markdownImageFile('..'))
            })
          } else {
            addConfig(this.form).then(response => {
              this.$message.success(response.msg)
              this.form.content = this.form.content.replaceAll(markdownImageFile(name), process.env.VUE_APP_BASE_API_FILE + markdownImageFile('..'))
            })
          }
        }
      })
    },
    // 硬盘使用情况
    hardDiskSize() {
      hardDiskSize().then(response => {
        this.hardDisk = response.data
      })
    },
    // 计算高度
    calculateHeight() {
      return window.innerHeight - 240
    },
    // md文件读取值
    handleFileRead(fileData) {
      // 这里的 fileData 包含文件名和内容
      const { fileName, content } = fileData
      // 在这里处理文件内容，例如将内容显示在页面上或者做其他操作
      console.log('文件名:', fileName)
      this.form.content = content
    }
  }
}
</script>

<style scoped>
.el-input .el-select {
  width: 90px;
}

.input-with-select ::v-deep .el-input-group__append, .el-input-group__prepend {
  background-color: transparent;
}

.mb-3 {
  margin-bottom: 1rem !important;
}

.me-2 {
  margin-right: 0.5rem !important;
}

.ms-2 {
  margin-left: 0.5rem !important;
}

.legend {
  --tblr-legend-size: 0.75em;
  display: inline-block;
  background: #e6e7e9;
  width: 0.75em;
  height: 0.75em;
  border-radius: 2px;
}

.legend-primary {
  --tblr-legend-size: 0.75em;
  display: inline-block;
  background: #6f7ad3;
  width: 0.75em;
  height: 0.75em;
  border-radius: 2px;
}

.font-size-primary {
  font-size: 15px;
  color: #999;
}

.row {
  display: flex;
  justify-content: flex-start;
}
</style>
