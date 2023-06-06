<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <!--电脑端-->
    <el-card class="box-card hidden-sm-and-down" shadow="hover">
      <h1 class="flex-center">{{ articleOne.title }}</h1>
      <span style="display: flex; align-items: center; justify-content: space-between">
        <span style="display: flex; align-items: center">
          <el-space size="default">
            <el-avatar
              size="default"
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
            <span class="text-sm">xiaohai</span>
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
            <svg-icon icon-class="time-light" />{{ articleOne.createdTime }}</span
          >
          <span class="text-sm font-number text-color">
            <svg-icon icon-class="eye-light" style="font-size: 15px" /> {{ articleOne.pageView }}
          </span>
          <span class="text-sm font-number text-color"
            ><svg-icon icon-class="message" style="font-size: 15px" /> 30
          </span>
          <span class="text-sm font-number">
            <svg-icon icon-class="give-light" style="font-size: 15px" /> 20
          </span>
        </el-space>
      </span>
      <hr class="divider" />
      <div v-if="articleOne.isOriginal == 0" class="tip">
        原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br />
      </div>
      <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br /></div>
      <v-md-preview :text="articleOne.text" ref="preview"></v-md-preview>
      <hr class="divider" />
      <h3 class="flex-center">推荐</h3>
      <el-row style="justify-content: center">
        <el-col v-for="(o, index) in dataList" :key="o" :span="7" :offset="index > 0 ? 1 : 0">
          <el-card :body-style="{ padding: '0px' }">
            <img :src="image(o.cover)" class="image" @click="getArticleId(o.id)" />
            <div style="padding: 14px; text-align: center">
              <el-link :underline="false" @click="getArticleId(o.id)">
                <span>{{ o.title }} </span>
              </el-link>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <hr class="divider" />
      <u-comment :config="config" @submit="submit" @like="like" relative-time></u-comment>
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
          <el-avatar
            size="small"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          />
          <span class="text-sm">xiaohai</span>
        </el-space>
      </span>
      <el-space alignment="center" size="small">
        <span class="text-xs font-number text-color" v-if="articleOne.createdTime">
          <svg-icon icon-class="time-light" /> {{ articleOne.createdTime.split(' ')[0] }}</span
        >
        <span class="text-xs font-number text-color">
          <svg-icon icon-class="eye-light" /> {{ articleOne.pageView }}
        </span>
        <span class="text-xs font-number text-color"><svg-icon icon-class="message" /> 30</span>
        <span class="text-xs font-number"><svg-icon icon-class="give-light" /> 20</span>
      </el-space>
    </span>
    <hr class="divider" />

    <div v-if="articleOne.isOriginal == 0" class="tip">
      原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br />
    </div>
    <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br /></div>
    <v-md-preview :text="articleOne.text"></v-md-preview>

    <hr class="divider" />
    <h3 class="flex-center">推荐</h3>
    <el-card
      :body-style="{ padding: '0px' }"
      v-for="o in dataList"
      :key="o"
      style="margin-bottom: 8px; position: relative"
    >
      <img :src="image(o.cover)" class="image" @click="getArticleId(o.id)" />
      <div style="padding: 14px; text-align: center">
        <el-link :underline="false" @click="getArticleId(o.id)">
          <span>{{ o.title }} </span>
        </el-link>
      </div>
    </el-card>
    <hr class="divider" />
    <h3 class="flex-center">
      <svg-icon icon-class="message"></svg-icon>
      <span>评论</span>
    </h3>
    <el-empty description="暂无评论" />
  </el-card>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <el-space direction="vertical" fill size="large" style="position: fixed; top: 90px">
      <el-card
        class="box-card"
        shadow="hover"
        :body-style="{ padding: '0px', height: '380px' }"
        style="position: relative"
      >
        <img src="http://localhost:8089/api/document/upload/image/1/20230401.jpg" class="image" />

        <div style="display: flex; padding: 10px; justify-content: center; text-align: center">
          <el-avatar :src="image(userBasic.avatar ? userBasic.avatar : '')" class="panThumb" />

          <el-space direction="vertical" :size="'large'" fill style="margin-top: 20px; width: 80%">
            <h3>{{ userBasic.username }}</h3>
            <div>{{ userBasic.summary }}</div>
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
      <el-card class="box-card" shadow="hover">
        <template #header>
          <h2 class="text-lg" style="margin: 0">
            <svg-icon icon-class="tags"></svg-icon>
            目录
          </h2>
        </template>
        <div
          v-for="anchor in titles"
          :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
          @click="handleAnchorClick(anchor)"
        >
          <el-link :underline="false">{{ anchor.title }}</el-link>
        </div>
      </el-card>
    </el-space>
  </el-col>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref, toRefs, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { CommentApi, ConfigApi, SubmitParamApi, UToast, createObjectURL, dayjs } from 'undraw-ui'
import { ElMessage } from 'element-plus'
import { article, listArticles, listTag, getComment } from '@/api/show'
import { addComment } from '@/api/user'
import emoji from '@/components/emoji/emoji'

// 文章详情
const articleOne = ref('')
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
// 评论列表
const commentList = ref([])

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 3,
    type: 6,
    id: 0
  },
  comment: {
    parentId: 0,
    articleId: route.params.id,
    content: ''
  }
})

const config = reactive<ConfigApi>({
  user: {
    id: 1,
    username: 'jack',
    avatar:
      'https://static.juzicon.com/avatars/avatar-200602130320-HMR2.jpeg?x-oss-process=image/resize,w_100',
    // 评论id数组 建议:存储方式用户uid和评论id组成关系,根据用户uid来获取对应点赞评论id,然后加入到数组中返回
    likeIds: [1, 2, 3]
  },
  emoji,
  comments: [],
  total: 10
})
const temp_id = 100

config.comments = [
  {
    id: '1',
    parentId: null,
    uid: '1',
    address: '来自上海',
    content:
      '缘生缘灭，缘起缘落，我在看别人的故事，别人何尝不是在看我的故事?别人在演绎人生，我又何尝不是在这场戏里?谁的眼神沧桑了谁?我的眼神，只是沧桑了自己[喝酒]',
    likes: 2,
    contentImg: 'https://gitee.com/undraw/undraw-ui/raw/master/public/docs/normal.webp',
    createTime: dayjs().subtract(10, 'minute').toString(),
    user: {
      username: '落🤍尘',
      avatar:
        'https://static.juzicon.com/avatars/avatar-200602130320-HMR2.jpeg?x-oss-process=image/resize,w_100',
      level: 6,
      homeLink: '/1'
    }
  }
]

const { queryParams, comment } = toRefs(data)

/** 查询展示推荐列表 */
function getList(categoryId: any) {
  queryParams.value.id = categoryId
  listArticles(queryParams.value).then((response) => {
    dataList.value = response.data.data.records
  })
}

/**
 * 根据文章id获取评论列表
 */
function getCommentList() {
  getComment(route.params.id).then((response) => {
    commentList.value = response.data.data.records
  })
}
// 提交评论事件
function addCommenta(categoryId: any) {
  addComment(route.params.id).then((response) => {
    commentList.value = response.data.data.records
    ElMessage.info(response.msg)
  })
}

// 页面跳转
function getArticleId(id: any) {
  router.push({ path: `/article/${id}` })
}

/**
 * 图片地址拼接
 * @param cover
 */
function image(cover: any) {
  return import.meta.env.VITE_APP_BASE_API_FILE + cover
}

// 获取文章详情
const getArticle = async () => {
  await article(route.params.id).then((res: any) => {
    articleOne.value = res.data.data
    userBasic.value = res.data.data.userBasic
    // 文章内图片地址替换
    articleOne.value.text = articleOne.value.text.replaceAll(
      '../image',
      `${import.meta.env.VITE_APP_BASE_API_FILE}/image`
    )
    console.log(articleOne.value)
    getCommentList()
    getList(res.data.data.categoryId)
  })
}

/**
 * 标签列表
 */
const getTags = async () => {
  // 函解构用async和await包裹
  const { data: res } = await listTag() // 获取接口调用函数getList中的值data 其中data是表单里的数据
  // 对data进行解构赋值 取出请求的结果res
  tags.value = res.data
}

getTags()
// 跳转到指定位置
const handleAnchorClick = (anchor: any) => {
  const { lineIndex } = anchor
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

onMounted(async () => {
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

>>> .u-comment {
  background-color: transparent;
  padding: 0;
}
</style>
