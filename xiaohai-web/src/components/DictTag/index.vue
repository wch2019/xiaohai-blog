<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.dictValue)">
        <span
          v-if="item.style === 'default' || item.style === null"
          :key="item.dictValue"
          :index="index"
        >{{ item.dictLabel }}</span>
        <el-tag
          v-else
          :key="item.dictValue"
          :disable-transitions="true"
          :index="index"
          :type="item.style"
        >
          {{ item.dictLabel }}
        </el-tag>
      </template>
    </template>
  </div>
</template>

<script>
export default {
  name: 'DictTag',
  props: {
    options: {},
    value: [Number, String, Array]
  },
  computed: {
    values() {
      if (this.value !== null && typeof this.value !== 'undefined') {
        return Array.isArray(this.value) ? this.value : [String(this.value)]
      } else {
        return []
      }
    }
  }
  // created() {
  //   console.log('这是value啊啊啊啊啊', this.value)
  //   console.log('这是options啊啊啊啊啊', this.options)
  // }
}
</script>
<style scoped>
</style>
