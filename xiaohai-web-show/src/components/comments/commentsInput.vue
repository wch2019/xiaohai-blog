<template>
  <div class="commentInput">
    <el-input
      v-model="textarea"
      :rows="4"
      type="textarea"
      :placeholder="props.placeholderValue"
      @focus="inputFocus()"
    />
    <div class="opertionBtn">
      <div class="btnleft">
        <el-icon><Sunny /></el-icon>
        <span>表情</span>
      </div>
      <el-button type="primary">{{props.btnValue}}</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";

const textarea = ref('')
const props = defineProps({
  placeholderValue:{
    type:String,
    default:'留下点什么吧...'
  },
  btnValue:{
    type:String,
    default:'发表评论'
  }
})
function inputFocus(){
  console.log('fouce')
}
</script>

<style scoped lang="scss">
.commentInput{
  width: 100%;
}
.opertionBtn{
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
  font-size: 14px;
  color: #909399;
  span{
    margin-left: 5px;
  }
}
.btnleft{
  display: flex;
  align-items: center;
}
</style>
