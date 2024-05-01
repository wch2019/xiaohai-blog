<template>
  <div :id="editorId" :style="{height: height + 'px'}" />
</template>

<script>
import Vditor from 'vditor'
import 'vditor/dist/index.css'
import { getToken } from '@/utils/auth'
import { getLastSegment } from '@/utils'

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
        pin: false
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
      editorId: 'vditor-' + Math.random().toString(36).substr(2, 9)
    }
  },
  watch: {
    value(newVal) {
      if (newVal !== '') {
        this.initVditor()
      }
    }
  },
  mounted() {
    this.initVditor()
  },
  methods: {
    initVditor() {
      new Vditor(this.editorId, {
        placeholder: this.placeholder,
        value: this.value,
        height: this.height,
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
        after: this.afterInit
      })
    }
  }
}
</script>
