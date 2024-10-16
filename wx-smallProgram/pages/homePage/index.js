import {
  reqSwiperData,
  getCategory
} from '../../utils/api/homePage'
Page({
  data: {
    loading:true,
    indicatorDots: true,
    vertical: false,
    autoplay: false,
    interval: 2000,
    duration: 500,
    fileUrl:getApp().globalData.fileUrl,
    imgList:[],
    keywords: "",
    classifyList:[
      {title:'全部',type:'all'}
    ],
    active:0,
    contentList:[],
    paramsData:{
      pageNum:1,
      pageSize:10,
      total:0,
      allPageNum:1,// 总页数
      type:6,
      id:''
    },
    loadStatus:0,// 数据状态
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getInstance()
    this.getCategory()
    this.getImage() // 轮播
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {
    this.setData({
      "paramsData.pageNum":1
    },()=>{
      this.getContentList(false)
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    if (this.data.paramsData.pageNum < this.data.paramsData.allPageNum){
      this.setData({
        "paramsData.pageNum":this.data.paramsData.pageNum + 1
      },()=>{
        this.getContentList()
      })
    }else if (this.data.paramsData.pageNum == this.data.paramsData.allPageNum){
      this.setData({
        loadStatus:2
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  getInstance() {
    if (typeof this.getTabBar === 'function' ) {
      this.getTabBar((tabBar) => {
        tabBar.setData({
          selected: 0
        })
      })
    }
  },
  getImage(){
    reqSwiperData({
      pageNum:1,
      pageSize:3,
      type:2,
    }).then(res=>{
      this.setData({
        imgList:res.data.records
      })
    })
  },
  getContentList(status = true){
    if (!status){
      wx.pageScrollTo({
        scrollTop: 0,
        duration: 300
      })
    }
    this.setData({
      loadStatus:1
    })
    reqSwiperData({
      pageNum:this.data.paramsData.pageNum,
      pageSize:this.data.paramsData.pageSize,
      type:this.data.paramsData.type,
      id:this.data.paramsData.id,
    }).then(res=>{
      this.setData({
        contentList:status?this.data.contentList.concat(res.data.records):res.data.records,
        "paramsData.allPageNum":Math.ceil(res.data.total / res.data.size),
        loadStatus:0
      })
    }).catch(err=>{
      this.setData({
        loadStatus:3
      })
    })
  },
  async getCategory(){
    let res = await getCategory()
    this.setData({
      classifyList:res.data,
      "paramsData.id":res.data[0].id,
    },()=>{
      this.getContentList()
    })
  },
  handleTab(event){
    this.setData({
      "paramsData.id":this.data.classifyList[event.detail.index].id,
      "paramsData.pageNum":1
    },()=>{
      this.getContentList(false)
    })
  },
  tapSearch(event){
    wx.navigateTo({
      url:`/pages/search/index?keywords=${this.data.keywords}`
    })
  }
})
