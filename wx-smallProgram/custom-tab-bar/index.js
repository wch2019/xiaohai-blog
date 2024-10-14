Component({
  data: {
    list:[{
      "pagePath": "/pages/homePage/index",
      "text": "首页"
    },
    {
      "pagePath": "/pages/tags/index",
      "text": "标签"
    },
    {
      "pagePath": "/pages/mine/index",
      "text": "我的"
    }],
    selected:0,// 选中的数据
  },
  methods:{
    switchTab(e){
      const data = e.currentTarget.dataset
      const url = data.path
      wx.switchTab({url})
      this.setData({
        selected: data.index
      })
  }
}
})
