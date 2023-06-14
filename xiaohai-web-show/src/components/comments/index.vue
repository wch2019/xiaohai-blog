<template>
  <div>
    <h3 class="flex-center">评论</h3>
    <div class="publishTop">
      <div class="headProtrait">
        <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
      </div>
      <div style="width: 100%">
        <comments-input></comments-input>
      </div>
    </div>
    <h3 class="flex-center">全部评论</h3>
    <div class="dataList" v-for="(item,index) in dataList" :key="index">
      <div class="headProtrait">
        <img :src="image(item.avatar)">
      </div>
      <div class="dataListRight">
        <div class="title">
          <div>{{item.username}}</div>
<!--          <div>来自上海</div>-->
        </div>
        <div class="content">
          {{item.content}}
        </div>
        <div class="listOperation">
          <div>
            <el-icon><PictureRounded /></el-icon>
            <span>2</span>
          </div>
          <div class="replyBtn" @click="replyClick()">
            <el-icon><ChatDotRound /></el-icon>
            <span>回复</span>
          </div>
        </div>
        <comments-input
          v-if="replyInputShow"
          :placeholderValue="placeholderValue"
          :btnValue="btnValue"
        ></comments-input>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import CommentsInput from "@/components/comments/commentsInput.vue";
import {ref} from "vue";
import {getComment} from "@/api/show";
const placeholderValue = ref('回复谁谁谁')
const btnValue = ref('发布')
const replyInputShow = ref(false)
const dataList = ref([])
const props = defineProps({
  articleId:{
    type:Number,
    default:0
  }
})

function replyClick(){
  replyInputShow.value =!replyInputShow.value
}
function getlistComment(){
  getComment(props.articleId).then(res=>{
    dataList.value = res.data.data
  })
}
getlistComment()
</script>

<style scoped lang="scss">
.publishTop{
  display: flex;
  justify-content: space-between;
}
.headProtrait{
  width: 40px;
  height: 40px;
  margin-right: 20px;
  img{
    width: 100%;
    border-radius: 50%;
  }
}
.dataList{
  display: flex;
  justify-content: space-between;
}
.dataListRight{
  width: 100%;
}
.title{
  display: flex;
  align-items: center;
  margin-bottom: 10px;

  div:first-child{
    margin-right: 10px;
    color: #303133;
    font-weight: 500;
    font-size: 15px;
  }
  div:nth-child(2){
    color: rgb(147, 147, 147);
    font-size: 12px;
  }
}
.content{
  font-size: 14px;
  line-height: 20px;
}
.listOperation{
  display: flex;
  margin: 10px 0;
  line-height: 22px;
  font-size: 14px;
  color: #909399;
  div{
    display: flex;
    align-items: center;
    margin-right: 10px;
  }
  span{
    margin-left: 5px;
  }
}
.replyBtn{
  cursor: pointer;
}
.replyBtn:hover{
  color: rgb(30, 128, 255);
}
</style>
