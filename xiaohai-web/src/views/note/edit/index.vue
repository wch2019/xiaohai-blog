<template>
  <div class="app-container">
  <!-- 添加或修改参数配置对话框 -->
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-row>
      <el-col :span="16">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标签名称"/>
        </el-form-item>
        <el-row>
          <el-col :span="8">
            <el-form-item label="分类" prop="categoryId">
              <el-select
                v-model="form.categoryId"
                placeholder="分类"
                clearable
                size="small"
                @clear="form.categoryId = null"
              >
                <el-option
                  v-for="tags in CategoryList"
                  :key="tags.id"
                  :label="tags.name"
                  :value="tags.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="标签" prop="tags">
              <el-select
                v-model="form.tags"
                placeholder="标签"
                clearable
                size="small"
                multiple
                @clear="form.tags = []"
              >
                <el-option
                  v-for="tag in TagsList"
                  :key="tag.id"
                  :label="tag.name"
                  :value="tag.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="顶置" prop="isTop">
              <el-radio-group v-model="form.isTop" size="small">
                <el-radio label="0" border>否</el-radio>
                <el-radio label="1" border>是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="发布" prop="isPush">
              <el-radio-group v-model="form.isPush" size="small">
                <el-radio label="0" border>草稿</el-radio>
                <el-radio label="1" border>发布</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类型" prop="isOriginal">
              <el-radio-group v-model="form.isOriginal" size="small">
                <el-radio label="0" border>转载</el-radio>
                <el-radio label="1" border>原创</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item v-if="form.isOriginal==0" label="转载地址" prop="originalUrl">
              <el-input v-model="form.originalUrl" placeholder="请输入转载地址"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="8">
        <el-form-item label="封面" prop="imageUrl">
          <el-popover
            placement="bottom"
            width="180"
            trigger="hover"
          >
            <div style="text-align: right; margin: 0">
              随机获取一张图片
              <el-button type="text" size="mini" @click="randomImg()">确定</el-button>
            </div>
            <i slot="reference" class="el-icon-question"/>
          </el-popover>
          <el-upload
            drag
            class="image-upload-pic"
            action="#"
            :show-file-list="false"
            :http-request="uploadSectionFile"
          >
            <img v-if="imageUrl" :src="imageUrl" class="el-upload-dragger">
            <i v-else class="el-icon-upload"/>
            <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-form-item>
      </el-col>
    </el-row>
    <mavon-editor
      style="height: 100%;width: 100%"
      ref="md"
      v-model="form.text"
      placeholder="输入文章内容..."
      font-size="18px"
      @save="submitForm"
      @imgAdd="imgAdd"
      @imgDel="imgDel"
    />



  </el-form>

  <div slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitForm">确 定</el-button>
    <el-button @click="cancel">取 消</el-button>
  </div>

  </div>
</template>

<script>
import { addArticle, updateArticle, getBingWallpaper } from '@/api/note/article'
import { delImage, uploadImage } from '@/api/file/file'

export default {
  name: 'Index',
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: '',
      // 标签下拉选
      TagsList: [],
      // 分类下拉选
      CategoryList: [],
      // 表单校验
      rules: {
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        categoryId: [{ required: true, message: '分类不能为空', trigger: 'blur' }],
        tags: [{ required: true, message: '标签不能为空', trigger: 'blur' }],
        isTop: [{ required: true, message: '顶置不能为空', trigger: 'blur' }],
        isPush: [{ required: true, message: '发布不能为空', trigger: 'blur' }],
        isOriginal: [{ required: true, message: '类型不能为空', trigger: 'blur' }]
      },
      form: {
        id: '',
        title: '',
        cover: '',
        text: '',
        categoryId: '',
        tags: [],
        isPush: '0',
        isTop: '0',
        isOriginal: '1',
        originalUrl: ''
      },
      imageUrl: ''
    }
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        id: '',
        title: '',
        cover: '',
        text: '',
        categoryId: '',
        tags: [],
        isPush: '0',
        isTop: '0',
        isOriginal: '1',
        originalUrl: ''
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 随机照片
    randomImg() {
      getBingWallpaper().then(response => {
        this.imageUrl = process.env.VUE_APP_BASE_API_FILE + response.data
        this.form.cover = response.data
        this.$message.success(response.msg)
      })
    },
    // 覆盖默认的上传行为
    uploadSectionFile(params) {
      const file = params.file
      const fileType = file.type
      const isImage = fileType.indexOf('image') !== -1
      const isLt2M = file.size / 1024 / 1024 < 2
      // 这里常规检验，看项目需求而定
      if (!isImage) {
        this.$message.error('只能上传图片格式png、jpg、gif!')
        return
      }
      if (!isLt2M) {
        this.$message.error('只能上传图片大小小于2M')
        return
      }
      // 根据后台需求数据格式
      const form = new FormData()
      // 文件对象
      form.append('file', file)
      uploadImage(form).then(response => {
        this.imageUrl = process.env.VUE_APP_BASE_API_FILE + response.data
        this.form.cover = response.data
        this.$message.success(response.msg)
      })
    },
    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      // 第一步.将图片上传到服务器.
      const data = new FormData()
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
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id !== '') {
            updateArticle(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
            })
          } else {
            addArticle(this.form).then(response => {
              this.$message.success(response.msg)
              this.open = false
              // 回调父方法
              this.$emit('closeDialog')
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
