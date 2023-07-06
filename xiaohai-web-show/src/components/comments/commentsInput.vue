<template>
  <div class="commentInput">
    <el-input v-model="content" :rows="4" type="textarea" :placeholder="placeholderValue" />
    <div class="opertionBtn">
      <el-popover placement="bottom" :width="200" trigger="hover">
        <template #reference>
          <h3 class="btnleft"><svg-icon icon-class="face"></svg-icon></h3>
        </template>
        <div class="emojiDiv">
          <el-tabs v-model="activeName" class="demo-tabs">
            <el-tab-pane v-for="(item, index) in emojiList" :name="item.label">
              <template #label>
                <span class="custom-tabs-label">
                  <img :src="item.img" class="emojiImg" />
                </span>
              </template>
              <div v-for="(v, k, i) in item.childrens" class="emojiBox">
                <img :src="v" class="emojiImg" @click="selectEmoji(k)" />
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-popover>
      <el-button type="primary" @click="submit()">{{ btnValue }}</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, toRefs } from 'vue'
import { emojiList } from '@/components/emoji/emoji'

const content = ref('')
const activeName = ref('first')
const props = defineProps({
  placeholderValue: {
    type: String,
    default: '留下点什么吧...'
  },
  btnValue: {
    type: String,
    default: '发表评论'
  },
  parentId: {
    type: Number,
    default: null
  },
  articleId: {
    type: String,
    default: ''
  }
})
const emit = defineEmits(['getListComment', 'submitComments'])
const { placeholderValue, btnValue, parentId, articleId } = toRefs(props)
function selectEmoji(val: any) {
  content.value += val
}
function submit() {
  const data: any = {
    parentId: parentId.value,
    articleId: articleId.value,
    content: content.value
  }
  emit('submitComments', data)
  content.value = ''
}
</script>

<style scoped lang="scss">
.commentInput {
  width: 100%;
}
.opertionBtn {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
  font-size: 14px;
  color: #909399;
  span {
    margin-left: 5px;
  }
}
.btnleft {
  margin: 0 10px;
  //display: flex;
  //align-items: center;
}
.emojiImg {
  width: 18px;
  height: 18px;
}
.emojiDiv {
  :deep(.el-tabs__nav) {
    width: 100%;
  }
  :deep(.el-tabs__item) {
    width: 50%;
  }
  :deep(.el-tabs__content) {
    height: 150px;
    overflow: auto;
  }
}
.emojiBox {
  width: 24px;
  margin: 2px;
  height: 24px;
  text-align: center;
  line-height: 24px;
  display: inline-block;
}
.emojiBox:hover {
  background: #ddd;
}
</style>
