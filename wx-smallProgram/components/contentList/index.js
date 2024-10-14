// components/contentList/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    contentList: {
      type: Array,
      value: [],
    }
  },
  
  /**
   * 组件的初始数据
   */
  data: {
    fileUrl:getApp().globalData.fileUrl
  },
  created(){
  },
  /**
   * 组件的方法列表
   */
  methods: {
    viewDetail(event){
      let id = event.currentTarget.id
      wx.navigateTo({
        url:`/pages/article/index?id=${id}`
      })
    }
  }
})
