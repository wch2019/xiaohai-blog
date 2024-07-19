<template>
  <div>
    <div :id="editorId" :style="{height: height + 'px'}" />
    <input v-show="false" id="upload" type="file" accept=".md" @change="importMd($event)">
    <SelectImage :visible.sync="drawerVisible" />
  </div>
</template>

<script>
import Vditor from 'vditor'
import 'vditor/dist/index.css'
import { getToken } from '@/utils/auth'
import { getLastSegment } from '@/utils'
import emoji from '@/components/Vditor/emoji.json'
import SelectImage from '@/components/Vditor/components/selectImage.vue'

export default {
  components: { SelectImage },
  props: {
    value: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: '请输入内容...'
    },
    height: {
      type: Number,
      default: window.innerHeight
    },
    toolbarConfig: {
      type: Object,
      default: () => ({
        pin: true
      })
    },
    uploadConfig: {
      type: Object,
      default: () => ({
        url: process.env.VUE_APP_BASE_API + '/file/image',
        headers: {
          'authorization': getToken()
        },
        accept: 'image/*',
        max: 2 * 1024 * 1024,
        multiple: false,
        fieldName: 'file',
        filename(name) {
          return name
            .replace(/[^(a-zA-Z0-9\u4e00-\u9fa5\.)]/g, '')
            .replace(/[\?\\/:|<>\*\[\]\(\)\$%\{\}@~]/g, '')
            .replace('/\\s/g', '')
        },
        format(files, responseText) {
          const res = JSON.parse(responseText)
          if (res.code === 200) {
            return JSON.stringify({
              code: 0,
              data: {
                errFiles: '',
                succMap: { [getLastSegment(res.data)]: process.env.VUE_APP_BASE_API_FILE + res.data }
              }
            })
          } else {
            return JSON.stringify({
              code: 1,
              msg: res.msg,
              data: { errFiles: '', succMap: {}}
            })
          }
        }
      })
    },
    fullscreenConfig: {
      type: Object,
      default: () => ({
        index: 2000
      })
    },
    // 是否禁用编辑器
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      editorId: 'vditor-' + Math.random().toString(36).substring(2, 9),
      emoji: emoji,
      drawerVisible: false,
      // 初始化禁用
      initializeDisabled: false
    }
  },
  watch: {
    value(newVal) {
      if (newVal !== '') {
        this.initVditor()
      }
    },
    disabled(newVal) {
      if (this.initializeDisabled) {
        console.log(this.editor)
        if (newVal) {
          this.editor.disabled()
        }
        if (newVal === false && this.value === '') {
          this.editor.enable()
        }
      }
    },
    beforeDestroy() {
      // 销毁编辑器实例
      if (this.editor) {
        this.editor.destroy()
      }
    }
  },
  mounted() {
    this.initVditor()
  },
  methods: {
    initVditor() {
      this.editor = new Vditor(this.editorId, {
        toolbar: [
          'headings',
          'bold',
          'italic',
          'strike',
          'link',
          'emoji',
          '|',
          'list',
          'ordered-list',
          'check',
          'outdent',
          'indent',
          '|',
          'quote',
          'line',
          'code',
          'inline-code',
          'insert-before',
          'insert-after',
          'table',
          '|',
          'undo',
          'redo',
          '|',
          'upload',
          {
            name: 'image',
            tipPosition: 'nw',
            tip: '图片',
            className: 'right',
            icon: '<svg t="1718257683771" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2786" width="200" height="200"><path d="M867.90864 574.538232V257.779543a50.844091 50.844091 0 0 0-50.844092-50.844091h-610.129096a50.844091 50.844091 0 0 0-50.844092 50.844091v499.797418l430.141013-257.779543a152.532274 152.532274 0 0 1 157.108243 0z m0 118.466733l-177.445879-106.264151a50.844091 50.844091 0 0 0-50.844092 0L254.220457 817.064548h562.844091a50.844091 50.844091 0 0 0 50.844092-50.844091z m-660.973188-587.757696h610.129096a152.532274 152.532274 0 0 1 152.532274 152.532274v508.440914a152.532274 152.532274 0 0 1-152.532274 152.532274h-610.129096a152.532274 152.532274 0 0 1-152.532274-152.532274v-508.440914a152.532274 152.532274 0 0 1 152.532274-152.532274z m127.110228 355.90864a76.266137 76.266137 0 1 1 76.266137-76.266137 76.266137 76.266137 0 0 1-76.266137 76.266137z" fill="#666666" p-id="2787"></path></svg>',
            click: () => {
              this.drawerVisible = true
            }
          },
          {
            name: 'upload-md',
            tipPosition: 'nw',
            tip: '导入md文件',
            className: 'right',
            icon: '<svg t="1718257763211" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3792" width="200" height="200"><path d="M601.152 708.288 400 568 344 568l0 112-224 0 0 112 224 0 0 112L400 904l201.152-140.288C624 749.504 624 722.496 601.152 708.288L601.152 708.288zM891.264 331.2 638.656 76.864C630.528 68.608 619.456 64 607.936 64L232 64C196.032 64 176 83.712 176 120L176 512 288 512 288 176l280 0 0 168c0 24.192 32 56 56 56l168 0 0.768 448L624 848 624 960l224 0c35.968 0 56-19.712 56-56L904 362.176C904 350.528 899.392 339.392 891.264 331.2L891.264 331.2z" p-id="3793"></path></svg>',
            click() {
              document.getElementById('upload').click()
            }
          },
          {
            hotkey: '⌘S',
            name: 'sponsor',
            tipPosition: 'nw',
            tip: '保存',
            className: 'right',
            icon: '<svg t="1714634549868" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="21360" width="200" height="200"><path d="M1013.3 223.2l-148.7-148.5c-6.8-6.8-16-10.8-26.2-10.8-7.4 0-32.9 0-70.3 0L256 63.9c-83.7 0-145 0-154.3 0l-1 0c-20.4 0-36.7 16.3-36.7 36.7l0 885.6 0 0.8c0 20.4 16.3 36.9 36.7 36.9l117.9 0 0.8 0 648.3 0 1 0 0.8 0L986 1023.9l1.1 0c20.7 0 36.9-16.5 36.9-36.9l0-736.6 0-0.5C1024 240.2 1020.9 231 1013.3 223.2zM704 128l0 256L320 384l0-256L704 128zM320 960l0-320 448 0 0 320L320 960zM960 960l-90.6 0-0.8 0-1 0L832 960 832 623.2c0-26.8-21.1-47.3-47.2-47.3L303.2 575.9c-25.9 0-47.2 20.4-47.2 47.3l0 336.7-36.7 0-0.8 0L128 959.9l0-832 128 0L256 400.7c0 26.8 21.4 47.3 47.2 47.3l417.5 0c26.2 0 47.2-20.4 47.2-47.3l0-272.7 59.3 0L960 260.4 960 960z" p-id="21361"></path><path d="M672 704 416 704c-17.7 0-32 14.3-32 32s14.3 32 32 32l256 0c17.7 0 32-14.3 32-32S689.7 704 672 704z" p-id="21362"></path><path d="M672 832 416 832c-17.7 0-32 14.3-32 32s14.3 32 32 32l256 0c17.7 0 32-14.3 32-32S689.7 832 672 832z" p-id="21363"></path></svg>',
            click: () => {
              this.$emit('save', this.editor.getValue())
            }
          },
          '|',
          'fullscreen',
          'edit-mode',
          {
            name: 'more',
            toolbar: [
              'both',
              'code-theme',
              'content-theme',
              'export',
              'outline',
              'preview',
              'devtools',
              'info',
              'help'
            ]
          }
        ],
        placeholder: this.placeholder,
        value: this.value,
        icon: 'material',
        height: this.height,
        hint: {
          emoji: this.emoji
        },
        typewriterMode: true,
        toolbarConfig: this.toolbarConfig,
        counter: {
          enable: true,
          type: 'text'
        },
        cache: {
          enable: false
        },
        upload: this.uploadConfig,
        fullscreen: this.fullscreenConfig,
        after: () => {
          this.addReferrerPolicyToImages()
          if (this.disabled) {
            this.editor.disabled()
            this.initializeDisabled = true
          }
        },
        resize: {
          // 是否支持大小拖拽
          enable: false
        },
        input: () => {
          // 返回Markdown数据
          this.$emit('markdown', this.editor.getValue())
          // 返回html
          this.$emit('html', this.editor.getHTML())
        }
      })
    },
    // 导入md文档
    importMd(e) {
      const file = e.target.files[0]
      if (!file.name.endsWith('.md')) {
        this.$message.warning('文件扩展名必须为.md！')
        return
      }
      const fileName = file.name.substring(0, file.name.length - 3)
      const reader = new FileReader()
      reader.readAsText(file)
      reader.onload = (res) => {
        this.$emit('fileRead', { fileName, content: res.target.result })
      }
      this.$notify({
        title: '导入成功',
        message: '若文章包含本地图片，需要手动导入',
        type: 'success'
      })
      e.target.value = null
    },
    // 防止无法展示图片
    addReferrerPolicyToImages() {
      const images = document.querySelectorAll('img')
      images.forEach(image => {
        if (!image.hasAttribute('referrerPolicy')) {
          image.setAttribute('referrerPolicy', 'no-referrer')
        }
      })
    }
  }
}
</script>

<style>

 .vditor-toolbar {
  background-color: var(--toolbar-background-color);
  box-shadow: 2px 2px 5px 1px var(--border-color);
  padding: 0 5px;
  line-height: 1;
  border-bottom: 0;
}
.vditor-img{
  z-index: 10000;
}
</style>
