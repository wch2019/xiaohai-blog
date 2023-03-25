<template>
  <div>
    <div class="user-info-head" @click="editCropper()">
      <el-avatar v-if="options.img" shape="circle" :size="120" :src="options.img" />
      <el-avatar v-else shape="circle" :size="120"> {{ $store.getters.name }} </el-avatar>
    </div>
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      @opened="modalOpened"
      @close="closeDialog()"
    >
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <VueCropper
            v-if="visible"
            ref="cropper"
            :img="options.img"
            :info="options.info"
            :output-size="options.outputSize"
            :output-type="options.outputType"
            :auto-crop="options.autoCrop"
            :auto-crop-width="options.autoCropWidth"
            :auto-crop-height="options.autoCropHeight"
            :fixed-box="options.fixedBox"
            @real-time="realTime"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img">
          </div>
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right" />
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)" />
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)" />
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()" />
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()" />
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { VueCropper } from 'vue-cropper'
import { updateUser } from '@/api/system/user'
import { uploadAvatar } from '@/api/file/file'

export default {
  components: { VueCropper },
  props: {
    user: {}
  },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 是否显示cropper
      visible: false,
      // 弹出层标题
      title: '修改头像',
      options: {
        img: this.$store.getters.avatar, // 裁剪图片的地址
        info: true, // 裁剪框的大小信息
        outputSize: 1, // 裁剪生成图片的质量 0.1 - 1
        outputType: 'png', //	裁剪生成图片的格式 jpeg || png || webp
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 200, // 默认生成截图框宽度
        autoCropHeight: 200, // 默认生成截图框高度
        fixedBox: true // 固定截图框大小 不允许改变
      },
      // 图片预览
      previews: {}
    }
  },
  methods: {
    // 编辑头像
    editCropper() {
      this.open = true
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft()
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight()
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1
      this.$refs.cropper.changeScale(num)
    },
    // 上传预处理
    beforeUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 4
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 4MB!')
      }
      if (file.type.indexOf('image/') === -1) {
        this.$message.error('文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。')
      } else {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.options.img = reader.result
        }
      }
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        const formData = new FormData()
        formData.append('avatarFile', data, 'a.png')
        uploadAvatar(formData).then(response => {
          this.user.avatar = response.data
          this.options.img = process.env.VUE_APP_BASE_API_FILE + response.data
          updateUser(this.user).then(response => {
            this.open = false
            this.$store.commit('user/SET_AVATAR', this.options.img)
            this.$message.success(response.msg)
            this.visible = false
          })
        })
      })
    },
    // 实时预览
    realTime(data) {
      this.previews = data
    },
    // 关闭窗口
    closeDialog() {
      this.options.img = this.$store.getters.avatar
      this.visible = false
    }
  }
}
</script>
<style scoped lang="scss">
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}

.user-info-head:hover:after {
  content: '+';
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 120px;
  border-radius: 50%;
}

/* image */
.img-circle {
  text-align: center;
  border-radius: 50%;
}

.img-lg {
  width: 120px;
  height: 120px;
}
.avatar-upload-preview {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 200px;
  height: 200px;
  border-radius: 50%;
  box-shadow: 0 0 4px #ccc;
  overflow: hidden;
}
</style>
