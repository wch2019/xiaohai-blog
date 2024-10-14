// pages/tags/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    tagsList:[
      {title:'Ts',num:2},
      {title:'壁纸',num:2},
      {title:'服务器',num:5},
      {title:'浏览器',num:2},
      {title:'Javascript',num:4},
      {title:'虚拟机',num:4},
      {title:'逆向',num:4},
      {title:'网络安全',num:4},
      {title:'Ts',num:2},
      {title:'壁纸',num:2},
      {title:'服务器',num:5},
      {title:'浏览器',num:2},
      {title:'Javascript',num:4},
      {title:'虚拟机',num:4},
      {title:'逆向',num:4},
      {title:'网络安全',num:4},
      {title:'Ts',num:2},
      {title:'壁纸',num:2},
      {title:'服务器',num:5},
      {title:'浏览器',num:2},
      {title:'Javascript',num:4},
      {title:'虚拟机',num:4},
      {title:'逆向',num:4},
      {title:'网络安全',num:4},
    ]
  },
  pageLifetimes: {
    show: function() {
      this.getInstance()
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    getInstance() {
      if (typeof this.getTabBar === 'function' ) {
        this.getTabBar((tabBar) => {
          tabBar.setData({
            selected: 1
          })
        })
      }
    },
  }
})
