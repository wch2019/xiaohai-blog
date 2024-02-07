<template>
  <div class="app-container" style="padding: 20px">
    <el-card class="box-card" :body-style="{ padding: 0 }">
      <div slot="header" class="clearfix">
        <el-input class="title" v-model="form.title" maxlength="100" placeholder="输入文章标题" prop="title"></el-input>
        <el-row style="float: right;">
          <el-button type="text" disabled> 自动保存草稿</el-button>
          <el-button size="small" @click="saveArticle()">保 存</el-button>
          <el-popover
            placement="left-start"
            title="发布文章"
            width="500"
            trigger="click"
            v-model="visible"
          >
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
              <el-row>
                <el-form-item prop="cover">
                  <template #label>
                    封面
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
                  </template>

                  <el-upload
                    drag
                    class="image-upload-pic"
                    action="#"
                    :show-file-list="false"
                    :http-request="uploadSectionFile"
                  >
                    <el-image v-if="form.cover" fit="cover" :src="form.cover" class="el-upload-dragger"/>
                    <i v-else class="el-icon-upload"/>
                    <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em>

                    </div>
                  </el-upload>
                </el-form-item>
                <el-form-item label="分类" prop="categoryId">
                  <el-select
                    v-model="form.categoryId"
                    placeholder="分类"
                    clearable
                    size="small"
                    filterable
                    allow-create
                    default-first-option
                    @change="handleChangeCategory"
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
                <el-form-item label="标签" prop="tags">
                  <el-select
                    v-model="form.tags"
                    :multiple-limit="3"
                    placeholder="标签"
                    clearable
                    size="small"
                    multiple
                    filterable
                    allow-create
                    default-first-option
                    @change="handleChangeTags"
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
                <el-form-item label="顶置" prop="isTop">
                  <el-radio v-for="(item,index) in isTop" :key="index" v-model="form.isTop" size="small"
                            :label="index"
                            border>{{ item }}
                  </el-radio>
                </el-form-item>
                <el-form-item label="类型" prop="isOriginal">
                  <el-radio v-for="(item,index) in isOriginal" :key="index" v-model="form.isOriginal" size="small"
                            :label="index" border>{{ item }}
                  </el-radio>
                </el-form-item>
                <el-form-item v-if="form.isOriginal===1" label="转载地址" prop="originalUrl">
                  <el-input v-model="form.originalUrl" placeholder="请输入转载地址"/>
                </el-form-item>
                <el-form-item label="简介" prop="summary">
                  <el-input v-model="form.summary" type="textarea" :autosize="{ minRows: 4, maxRows: 4}" maxlength="250"
                            show-word-limit style="width: 100%"
                            placeholder="请输入简介"/>
                </el-form-item>
              </el-row>
            </el-form>
            <div style="text-align: right; margin: 0">
              <el-button size="small" @click="visible = false">取消</el-button>
              <el-button size="small" type="primary" @click="submitForm()">发 布</el-button>
            </div>
            <el-button size="small" type="primary" style=" margin-left: 10px" slot="reference">发 布</el-button>
          </el-popover>
        </el-row>
      </div>
      <div style="height: calc((100vh - 166px) - 1rem);">
        <mavon-editor
          ref="md"
          v-model="form.text"
          style="height: 100%;width: 100%"
          placeholder="输入文章内容..."
          font-size="18px"
          @save="saveArticle()"
          @imgAdd="imgAdd"
          @imgDel="imgDel"
          @change="textSummary"
        >
          <template slot="left-toolbar-after">
            <span data-v-548e2160="" class="op-icon-divider"></span>
            <button
              type="button"
              @click="triggerFileInput"
              class="op-icon fa el-icon-document-add"
              aria-hidden="true"
              title="导入md文档"
            ></button>
            <input id="upload" type="file" accept=".md" @change="importMd($event)" v-show="false"/>
          </template>
          <template slot="right-toolbar-before">
            <el-button type="text"  disabled>{{ nowTime }}</el-button>
            <span class="op-icon-divider"></span>
            <el-button type="text"  style="color: #0a0a0a" disabled>{{wordCount}}词</el-button>
            <span class="op-icon-divider"></span>

          </template>
        </mavon-editor>
      </div>
    </el-card>
<!--    <el-button type="text"  style="float: right; color: #0a0a0a" disabled> {{wordCount}}词</el-button>-->
  </div>
</template>

<script>
import {
  addArticle,
  addDraftArticle,
  getArticle,
  getBingWallpaper,
  updateArticle,
  updateDraftArticle
} from '@/api/note/article'
import {delFile, uploadImage} from '@/api/file/file'
import {addCategory, optionSelectCategory} from '@/api/note/category'
import {addTags, optionSelectTags} from '@/api/note/tags'
import {findImg, getLastSegment, markdownImageFile, truncateString} from '@/utils'

export default {
  name: 'Index',
  data() {
    return {
      // 标题
      title: '新文章',
      isOriginal: ['原创', '转载'],
      // isPush: ['草稿', '发布'],
      isTop: ['否', '是'],
      // 标签下拉选
      TagsList: [],
      // 分类下拉选
      CategoryList: [],
      // 表单校验
      rules: {
        title: [{required: true, message: '标题不能为空', trigger: 'blur'}],
        summary: [{required: true, message: '请输入简介', trigger: 'blur'}],
        categoryId: [{required: true, message: '分类不能为空', trigger: 'blur'}],
        tags: [{required: true, message: '标签不能为空', trigger: 'blur'}],
        isTop: [{required: true, message: '顶置不能为空', trigger: 'blur'}],
        // isPush: [{required: true, message: '发布不能为空', trigger: 'blur'}],
        isOriginal: [{required: true, message: '类型不能为空', trigger: 'blur'}]
      },
      form: {
        id: '',
        title: '',
        summary: '',
        cover: '',
        text: '',
        categoryId: '',
        tags: [],
        isPush: '',
        isTop: 0,
        isOriginal: 0,
        originalUrl: '',
      },
      // 草稿
      draft: {
        id: '',
        title: '',
        summary: '',
        text: ''
      },
      oldText: '',
      //临时缓存
      cache: {},
      //保存时间
      nowTime: '',
      visible: false,
      //字数
      wordCount:0
    }
  },
  mounted() {
    // 在组件挂载后，启动定时器，每隔一定时间执行保存操作
    this.saveTimer = setInterval(() => {
      if (this.oldText !== this.form.text) {
        this.oldText = this.form.text
        this.saveArticle()
        this.getTime()
      }
    }, 5000); // 每隔5秒执行一次保存操作，根据需要调整时间间隔
  },
  beforeDestroy() {
    // 在组件销毁前清除定时器，防止内存泄漏
    clearInterval(this.saveTimer);
  },
  created() {
    this.getCategory()
    this.getTags()
    this.getArticle()
  },
  methods: {
    getTime() {
      const currentDate = new Date();
      this.nowTime = currentDate.toLocaleString()
    },
    /**
     * 查询分类下拉选
     */
    getCategory() {
      optionSelectCategory().then(response => {
        this.CategoryList = response.data
      })
    },
    /**
     * 获取标签选择列表
     */
    getTags() {
      optionSelectTags().then(response => {
        this.TagsList = response.data
      })
    },
    // 自动添加简介
    textSummary(markdownText) {
      //获取html数据,获取纯文本
      const text=this.$refs.md.d_render.replace(/<[^>]+>/g, '')
      this.form.summary = truncateString(text.replace(/\s*/g, ''), 100)
      this.wordCount = text.length;
    },
    // 监听分类
    handleChangeCategory(value) {
      console.log(value)
      // 判断是否为新添加的选项
      const isNewItem = value && !this.CategoryList.some(option => option.id === value);
      if (isNewItem) {
        const newCategory = {
          name: value,
          sort: '0',
          status: '0'
        }
        addCategory(newCategory).then(response => {
          if (response.code === 200) {
            const newItem = {id: response.data, name: value};
            this.CategoryList.push(newItem);
          }
        })
      }
    },
    // 监听标签
    handleChangeTags(value) {
      // 检查是否有新选项被创建
      const createdItems = value.filter(item => !this.TagsList.some(option => option.id === item));
      // 将新选项保存到后端
      if (createdItems.length > 0) {
        for (const i in createdItems) {
          const newCategory = {
            name: createdItems[i],
            sort: '0',
            status: '0'
          }
          addTags(newCategory).then(response => {
            if (response.code === 200) {
              const newItem = {id: response.data, name: createdItems[i]};
              this.TagsList.push(newItem);
            }
          })
        }
      }
      console.log(this.form.tags)
    },
    getArticle(id) {
      id = this.$route.query.id ? this.$route.query.id : id
      if (id) {
        getArticle(id).then(response => {
          this.form = response.data
          if (this.form.cover) {
            this.form.cover = process.env.VUE_APP_BASE_API_FILE + this.form.cover
          }
          this.title = this.form.title
          this.form.text = this.form.text.replaceAll(markdownImageFile(name), process.env.VUE_APP_BASE_API_FILE + markdownImageFile('..'))
          this.oldText = this.form.text
          let imgData = findImg(this.form.text);
          imgData.forEach(item => {
            this.imgRecurrent(item.text, item.url)
          })
        })
      }
    },
    //将解析到图片名字和地址添加到控制列表(具体为什么要填这些参数，是因为mavon-editor插件中要使用到这些内容)
    imgRecurrent(name, url) {
      this.$refs.md.$refs.toolbar_left.$imgAddByFilename(
        //markdown模板图片地址
        url,
        {
          // 图片控制列表图片链接
          miniurl: url,
          // 图片控制列表名字
          name: name,
          //markdown模板图片名称
          _name: name
        }
      )
    },
    // 随机照片
    randomImg() {
      getBingWallpaper().then(response => {
        this.form.cover = process.env.VUE_APP_BASE_API_FILE + response.data
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
        this.form.cover = process.env.VUE_APP_BASE_API_FILE + response.data
        this.$message.success(response.msg)
      })
    },
    triggerFileInput() {
      const fileInput = document.getElementById('upload');
      fileInput.click();
    },
    //导入md文档
    importMd(e) {
      const file = e.target.files[0];
      if (!file.name.endsWith(".md")) {
        this.$message.warning("文件扩展名必须为.md！")
        return;
      }
      let fileName = file.name.substring(0, file.name.length - 3)
      const reader = new FileReader;
      reader.readAsText(file);
      reader.onload = (res) => {
        this.form.text = res.target.result;
        // this.form.summary = truncateString(res.target.result.trim(),100)
      }
      this.form.title = fileName
      this.$emit('loadTitle', fileName)
      this.$notify({
        title: '导入成功',
        message: '若文章包含本地图片，需要手动导入',
        type: 'success'
      });

      e.target.value = null;
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
      const name = filename[0].replace(process.env.VUE_APP_BASE_API_FILE, "")
      delFile(getLastSegment(name)).then(response => {
        this.$message.success(response.msg)
      })
    },
    /** 发布按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.cover === '') {
            this.$message.error('请上传封面')
            return
          }
          if (this.form.text === '') {
            this.$message.error('请填写文章内容')
            return
          }
          //新增分类获取id
          const matchedItem = this.CategoryList.find(option => option.name === this.form.categoryId);
          if (matchedItem) {
            this.form.categoryId = matchedItem.id;
          }
          //新增标签获取id
          this.form.tags = this.form.tags.map(tagName => {
            const foundTag = this.TagsList.find(tag => tag.name === tagName);
            return foundTag ? foundTag.id : tagName;
          });
          // 临时缓存
          this.cache = JSON.parse(JSON.stringify(this.form));
          this.cache.cover = this.cache.cover.replaceAll(process.env.VUE_APP_BASE_API_FILE, '')
          this.cache.text = this.cache.text.replaceAll(process.env.VUE_APP_BASE_API_FILE, '..')
          if (this.cache.id !== '') {
            updateArticle(this.cache).then(response => {
              this.$message.success(response.msg)
              this.$router.push('/note/article')
            })
          } else {
            addArticle(this.cache).then(response => {
              this.$message.success(response.msg)
              this.$router.push('/note/article')
            })
          }

        }
      })
    },
    /** 保存按钮*/
    saveArticle() {
      this.draft.id = JSON.parse(JSON.stringify(this.form)).id;
      this.draft.title = JSON.parse(JSON.stringify(this.form)).title;
      this.draft.text = JSON.parse(JSON.stringify(this.form)).text;
      this.draft.summary = JSON.parse(JSON.stringify(this.form)).summary;
      if (this.draft.title === '') {
        this.$message.error('请填写文章标题')
        return
      }
      if (this.draft.text === '') {
        this.$message.error('请填写文章内容')
        return
      }
      if (this.draft.summary === '') {
        this.$message.error('请填写简介内容')
        return
      }
      this.draft.text = this.draft.text.replaceAll(process.env.VUE_APP_BASE_API_FILE, '..')
      if (this.draft.id !== '') {
        updateDraftArticle(this.draft).then(response => {
          console.log("保存草稿成功")
        })
      } else {
        addDraftArticle(this.draft).then(response => {
          this.getArticle(response.data)
        })
      }
    }
  }
}
</script>

<style scoped>
.title {
  width: 60%;
  border: none;
  font-weight: 600;
  font-size: 20px;
}

::v-deep .title .el-input__inner {
  border: none;
  font-weight: 600;
  font-size: 20px;
}
</style>
