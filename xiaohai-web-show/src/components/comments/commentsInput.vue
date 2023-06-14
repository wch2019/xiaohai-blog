<template>
  <div class="commentInput">
    <el-input
      v-model="textarea"
      :rows="4"
      type="textarea"
      :placeholder="props.placeholderValue"
    />
    <div class="opertionBtn">
        <el-popover
          placement="bottom"
          :width="200"
          trigger="hover"
        >
          <template #reference>
            <div class="btnleft">
              <el-icon><Sunny /></el-icon>
              <span>表情</span>
            </div>
          </template>
          <div class="emojiDiv">
            <el-tabs v-model="activeName" class="demo-tabs">
              <el-tab-pane v-for="(item,index) in emojiList" :name="item.label">
                <template #label>
                  <span class="custom-tabs-label">
                    <img :src="item.img" class="emojiImg">
                  </span>
                </template>
                <div v-for="k in item.childrens" class="emojiBox">
                  <img :src="k" class="emojiImg">
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-popover>

      <el-button type="primary">{{props.btnValue}}</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {emojiList} from '@/components/emoji/emoji'
console.log(emojiList,'emojiList')
const textarea = ref('')
const activeName = ref('first')
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
.emojiImg{
  width: 18px;
  height: 18px;
}
.emojiDiv{
   :deep(.el-tabs__nav){
    width: 100%;
  }
  :deep(.el-tabs__item){
    width: 50%;
  }
  :deep(.el-tabs__content){
    height: 150px;
    overflow: auto;
  }
}
.emojiBox{
  width: 24px;
  margin: 2px;
  height: 24px;
  text-align: center;
  line-height: 24px;
  display: inline-block;
}
.emojiBox:hover{
  background: #ddd;
}
</style>
