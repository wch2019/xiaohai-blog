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
    <div class="dataList">
      <div class="headProtrait">
        <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png">
      </div>
      <div class="dataListRight">
        <div class="title">
          <div>名字</div>
          <div>来自上海</div>
        </div>
        <div class="content">
          缘生缘灭，缘起缘落，我在看别人的故事，别人何尝不是在看我的故事?别人在演绎人生，我又何尝不是在这场戏里?谁的眼神沧桑了谁?我的眼神，只是沧桑了自己
        </div>
        <div class="listOperation">
          <div>
            <el-icon><PictureRounded /></el-icon>
            <span>2</span>
          </div>
          <div>
            <el-icon><ChatDotRound /></el-icon>
            <span>回复</span>
          </div>
        </div>
        <comments-input
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

const placeholderValue = ref('回复谁谁谁')
const btnValue = ref('发布')
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
</style>
