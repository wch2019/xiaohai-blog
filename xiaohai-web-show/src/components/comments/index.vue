<template>
  <div>
    <h3 class="flex-center">评论</h3>
    <div class="publishTop">
      <div class="headProtrait">
        <img :src="store.avatar ? store.avatar : '../static/avatar.svg'" />
      </div>
      <div style="width: 100%">
        <comments-input
          :articleId="articleId"
          @getListComment="getListComment"
          @submitComments="submitComments"
        ></comments-input>
      </div>
    </div>
    <h3 class="flex-center">全部评论</h3>
    <div class="dataList" v-for="(item, index) in dataList" :key="index">
      <div class="headProtrait">
        <img :src="image(item.avatar)" />
      </div>
      <div class="dataListRight">
        <div class="title">
          <h4>{{ item.username }}</h4>
          <span class="text-sm font-number text-color"> {{ item.createdTime }}</span>
        </div>
        <div class="content">
          <p v-html="parseEmojis(item.content)"></p>
        </div>
        <div class="listOperation">
          <div class="replyBtn" @click="replyClick(item)"><span class="text-sm"> 回复 </span></div>
          <div class="replyBtn" v-if="store.userId === item.userId" @click="deleteClick(item)">
            <span class="text-sm"> 删除 </span>
          </div>
        </div>
        <comments-input
          v-if="item.replyInputShow"
          :placeholderValue="placeholderValue"
          :btnValue="btnValue"
          :articleId="articleId"
          :parentId="item.id"
          @getListComment="getListComment"
          @submitComments="submitComments"
        ></comments-input>

        <div v-for="(i, k) in item.commentTrees" :key="k" class="dataList">
          <div class="headProtrait">
            <img :src="image(i.avatar)" />
          </div>
          <div class="dataListRight">
            <div class="titleChild">
              <div class="title">
                <h4>{{ i.username }}</h4>
                <span class="text-sm font-number text-color"> {{ i.createdTime }}</span>
              </div>
            </div>
            <div class="content">
              <span>@{{ i.replyUsername }}</span>
              {{ i.content }}
            </div>
            <div class="listOperation">
              <div class="replyBtn" @click="replyChildClick(i)">
                <span class="text-sm">回复</span>
              </div>
              <div class="replyBtn" v-if="store.userId === i.userId" @click="deleteClick(i)">
                <span class="text-sm">删除</span>
              </div>
            </div>
            <comments-input
              v-if="i.replyInputShow"
              :placeholderValue="placeholderValue"
              :btnValue="btnValue"
              :articleId="articleId"
              :parentId="i.id"
              @getListComment="getListComment"
              @submitComments="submitComments"
            ></comments-input>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, toRefs, watch } from 'vue'
import CommentsInput from '@/components/comments/commentsInput.vue'
import { allEmoji } from '@/components/emoji/emoji'
import useStore from '@/store'
import { image } from '@/utils/publicMethods'

const store = useStore()
const placeholderValue = ref('')
const btnValue = ref('发布')
const props = defineProps({
  config: {
    type: Object,
    default: {}
  }
})
const emit = defineEmits(['getListComment', 'submitComments', 'vanishDelete'])
const { config } = toRefs(props)
const { dataList, articleId } = toRefs(props.config)
watch(
  () => config.value,
  () => {
    dataList.value = config.value.dataList
  },
  { deep: true }
)
function deleteClick(item: any) {
  emit('vanishDelete', item)
}
function getListComment() {
  emit('getListComment')
}
function submitComments(val: any) {
  emit('submitComments', val)
}
function replyClick(item: any) {
  item.replyInputShow = !item.replyInputShow
  placeholderValue.value = `回复 @${item.username}`
}
function replyChildClick(item: any) {
  item.replyInputShow = !item.replyInputShow
  placeholderValue.value = `回复 @${item.username}`
}
function parseEmojis(text: any) {
  const emojiMap: any = allEmoji()
  // 使用正则表达式匹配表情符号，并替换为对应的图片标签
  return text.replace(/\[[^\[\]]+\]/g, (match: any) => {
    const emojiImg = emojiMap[match]
    if (emojiImg) {
      return `<img src="${emojiImg}" alt="${match}" style="width: 20px;"/>`
    }
    return match // 如果未找到对应的表情图片，则保留原始文本
  })
}
</script>

<style scoped lang="scss">
.publishTop {
  display: flex;
  justify-content: space-between;
}
.headProtrait {
  width: 40px;
  height: 40px;
  margin-right: 12px;
  img {
    width: 100%;
    border-radius: 50%;
  }
}
.dataList {
  display: flex;
  justify-content: space-between;
}
.dataListRight {
  width: 100%;
}
.title {
  height: 35px;
  margin-bottom: 5px;
  display: flex;
  align-items: center;
  //margin-bottom: 10px;
  justify-content: space-between;
  //
  //div:first-child {
  //  margin-right: 10px;
  //  //color: #303133;
  //  font-weight: 500;
  //  font-size: 15px;
  //}
  //div:nth-child(2) {
  //  color: rgb(147, 147, 147);
  //  font-size: 12px;
  //}
}
.content {
  line-height: 20px;
  span {
    color: #50cf97ff;
  }
}
.listOperation {
  display: flex;
  margin: 10px 0;
  line-height: 22px;
  font-size: 14px;
  color: #909399;
  div {
    display: flex;
    align-items: center;
    margin-right: 10px;
  }
  span {
    margin-left: 5px;
  }
}
.replyBtn {
  cursor: pointer;
}
.replyBtn:hover {
  color: rgb(30, 128, 255);
}
.commentTrees {
  display: flex;
  .childImg {
    margin: 10px;
    width: 25px;
    height: 25px;
    border-radius: 50%;
    img {
      width: 100%;
      border-radius: 50%;
    }
  }
  .titleChild {
    margin-right: 10px;
    //color: #303133;
    font-size: 12px;
    span:nth-of-type(2) {
      font-weight: 500;
    }
  }
  .content {
    font-size: 12px;
    line-height: 20px;
  }
}
</style>
