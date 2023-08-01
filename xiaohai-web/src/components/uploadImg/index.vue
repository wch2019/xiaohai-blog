<template>
  <div>
    <el-upload
      class="avatar-uploader"
      :action="actionUrl"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :name="fileName"
      :headers="{
        'Authorization':getToken()
      }"
    >
      <img v-if="imageUrlEcho" :src="imageUrlEcho" class="avatar" :style="{'width':imgWidth+'px','height':imgHeight+'px'}">
      <i v-else class="el-icon-plus avatar-uploader-icon" :style="{'width':imgWidth+'px','height':imgHeight+'px','lineHeight':imgHeight+'px'}" />
    </el-upload>
  </div>
</template>
<script>
import { getToken } from '@/utils/auth'

export default {
  name: 'Index',
  props: {
    actionUrl: {
      type: String,
      default: '',
      required: true
    },
    imgWidth: {
      type: Number,
      default: 178
    },
    imgHeight: {
      type: Number,
      default: 178
    },
    fileName: {
      type: String,
      default: 'file',
      required: true
    },
    imageUrl: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      imageUrlEcho: ''
    }
  },
  mounted() {
    const random = new Date().getTime()
    setTimeout(() => {
      this.imageUrlEcho = this.imageUrl ? process.env.VUE_APP_BASE_API_FILE + this.imageUrl + `?random=` + random : ''
    }, 500)
  },
  methods: {
    getToken,
    handleAvatarSuccess(res, file) {
      const random = new Date().getTime()
      this.imageUrlEcho = process.env.VUE_APP_BASE_API_FILE + res.data + `?random=` + random
      this.$emit('getImgUrl', res.data)
    },
    beforeAvatarUpload(file) {

    }
  }
}
</script>
<style lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  //width: 178px;
  //height: 178px;
  //line-height: 178px;
  text-align: center;
}
.avatar {
  display: block;
}
</style>
