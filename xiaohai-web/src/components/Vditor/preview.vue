<template>
  <div :id="previewId" />
</template>

<script>
import Vditor from 'vditor'
import 'vditor/dist/index.css'

export default {
  props: {
    md: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      previewId: 'vditor-preview-' + Math.random().toString(36).substr(2, 9)
    }
  },
  watch: {
    md(newVal) {
      if (newVal !== '') {
        this.initPreview()
      }
    }
  },
  methods: {
    initPreview() {
      const previewElement = document.getElementById(this.previewId)
      Vditor.preview(previewElement, this.md, {
        mode: 'light',
        anchor: 2,
        hljs: { style: 'github', lineNumber: true },
        transform(html) {
          return html.replaceAll('<img', '<img referrerPolicy="no-referrer"')
        },
        after: () => {
          previewElement.addEventListener('click', (event) => {
            const target = event.target
            if (target.tagName === 'IMG') {
              Vditor.previewImage(target)
            }
          })
        }
      })
    }
  }
}
</script>
