// pages/article/index.js
import {
  articleDetails, getComment
} from '../../utils/api/article'
import {markdownImageFile} from "../../utils/util";
//获取应用实例
const app = getApp();
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
    nodes:'',
    text:null,
    title:'',
    userBasic:{},
    fileUrl:getApp().globalData.fileUrl,
    createdTime:'',
    pageView:'',
    id:'',
    commentList:[]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad: function(options) {
      this.setData({
        id:options.id
      },()=>{
        this.getArticleDetails() // 文章详情
        this.getComment() // 评论区
      })
    },
    getArticleDetails(){
      articleDetails(this.data.id).then(res=>{
        res.data.text = res.data.text.replaceAll(
            markdownImageFile(''),
            `${this.data.fileUrl}${markdownImageFile('..')}`
        )
        let nodes = app.towxml(`${res.data.text}`,'markdown',{
          theme: 'light'
        });
        this.setData({
          nodes:nodes,
          title:res.data.title,
          userBasic:res.data.userBasic,
          createdTime:res.data.createdTime,
          pageView:res.data.pageView,
        })
      })
    },
    getComment(){
      getComment(this.data.id).then(res=>{
        this.setData({
          commentList:res.data.commentTrees
        })
      })
    }
  }
})
