<template>
  <el-backtop :right="100" :bottom="50" />
  <!--  <el-backtop :bottom="100">-->
  <!--    <el-scrollbar ref="scrollbarRef" height="300px" always @scroll="scroll">-->
  <!--      <div ref="innerRef">-->
  <!--        <h3 v-for="item in 5" :key="item" class="scrollbar-demo-item">-->
  <!--          逆境清醒：{{ item }}<br />-->
  <!--          阳光总在风雨后，<br />-->
  <!--          历练中完成自我升华!<br />-->
  <!--          共勉！<br />-->
  <!--        </h3>-->
  <!--      </div>-->
  <!--    </el-scrollbar>-->

  <!--    <el-slider v-model="value" :max="max" :format-tooltip="formatTooltip" @input="inputSlider" />-->
  <!--  </el-backtop>-->
</template>

<script lang="ts" setup>
// import { onMounted, ref } from 'vue'
// import { ElScrollbar } from 'element-plus'
//
// const max = ref(0)
// const value = ref(0)
// const innerRef = ref<HTMLDivElement>()
// const scrollbarRef = ref<InstanceType<typeof ElScrollbar>>()
// onMounted(() => {
//   max.value = innerRef.value!.clientHeight - 380
// })
//
// const inputSlider = (value: number) => {
//   scrollbarRef.value!.setScrollTop(value)
// }
// const scroll = ({ scrollTop }) => {
//   value.value = scrollTop
// }
// const formatTooltip = (value: number) => {
//   return `${value} px`
// }
</script>

<style scoped></style>
