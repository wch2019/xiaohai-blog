const fs = wx.getFileSystemManager()

import { Base64 } from './base64.js';
const base64 = new Base64()

Component({
  properties: {
    // svg图片路径
    src: {
      type: String,
      value: ''
    },
    // svg颜色
    color: {
      type: String,
      value: ''
    },
    // svg宽度
    width: {
      type: String,
      value: '60rpx'
    },
    // svg高度
    height: {
      type: String,
      value: '60rpx'
    }
  },

  observers: {
    'src,color': function (src, color) {
      this.getSvgFile(src, color)
    }
  },
  data: {
    svgData: ''
  },
  methods: {
    getSvgFile(src, color) {
      let that = this;
      fs.readFile({
        filePath: src,
        encoding: 'UTF-8',
        position: 0,
        success(res) {
          let sourceFile = res.data;
          let newFile = that.changeColor(sourceFile, color);
          let svgBase64File = base64.encode(newFile);
          that.setData({
            svgData: 'data:image/svg+xml;base64,' + svgBase64File
          })
        },
        fail(res) {
          console.error(res)
        }
      })
    },

    changeColor(sourceFile, color) {
      let newSvg;
      if (/fill=".*?"/.test(sourceFile)) {
        newSvg = sourceFile.replace(/fill=".*?"/g, `fill="${color}"`);  // SVG有默认色
      } else {
        newSvg = sourceFile.replace(/<svg /g, `<svg fill="${color}" `); // 无默认色
      }
      return newSvg
    }
  }
})
