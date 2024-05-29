<template>
  <div>
    <div :id="editorId" :style="{height: height + 'px'}" @input="onEditorInput" />
    <input v-show="false" id="upload" type="file" accept=".md" @change="importMd($event)">
    <button v-show="false" id="save" @click="save()" />
  </div>
</template>

<script>
import Vditor from 'vditor'
import 'vditor/dist/index.css'
import { getToken } from '@/utils/auth'
import { getLastSegment } from '@/utils'
import emoji from '@/components/Vditor/emoji.json'

export default {
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
        url: 'api/file/image',
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
        index: 10000
      })
    },
    afterInit: {
      type: Function,
      default: () => {
      }
    }
  },
  data() {
    return {
      editorId: 'vditor-' + Math.random().toString(36).substr(2, 9),
      emoji: emoji
    }
  },
  watch: {
    value(newVal) {
      if (newVal !== '') {
        this.initVditor()
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
    // this.initVditor()
  },
  methods: {
    onEditorInput(value) {
      // console.log('this.editor.getValue()', this.editor.getValue())
    },
    initVditor() {
      this.editor = new Vditor(this.editorId, {
        toolbar: [
          'emoji',
          'headings',
          'bold',
          'italic',
          'strike',
          'link',
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
          '|',
          'upload',
          'record',
          'table',
          '|',
          'undo',
          'redo',
          {
            hotkey: '⌘S',
            name: 'sponsor',
            tipPosition: 'nw',
            tip: '保存',
            className: 'right',
            icon: '<svg t="1714634549868" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="21360" width="200" height="200"><path d="M1013.3 223.2l-148.7-148.5c-6.8-6.8-16-10.8-26.2-10.8-7.4 0-32.9 0-70.3 0L256 63.9c-83.7 0-145 0-154.3 0l-1 0c-20.4 0-36.7 16.3-36.7 36.7l0 885.6 0 0.8c0 20.4 16.3 36.9 36.7 36.9l117.9 0 0.8 0 648.3 0 1 0 0.8 0L986 1023.9l1.1 0c20.7 0 36.9-16.5 36.9-36.9l0-736.6 0-0.5C1024 240.2 1020.9 231 1013.3 223.2zM704 128l0 256L320 384l0-256L704 128zM320 960l0-320 448 0 0 320L320 960zM960 960l-90.6 0-0.8 0-1 0L832 960 832 623.2c0-26.8-21.1-47.3-47.2-47.3L303.2 575.9c-25.9 0-47.2 20.4-47.2 47.3l0 336.7-36.7 0-0.8 0L128 959.9l0-832 128 0L256 400.7c0 26.8 21.4 47.3 47.2 47.3l417.5 0c26.2 0 47.2-20.4 47.2-47.3l0-272.7 59.3 0L960 260.4 960 960z" p-id="21361"></path><path d="M672 704 416 704c-17.7 0-32 14.3-32 32s14.3 32 32 32l256 0c17.7 0 32-14.3 32-32S689.7 704 672 704z" p-id="21362"></path><path d="M672 832 416 832c-17.7 0-32 14.3-32 32s14.3 32 32 32l256 0c17.7 0 32-14.3 32-32S689.7 832 672 832z" p-id="21363"></path></svg>',
            click() {
              document.getElementById('save').click()
            }
          },
          '|',
          'fullscreen',
          'edit-mode',
          '|',
          {
            name: 'upload-md',
            tipPosition: 'nw',
            tip: '导入md文件',
            className: 'right',
            icon: '<svg t="1714634468021" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="19518" width="200" height="200"><path d="M594.04 959.5H142.31c-41.14 0-74.6-33.46-74.6-74.6V367.47c0-21.18 17.17-38.36 38.36-38.36s38.36 17.17 38.36 38.36v515.32h449.62c21.18 0 38.36 17.17 38.36 38.36-0.01 21.18-17.18 38.35-38.37 38.35zM783.71 569.26c-21.18 0-38.36-17.17-38.36-38.36V141.21H365.4c-21.18 0-38.36-17.17-38.36-38.36S344.22 64.5 365.4 64.5h382.07c41.14 0 74.6 33.46 74.6 74.6v391.8c0 21.19-17.18 38.36-38.36 38.36z m-36.24-428.05h0.12-0.12z" p-id="19519"></path><path d="M360.67 438.06H130.53c-21.18 0-38.36-17.17-38.36-38.36s17.17-38.36 38.36-38.36h230.14c21.18 0 38.36 17.17 38.36 38.36s-17.18 38.36-38.36 38.36zM917.93 824.76h-268.5c-21.18 0-38.36-17.17-38.36-38.36 0-21.18 17.17-38.36 38.36-38.36h268.5c21.18 0 38.36 17.17 38.36 38.36 0 21.19-17.18 38.36-38.36 38.36z" p-id="19520"></path><path d="M783.68 959.01c-21.18 0-38.36-17.17-38.36-38.36v-268.5c0-21.18 17.17-38.36 38.36-38.36 21.18 0 38.36 17.17 38.36 38.36v268.5c0 21.19-17.18 38.36-38.36 38.36zM364.04 437.23c-21.18 0-38.36-17.17-38.36-38.36V110.64c0-21.18 17.17-38.36 38.36-38.36 21.18 0 38.36 17.17 38.36 38.36v288.23c0 21.19-17.17 38.36-38.36 38.36z" p-id="19521"></path><path d="M106.24 398.98c-9.85 0-19.7-3.77-27.19-11.31-14.94-15.02-14.88-39.31 0.14-54.25L338.35 75.66c15.02-14.93 39.3-14.88 54.25 0.14 14.94 15.02 14.88 39.31-0.14 54.25L133.3 387.81c-7.49 7.45-17.27 11.17-27.06 11.17z" p-id="19522"></path></svg>',
            click() {
              document.getElementById('upload').click()
            }
          },
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
        height: this.height,
        hint: {
          emoji: this.emoji
        },
        typewriterMode: true,
        toolbarConfig: this.toolbarConfig,
        counter: {
          enable: true
        },
        cache: {
          enable: false
        },
        upload: this.uploadConfig,
        fullscreen: this.fullscreenConfig,
        after: this.afterInit,
        input(value) {
          this.value = value
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
    save() {
      this.$emit('save', this.editor.getValue())
    }
  }
}
</script>
