<template>
  <!--左内容区-->
  <el-col :lg="14" :xl="11">
    <h1 class="flex-center">
      <el-icon>
        <UserFilled/>
      </el-icon>
      关于
    </h1>
    <el-card class="box-card" shadow="hover">
      <div ref="preview" class="preview"></div>
    </el-card>
  </el-col>
  <!--右内容区-->
  <el-col class="hidden-md-and-down" :lg="6" :xl="5">
    <RightSide></RightSide>
  </el-col>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'
import RightSide from '@/components/layouts/RightSide.vue'
import useStore from '@/store/index'
import {markdownImageFile} from "@/utils/publicMethods";
import Vditor from "vditor";
import 'vditor/dist/index.css'

const store = useStore()

// 关于信息
const content = ref<any>('')
// 网站信息
const website = ref<any>(store.website)

function getContent() {
  // 文章内图片地址替换
  content.value = website.value.content.replaceAll(
    markdownImageFile(''),
    `${import.meta.env.VITE_APP_BASE_API_FILE}` + markdownImageFile('')
  )
  renderMarkdown(content.value)
}

onMounted(() => {
  getContent()
})

function renderMarkdown(md: any) {
  const previewElement = document.querySelector(".preview") as HTMLDivElement;
  if(previewElement){
    Vditor.preview(previewElement,
      md,
      {
        mode: "light",
        anchor: 2,
        hljs: {style: "github", lineNumber: true},
        transform(html) {
          return html.replaceAll('<img', '<img referrerPolicy="no-referrer"')
        },
        after: function () {
          // 事件委托处理图片点击事件
          previewElement.addEventListener("click", (event) => {
            // 使用类型断言告诉 TypeScript 事件的目标是 HTMLImageElement 类型
            const target = event.target as HTMLImageElement;
            if (target.tagName === "IMG") {
              Vditor.previewImage(target);
            }
          });
        },
      })
  }

}

getContent()
</script>

<style scoped>
.box-card {
  padding: 0;
  border-radius: 10px;
  border: 1px solid transparent;
}
</style>
