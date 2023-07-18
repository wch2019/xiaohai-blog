<script setup lang="ts">
import { ref, watch, reactive, toRefs } from 'vue'
import { listArticles, articleLike } from '@/api/show'
import { getArticle, image } from '@/utils/publicMethods'
import useStore from '@/store/index'

const store = useStore()

// 定义父组件传递的属性类型
const props = defineProps({
  dataList: {
    type: Object,
    default: []
  }
})

const { dataList } = toRefs(props)
// 加载
const loading = ref(true)

/**
 * 点赞
 * @param val
 */
function clickLike(val: any) {
  const params: any = {
    articleId: val.id,
    clickLike: val.clickLike == null || val.clickLike === 0 ? 1 : 0
  }
  articleLike(params).then((res: any) => {
    if (res.data.code === 200) {
      for (let i = 0; i < dataList.value.length; i++) {
        if (dataList.value[i].id === val.id) {
          dataList.value[i].clickLike = dataList.value[i].clickLike === 1 ? 0 : 1
          if (dataList.value[i].clickLike === 1) {
            // 点赞
            dataList.value[i].likeCount += 1
          } else {
            // 取消点赞
            dataList.value[i].likeCount -= 1
          }
        }
      }
    }
  })
}

// 监听数据变动
watch(
  () => dataList?.value,
  () => {
    loading.value = false
  }
)
</script>

<template>
  <!--电脑端-->
  <el-space class="hidden-sm-and-down" direction="vertical" fill size="large" style="display: flex">
    <el-card
      :body-style="{ padding: '0px' }"
      v-loading="loading"
      v-for="article in dataList"
      :key="article"
      class="box-card box-card-hover"
      shadow="hover"
    >
      <div style="display: flex; flex-direction: row">
        <el-image :src="image(article.cover)" class="image" @click="getArticle(article.id)" />
        <div
          style="
            margin-left: 18px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 100%;
          "
        >
          <el-link :underline="false" style="justify-content: left">
            <h2
              style="
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                text-overflow: ellipsis;
                white-space: normal;
              "
              @click="getArticle(article.id)"
            >
              <svg-icon v-if="article.isTop == 1" icon-class="top" />
              {{ article.title }}
            </h2>
          </el-link>
          <span
            style="
              font-size: 14px;
              overflow: hidden;
              display: -webkit-box;
              -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;
              text-overflow: ellipsis;
              white-space: normal;
            "
          >
            {{ article.summary }}
          </span>
          <span style="display: flex; align-items: center; justify-content: space-between">
            <span style="display: flex; align-items: center">
              <el-space size="small">
                <el-avatar size="small" :src="image(article.avatar)" />
                <span v-if="article.nickName" class="text-xs">{{ article.nickName }}</span>
                <span v-else class="text-xs">{{ article.username }}</span>
                <el-tag size="small">{{ article.categoryName }}</el-tag>
                <template v-for="(item, index) in store.tags">
                  <el-tag
                    v-if="article.tags && article.tags.split(',').map(Number).includes(item.id)"
                    :key="index"
                    style="margin-right: 4px"
                    type="success"
                    size="small"
                    :label="index"
                    border
                    >{{ item.name }}
                  </el-tag>
                </template>
              </el-space>
            </span>

            <el-space alignment="center" size="large">
              <span class="text-xs font-number text-color">
                <svg-icon icon-class="eye-light" style="font-size: 15px" /> {{ article.pageView }}
              </span>
              <span class="text-xs font-number text-color"
                ><svg-icon icon-class="message" style="font-size: 15px" />
                {{ article.commentCount }}</span
              >
              <span class="text-xs font-number" @click="clickLike(article)"
                ><svg-icon
                  :icon-class="article.clickLike == 1 ? 'give-dark' : 'give-light'"
                  style="font-size: 15px; cursor: pointer"
                  :style="{ color: article.clickLike == 1 ? '#fd5a5a' : '' }"
                />
                {{ article.likeCount }}</span
              >
            </el-space>
          </span>
        </div>
      </div>
    </el-card>
  </el-space>
  <!--手机端-->
  <el-space class="hidden-md-and-up" direction="vertical" fill size="large">
    <el-card
      v-for="article in dataList"
      :key="article"
      class="box-card-mobile box-card-hover"
      shadow="hover"
      :body-style="{ padding: '10px' }"
    >
      <div class="article-flex">
        <el-image :src="image(article.cover)" class="image" @click="getArticle(article.id)" />
        <div
          style="
            margin-left: 10px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            width: 100%;
          "
        >
          <h2
            class="text-md"
            style="
              overflow: hidden;
              display: -webkit-box;
              -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;
            "
            @click="getArticle(article.id)"
          >
            <svg-icon v-if="article.isTop == 1" icon-class="top" />
            {{ article.title }}
          </h2>
          <span style="display: flex; align-items: center; justify-content: space-between">
            <span style="display: flex; align-items: center">
              <el-space size="small">
                <el-avatar size="small" :src="image(article.avatar)" />
                <span v-if="article.nickName" class="text-xs">{{ article.nickName }}</span>
                <span v-else class="text-xs">{{ article.username }}</span>
              </el-space>
            </span>
            <span class="text-xs font-number" @click="clickLike(article)">
              <svg-icon
                :icon-class="article.clickLike === 1 ? 'give-dark' : 'give-light'"
                style="font-size: 15px; cursor: pointer"
                :style="{ color: article.clickLike === 1 ? '#fd5a5a' : '' }"
              />
              {{ article.likeCount }}
            </span>
          </span>
        </div>
      </div>
    </el-card>
  </el-space>
</template>

<style scoped>
.box-card {
  max-height: 180px;
  padding: 4px;
  border-radius: 10px;
  border: 1px solid transparent;
}

.box-card-mobile {
  max-height: 120px;
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}

.image {
  border-radius: 10px;
  min-width: 238px;
  height: 140px;
}

.box-card-hover:hover {
  filter: drop-shadow(0 0 0.5em rgb(252, 191, 191));
  transform: scale(1.01);
}

/*手机端*/
@media (max-width: 992px) {
  .image {
    border-radius: 10px;
    min-width: 149px;
    height: 92px;
  }
}

.image:hover {
  transform: scale(1.1);
}

.category {
  height: 40px;
}

.article-flex {
  display: flex;
  flex-direction: row;
}

.article-right-flex {
  display: flex;
  flex-direction: row;
}

.carousel-title {
  cursor: pointer;
  position: absolute;
  z-index: 10;
  bottom: 40px;
  height: 40px;
  width: 100%;
  line-height: 40px;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
  font-size: 22px;
  color: #ecf8ff;
  font-weight: bold;
}
</style>
