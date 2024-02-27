<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <!--电脑端-->
    <el-card class="box-card hidden-sm-and-down" shadow="hover">
      <h1 class="flex-center">{{ articleOne.title }}</h1>
      <span style="display: flex; align-items: center; justify-content: space-between">
        <span style="display: flex; align-items: center">
          <el-space size="default">
            <el-avatar v-if="userBasic.avatar" size="default" :src="image(userBasic.avatar)"/>
            <span class="text-sm">{{ userBasic.username }}</span>
            <el-tag size="default">{{ articleOne.categoryName }}</el-tag>
            <template v-for="(item, index) in tags">
              <el-tag
                v-if="articleOne.tags && articleOne.tags.includes(item.id)"
                :key="index"
                style="margin-right: 4px"
                type="success"
                size="default"
                :label="index"
                border
              >{{ item.name }}
              </el-tag>
            </template>
          </el-space>
        </span>
        <el-space alignment="center" size="large">
          <span class="text-sm font-number text-color">
            <svg-icon icon-class="time-light"/>{{ articleOne.createdTime }}</span
          >
          <span class="text-sm font-number text-color">
            <svg-icon icon-class="eye-light" style="font-size: 15px"/> {{ articleOne.pageView }}
          </span>
          <span class="text-sm font-number text-color">
            <svg-icon icon-class="message" style="font-size: 15px"/> {{ commentCount }}
          </span>
          <span class="text-sm font-number" @click="clickLike(articleOne)">
            <svg-icon
              :icon-class="articleOne.clickLike == 1 ? 'give-dark' : 'give-light'"
              style="font-size: 15px; cursor: pointer"
              :style="{ color: articleOne.clickLike == 1 ? '#fd5a5a' : '' }"
            />
            {{ articleOne.likeCount }}
          </span>
        </el-space>
      </span>
      <hr class="divider"/>
      <div v-if="articleOne.isOriginal == 0" class="tip">
        原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br/>
      </div>
      <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br/></div>
      <v-md-preview :text="articleOne.text" ref="preview" @scroll="handlePreviewScroll"></v-md-preview>
      <hr class="divider"/>
      <h3 class="flex-center">推荐</h3>
      <el-row style="justify-content: center">
        <el-col v-for="(o, index) in dataList" :key="o" :span="7" :offset="index > 0 ? 1 : 0">
          <el-card :body-style="{ padding: '0px' }">
            <el-image fit="cover" :src="image(o.cover)" class="image" @click="getArticleId(o.id)"/>
            <div style="padding: 14px; text-align: center">
              <el-link :underline="false" @click="getArticleId(o.id)">
                <span>{{ o.title }} </span>
              </el-link>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <hr class="divider"/>
      <comments
        v-if="config.disabled"
        :config="config"
        @getListComment="getListComment"
        @submitComments="submitComments"
        @vanishDelete="vanishDelete"
      ></comments>
    </el-card>
  </el-col>
  <!--手机端-->
  <el-card class="box-card hidden-md-and-up" shadow="hover" style="width: 100%">
    <el-space alignment="center" wrap size="small">
      <el-tag size="small">{{ articleOne.categoryName }}</el-tag>
      <template v-for="(item, index) in tags">
        <el-tag
          v-if="articleOne.tags && articleOne.tags.includes(item.id)"
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
    <h1 class="flex-center">{{ articleOne.title }}</h1>
    <span
      style="display: flex; align-items: center; justify-content: space-between; flex-wrap: wrap"
    >
      <span style="display: flex; align-items: center">
        <el-space size="default">
          <el-avatar v-if="userBasic.avatar" size="small" :src="image(userBasic.avatar)"/>
          <span class="text-sm">{{ userBasic.username }}</span>
        </el-space>
      </span>
      <el-space alignment="center" size="small">
        <span class="text-xs font-number text-color" v-if="articleOne.createdTime">
          <svg-icon icon-class="time-light"/> {{ articleOne.createdTime.split(' ')[0] }}</span
        >
        <span class="text-xs font-number text-color">
          <svg-icon icon-class="eye-light"/> {{ articleOne.pageView }}
        </span>
        <span class="text-xs font-number text-color"
        ><svg-icon icon-class="message"/> {{ commentCount }}</span
        >
        <span class="text-xs font-number" @click="clickLike(articleOne)">
          <svg-icon
            :icon-class="articleOne.clickLike == 1 ? 'give-dark' : 'give-light'"
            style="font-size: 15px; cursor: pointer"
            :style="{ color: articleOne.clickLike == 1 ? '#fd5a5a' : '' }"
          />
          {{ articleOne.likeCount }}</span
        >
      </el-space>
    </span>
    <hr class="divider"/>

    <div v-if="articleOne.isOriginal == 0" class="tip">
      原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br/>
    </div>
    <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br/></div>
    <v-md-preview :text="articleOne.text"></v-md-preview>

    <hr class="divider"/>
    <h3 class="flex-center">推荐</h3>
    <el-card
      :body-style="{ padding: '0px' }"
      v-for="o in dataList"
      :key="o"
      style="margin-bottom: 8px; position: relative"
    >
      <el-image fit="cover" :src="image(o.cover)" class="image" @click="getArticleId(o.id)"/>
      <div style="padding: 14px; text-align: center">
        <el-link :underline="false" @click="getArticleId(o.id)">
          <span>{{ o.title }} </span>
        </el-link>
      </div>
    </el-card>
    <hr class="divider"/>
    <comments
      v-if="config.disabled"
      :config="config"
      @getListComment="getListComment"
      @submitComments="submitComments"
      @vanishDelete="vanishDelete"
    ></comments>
  </el-card>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <el-space direction="vertical" fill size="large" style="width: 100%">
      <el-card
        class="box-card"
        shadow="hover"
        :body-style="{ padding: '0px', height: '440px' }"
        style="position: relative"
      >
        <img src="../../assets/image/1.jpg" class="image"/>

        <div style="display: flex; padding: 10px; justify-content: center; text-align: center">
          <el-avatar v-if="userBasic.avatar" :src="image(userBasic.avatar)" class="panThumb"/>

          <el-space direction="vertical" :size="'large'" fill style="margin-top: 20px; width: 80%">
            <h2 style="margin-bottom: 0px">{{ userBasic.username }}</h2>
            <div>{{ userBasic.summary }}</div>
            <div style="padding-top: 20px">
              <el-space wrap size="default">
                <a v-if="userBasic.gitee" :href="userBasic.gitee" target="_blank">
                  <div class="diamond-clip-path diamond-icon">
                    <svg-icon icon-class="gitee"></svg-icon>
                  </div>
                </a>
                <a :href="userBasic.github" target="_blank">
                  <div class="diamond-clip-path diamond-icon">
                    <svg-icon icon-class="github"></svg-icon>
                  </div>
                </a>
                <a
                  v-if="userBasic.qqNumber"
                  :href=openQQ(userBasic.qqNumber)
                  target="_blank"
                >
                  <div class="diamond-clip-path diamond-icon">
                    <svg-icon icon-class="qq"></svg-icon>
                  </div>
                </a>
                <el-link :underline="false" v-if="userBasic.weChat" @click="open(userBasic.weChat)">
                  <div class="diamond-clip-path diamond-icon">
                    <svg-icon icon-class="wechat"></svg-icon>
                  </div>
                </el-link>
              </el-space>
            </div>

            <div style="display: inline-flex; vertical-align: top; justify-content: space-between">
              <el-space fill direction="vertical">
                <div class="text-sm text-color">文章</div>
                <div class="text-xl font-number">{{ userBasic.articleCount }}</div>
              </el-space>
              <el-space fill direction="vertical">
                <div class="text-sm text-color">分类</div>
                <div class="text-xl font-number">{{ userBasic.categoryCount }}</div>
              </el-space>
              <el-space fill direction="vertical" size="small">
                <div class="text-sm text-color">标签</div>
                <div class="text-xl font-number">{{ userBasic.tagsCount }}</div>
              </el-space>
              <el-space fill direction="vertical" size="small">
                <div class="text-sm text-color">评论</div>
                <div class="text-xl font-number">{{ userBasic.messageCount }}</div>
              </el-space>
            </div>
          </el-space>
        </div>
      </el-card>

      <div ref="header" :style="{ width: originalWidth + 'px' }" :class="{ 'fixed': isFixed }">
        <el-card class="box-card" shadow="hover" style="width: 100% ;position: relative">
          <template #header>
            <h2 class="text-lg" style="margin: 0">
              <svg-icon icon-class="tags"></svg-icon>
              目录
            </h2>
          </template>
          <div class="catalog">
            <div
              v-for="anchor in titles"
              :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
              @click="handleAnchorClick(anchor)"
              :class="{ 'toc-item': true, 'active': anchor.highlight }"
            >
              <el-link :underline="false">{{ anchor.title }}</el-link>
            </div>
          </div>
        </el-card>
      </div>


    </el-space>
  </el-col>
</template>

<script setup lang="ts">
import {reactive, ref, toRefs, watch, onBeforeMount, onMounted, onUnmounted, nextTick} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import {article, listArticles, listTag, getComment, articleLike, deleteComment} from '@/api/show'
import {addComment} from '@/api/user'
import comments from '@/components/comments/index.vue'
import {image, markdownImageFile, open, openQQ} from '@/utils/publicMethods'

// 文章详情
const articleOne = ref({})
// 用户信息
const userBasic = ref('')
const route = useRoute()
const titles = ref()
const preview = ref()
const router = useRouter()
// 标签列表
const tags = ref([])
// 展示文章列表
const dataList = ref([])
// 评论数
const commentCount = ref([])

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 3,
    type: 6,
    id: 0
  }
})
const config = ref({
  dataList: [],
  disabled: false,
  articleId: route.params.id
})

const {queryParams} = toRefs(data)

/** 查询展示推荐列表 */
function getList(categoryId: any) {
  queryParams.value.id = categoryId
  listArticles(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
  })
}

// 页面跳转
function getArticleId(id: any) {
  router.push({path: `/article/${id}`})
}

function clickLike(val: any) {
  const params: any = {
    articleId: val.id,
    clickLike: val.clickLike == null || val.clickLike === 0 ? 1 : 0
  }
  articleLike(params).then((res: any) => {
    if (res.data.code === 200) {
      ;(articleOne.value as any).clickLike = (articleOne.value as any).clickLike === 1 ? 0 : 1
      if ((articleOne.value as any).clickLike === 1) {
        // 点赞
        ;(articleOne.value as any).likeCount += 1
      } else {
        // 取消点赞
        ;(articleOne.value as any).likeCount -= 1
      }
    }
  })
}

// 获取文章详情
const getArticle = async () => {
  await article(route.params.id).then((res: any) => {
    articleOne.value = res.data.data
    userBasic.value = res.data.data.userBasic
    // 文章内图片地址替换
    articleOne.value.text = articleOne.value.text.replaceAll(
      markdownImageFile(''),
      `${import.meta.env.VITE_APP_BASE_API_FILE}${markdownImageFile('..')}`
    )
    if (res.data.data.categoryId){
      getList(res.data.data.categoryId)
    }
  })
}

/**
 * 标签列表
 */
const getTags = async () => {
  // 函解构用async和await包裹
  const {data: res} = await listTag() // 获取接口调用函数getList中的值data 其中data是表单里的数据
  // 对data进行解构赋值 取出请求的结果res
  tags.value = res.data
}

getTags()
// 跳转到指定位置
const handleAnchorClick = (anchor: any) => {
  const {lineIndex} = anchor
  const heading = preview.value.$el.querySelector(`[data-v-md-line="${lineIndex}"]`)
  if (heading) {
    preview.value.scrollToTarget({
      target: heading,
      scrollContainer: window,
      top: 80
    })
  }
}

// 目录生成方法
async function getCatalog() {
  await getArticle()
  const anchors = preview.value.$el.querySelectorAll('h1,h2,h3,h4,h5,h6')
  const filterTitles = Array.from(anchors).filter((title: any) => !!title.innerText.trim())
  if (!filterTitles.length) {
    titles.value = []
    return
  }
  const hTags = Array.from(new Set(filterTitles.map((title: any) => title.tagName))).sort()
  titles.value = filterTitles.map((el: any) => ({
    title: el.innerText,
    lineIndex: el.getAttribute('data-v-md-line'),
    indent: hTags.indexOf(el.tagName)
  }))
}

const activeIndex = ref(-1)
const handlePreviewScroll = () => {
  // 在滚动时更新当前高亮的标题索引
  const anchors = preview.value.$el.querySelectorAll('h1,h2,h3,h4,h5,h6');
  // 初始化最近的元素和距离
  let nearestElement = null;
  let nearestDistance = Infinity;

  anchors.forEach((anchor:any) => {
    const rect = anchor.getBoundingClientRect();
    const distanceToTop = rect.top;
    nearestDistance = distanceToTop;
    // 检查是否在视口内
    if (distanceToTop <= 90 && distanceToTop >= nearestDistance) {
      nearestElement = anchor;
      nearestDistance = distanceToTop;
    }
  });
  if (nearestElement) {
    const lineValue = nearestElement.getAttribute('data-v-md-line');
    // 在使用 titles 之前将所有元素的 highlight 属性设置为 false
    titles.value.forEach((title:any) => title.highlight = false);
    // 找到 lineIndex 等于 lineValue 的元素
    const targetTitle = titles.value.find((title:any) => title.lineIndex == lineValue);

    // 如果找到了符合条件的元素，设置其属性 highlight 为 true
    if (targetTitle) {
      targetTitle.highlight = true;
    }

    // console.log('Nearest Element with data-v-md-line:', nearestDistance);
  }
}

function getListComment() {
  getComment(route.params.id).then((res) => {
    commentCount.value = res.data.data.commentCount
    const array = res.data.data.commentTrees
    for (let i = 0; i < array.length; i++) {
      ;(array[i] as any).replyInputShow = false
    }
    config.value.dataList = array
    config.value.disabled = true
  })
}

function submitComments(val: any) {
  const data: any = {
    parentId: val.parentId,
    articleId: val.articleId,
    content: val.content
  }
  addComment(data).then((res: any) => {
    getListComment()
    ElMessage.success(res.data.msg)
  })
}

function vanishDelete(val: any) {
  deleteComment(val.id).then((res) => {
    getListComment()
    ElMessage.success(res.data.msg)
  })
}

onBeforeMount(async () => {
  // 监听$route对象上的参数属性变化
  watch(
    () => route.params.id,
    (newId, oldId) => {
      if (newId !== oldId) {
        // 如果发生变化重新载入
        window.location.reload()
      }
    }
  )
  await getCatalog()
})
const header = ref(null);
const isFixed = ref(false);
const originalWidth = ref(0);
const handleScroll = () => {
  const scrollPosition = window.scrollY;
  isFixed.value = scrollPosition > 400;
};
onMounted(() => {
  // 获取 header 元素的初始宽度
  // 使用 nextTick 来获取元素宽度，确保在 DOM 更新之后
  nextTick(() => {
    if (header.value) {
      originalWidth.value = header.value.getBoundingClientRect().width;
    }
  });
  // 监听滚动事件
  window.addEventListener('scroll', handleScroll);
  window.addEventListener('scroll', handlePreviewScroll);
});

onUnmounted(() => {
  // 在组件销毁时移除滚动事件监听器
  window.removeEventListener('scroll', handleScroll);
  window.removeEventListener('scroll', handlePreviewScroll);
});

getListComment()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}

.tip {
  padding: 10px;
  margin: 20px auto 15px;
  background-color: #ecf8ff;
  border-radius: 4px;
  border-left: 5px solid #50bfff;
  color: #888;
}

.image {
  width: 100%;
  height: 170px;
  display: block;
}

.panThumb {
  height: 100px !important;
  width: 100px !important;
  position: absolute !important;
  top: 115px;
  border: 5px solid #ffffff;
}

.image-hot:hover {
  transform: scale(1.1);
}

.image:hover {
  transform: scale(1.1);
}

/*样式穿透 md文件*/
>>> .github-markdown-body {
  padding: 0;
}

.fixed {
  position: fixed;
  top: 90px;
}
.catalog{
  overflow: hidden;
  max-height: calc(100vh - 400px);
}
/* 在滑动时显示滚动条 */
.catalog:hover {
  overflow-y: auto;
}

.toc-item {
  cursor: pointer;
}

.toc-item:hover {
  background-color: #f0f0f0;
}

.active {
  background-color: #f0f0f0;
}

</style>
