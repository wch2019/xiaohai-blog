<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.value)">
        <span
          v-if="item.raw.style === 'default' || item.raw.style === ''"
          :key="item.value"
          :index="index"
        >{{ item.label }}</span>
        <el-tag
          v-else
          :key="item.value"
          :disable-transitions="true"
          :index="index"
          :type="item.raw.style"
        >
          {{ item.label }}
        </el-tag>
      </template>
    </template>
  </div>
</template>

<script>
export default {
  name: 'DictTag',
  props: {
    options: {
      type: Array,
      default: null
    },
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
}
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
