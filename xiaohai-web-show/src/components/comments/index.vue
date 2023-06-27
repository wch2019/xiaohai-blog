<template>
  <div>
    <h3 class="flex-center">评论</h3>
    <div class="publishTop">
      <div class="headProtrait">
        <img :src="store.avatar ? store.avatar : '../../src/assets/icons/svg/avatar.svg'" />
      </div>
      <div style="width: 100%">
        <comments-input
          :articleId="articleId"
          @getlistComment="getlistComment"
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
        <div>
          <div class="title">
            <div>{{ item.username }}</div>
            <!--          <div>来自上海</div>-->
          </div>
          <div class="content">
            <span v-html="parseEmojis(item.content)"></span>
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
            :articleId="articleId"
            :parentId="item.id"
            @getlistComment="getlistComment"
            @submitComments="submitComments"
          ></comments-input>
        </div>
        <div v-for="(i, k) in item.commentTrees" :key="k" class="commentTrees">
          <div class="childImg">
            <img :src="image(i.avatar)" />
          </div>
          <div class="dataListRight">
            <div class="titleChild">
              <span>{{ i.username }}</span
              >&nbsp; <span>回复</span>&nbsp;
              <span>{{ i.replyUsername }}</span>
            </div>
            <div class="content">
              {{ i.content }}
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
              :articleId="articleId"
              :parentId="i.id"
              @getlistComment="getlistComment"
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

const store = useStore()
const placeholderValue = ref('')
const btnValue = ref('发布')
const props = defineProps({
  config: {
    type: Object,
    default: {}
  }
})
const emit = defineEmits(['getlistComment', 'submitComments'])
const { config } = toRefs(props)
const { dataList, articleId } = toRefs(props.config)
watch(
  () => config.value,
  () => {
    dataList.value = config.value.dataList
  },
  { deep: true }
)

function getlistComment() {
  emit('getlistComment')
}
function submitComments(val: any) {
  emit('submitComments', val)
}
function replyClick(item: any) {
  item.replyInputShow = !item.replyInputShow
  placeholderValue.value = `回复${item.username}`
}
function replyChildClick(item: any) {
  item.replyInputShow = !item.replyInputShow
  placeholderValue.value = `回复${item.username}`
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
  margin-right: 20px;
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
  display: flex;
  align-items: center;
  margin-bottom: 10px;

  div:first-child {
    margin-right: 10px;
    color: #303133;
    font-weight: 500;
    font-size: 15px;
  }
  div:nth-child(2) {
    color: rgb(147, 147, 147);
    font-size: 12px;
  }
}
.content {
  font-size: 14px;
  line-height: 20px;
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
    color: #303133;
    font-size: 12px;
    span:nth-of-type(2) {
      font-weight: 500;
    }
  }
  .content {
    font-size: 12px;
    line-height: 20px;
  }
  .listOperation {
    font-size: 12px;
  }
}
</style>
