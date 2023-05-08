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
            ><svg-icon icon-class="message" style="font-size: 15px" /> 30</span
          >
          <span class="text-sm font-number"
            ><svg-icon icon-class="give-light" style="font-size: 15px" /> 20</span
          >
        </el-space>
      </span>
      <el-divider />
      <div v-if="articleOne.isOriginal == 0" class="tip">
        原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br />
      </div>
      <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br /></div>
      <v-md-preview :text="articleOne.text" ref="preview"></v-md-preview>
      <el-divider />
      <h3 class="flex-center"><svg-icon icon-class="hot"></svg-icon> 推荐</h3>
      <el-row style="justify-content: center">
        <el-col v-for="(o, index) in 3" :key="o" :span="7" :offset="index > 0 ? 1 : 0">
          <el-card :body-style="{ padding: '0px' }">
            <img
              src="http://localhost:8089/api/document/upload/image/1/20230401.jpg"
              class="image"
            />
            <div style="padding: 14px">
              <div
                style="
                  display: flex;
                  flex-direction: column;
                  justify-content: space-between;
                  align-items: flex-end;
                "
              >
                <el-link :underline="false" style="justify-content: left">
                  <span
                    style="
                      overflow: hidden;
                      display: -webkit-box;
                      -webkit-line-clamp: 2;
                      -webkit-box-orient: vertical;
                    "
                    >啦啦啦啦啦啦，对对对啦啦啦啦啦啦啦，对对对啦
                  </span>
                </el-link>
                <span class="text-xs font-number text-color">2023-04-23</span>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-divider />
      <h3 class="flex-center"><svg-icon icon-class="message"></svg-icon> <span>评论</span></h3>
      <el-empty description="暂无评论" />
    </el-card>
    <!--手机端-->
    <el-card class="box-card hidden-md-and-up" shadow="hover">
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
      <el-divider />

      <div v-if="articleOne.isOriginal == 0" class="tip">
        原创 本文DotCode原创文章，转载无需和我联系，但请注明来自本站<br />
      </div>
      <div v-else class="tip">转载 本文转载自{{ articleOne.originalUrl }}<br /></div>
      <v-md-preview :text="articleOne.text"></v-md-preview>

      <el-divider />
      <el-divider />
      <h3 class="flex-center"><svg-icon icon-class="message"></svg-icon> <span>评论</span></h3>
      <el-empty description="暂无评论" />
    </el-card>
  </el-col>

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
          <el-avatar
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            class="panThumb"
          />

          <el-space direction="vertical" :size="'large'" fill style="margin-top: 20px; width: 80%">
            <h3>xiaohai</h3>
            <div>用一点点代码改变生活</div>
            <div style="display: inline-flex; vertical-align: top; justify-content: space-between">
              <el-space fill direction="vertical">
                <div class="text-sm text-color">文章</div>
                <div class="text-xl font-number">7</div>
              </el-space>
              <el-space fill direction="vertical">
                <div class="text-sm text-color">分类</div>
                <div class="text-xl font-number">4</div>
              </el-space>
              <el-space fill direction="vertical" size="small">
                <div class="text-sm text-color">标签</div>
                <div class="text-xl font-number">14</div>
              </el-space>
              <el-space fill direction="vertical" size="small">
                <div class="text-sm text-color">评论</div>
                <div class="text-xl font-number">2</div>
              </el-space>
            </div>
          </el-space>
        </div>
      </el-card>
      <el-card class="box-card" shadow="hover">
        <template #header>
          <h2 class="text-lg" style="margin: 0"><svg-icon icon-class="tags"></svg-icon> 目录</h2>
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
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { article, listTag } from '@/api/show'

// 文章详情
const articleOne = ref('')
const route = useRoute()
const titles = ref()
const preview = ref()
// 标签列表
const tags = ref([])

// 获取文章详情
const getArticle = async () => {
  await article(route.params.id).then((res: any) => {
    articleOne.value = res.data.data
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
onMounted(async () => {
  await getArticle()
  console.log('[ proxy ]', preview.value.$el)
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
</style>
