<template>
  <div>
    <h3 class="flex-center">评论</h3>
    <div class="publishTop">
      <div class="headProtrait">
        <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
      </div>
      <div style="width: 100%">
        <comments-input :articleId="props.articleId" @getlistComment="getlistComment"></comments-input>
      </div>
    </div>
    <h3 class="flex-center">全部评论</h3>
    <div class="dataList" v-for="(item,index) in dataList" :key="index">
      <div class="headProtrait">
        <img :src="image(item.avatar)">
      </div>
      <div class="dataListRight">
        <div>
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
            <div class="replyBtn" @click="replyClick(item)">
              <el-icon><ChatDotRound /></el-icon>
              <span>回复</span>
            </div>
          </div>
          <comments-input
            v-if="item.replyInputShow"
            :placeholderValue="placeholderValue"
            :btnValue="btnValue"
            :articleId="props.articleId"
            :parentId="item.id"
            @getlistComment="getlistComment"
          ></comments-input>
        </div>
        <div v-for="(i,k) in item.commentTrees" :key="k" class="commentTrees">
          <div class="childImg">
            <img :src="image(i.avatar)">
          </div>
          <div class="dataListRight">
            <div class="titleChild">
              <span>{{i.username}}</span>&nbsp;
              <span>回复</span>&nbsp;
              <span>{{i.parentName}}</span>
            </div>
            <div class="content">
              {{i.content}}
            </div>
            <div class="listOperation">
              <div>
                <el-icon><PictureRounded /></el-icon>
                <span>2</span>
              </div>
              <div class="replyBtn" @click="replyChildClick(i)">
                <el-icon><ChatDotRound /></el-icon>
                <span>回复</span>
              </div>
            </div>
            <comments-input
              v-if="i.replyInputShow"
              :placeholderValue="placeholderValue"
              :btnValue="btnValue"
              :articleId="props.articleId"
              :parentId="i.id"
              @getlistComment="getlistComment"
            ></comments-input>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import CommentsInput from "@/components/comments/commentsInput.vue";
import {ref} from "vue";
import {getComment} from "@/api/show";
const placeholderValue = ref('')
const btnValue = ref('发布')
const dataList = ref([])
const props = defineProps({
  articleId:{
    type:Number,
    default:0
  }
})

function replyClick(item:any){
  item.replyInputShow = !item.replyInputShow
  placeholderValue.value = `回复${item.username}`
}
function replyChildClick(item:any){
  item.replyInputShow = !item.replyInputShow
  placeholderValue.value = `回复${item.username}`
}
function getlistComment(){
  getComment(props.articleId).then(res=>{
    dataList.value = res.data.data
    for (let i=0;i<dataList.value.length;i++){
        (dataList.value[i] as any).replyInputShow = false
    }
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
.commentTrees{
  display: flex;
  .childImg{
    width: 25px;
    height: 25px;
    border-radius: 50%;
    img{
      width: 100%;
      border-radius: 50%;
    }
  }
  .titleChild{
    margin-right: 10px;
    color: #303133;
    font-size: 12px;
    span:nth-of-type(2){
      font-weight: 500;
    }
  }
  .content{
    font-size: 12px;
    line-height: 20px;
  }
  .listOperation{
    font-size: 12px;
  }
}
</style>
