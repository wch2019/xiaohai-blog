<!-- components/QueryFilters.vue -->
<template>
  <el-row :gutter="10" type="flex" justify="end">
    <!-- 清空按钮 -->
    <el-col :span="1.5">
      <el-tooltip content="清空" placement="top-start">
        <el-button icon="el-icon-delete" size="mini" circle @click="$emit('reset')"/>
      </el-tooltip>
    </el-col>
    <!-- 动态输入框和下拉 -->
    <el-col
      v-for="(item, index) in filters"
      :key="item.prop || index"
      :span="1.5"
    >
      <el-input
        v-if="item.type === 'input'"
        v-model="queryParams[item.prop]"
        :placeholder="item.placeholder || '请输入'"
        clearable
        size="small"
        :style="{ width: item.width || '140px' }"
        @input="$emit('query')"
      />
      <el-select
        v-else-if="item.type === 'select'"
        v-model="queryParams[item.prop]"
        :placeholder="item.placeholder || '请选择'"
        clearable
        size="small"
        :style="{ width: item.width || '100px' }"
        @clear="queryParams[item.prop] = null"
        @change="$emit('query')"
      >
        <el-option
          v-for="option in getSelectOptions(item)"
          :key="option.value"
          :label="option.label"
          :value="option.value"
        />
      </el-select>
    </el-col>

    <!-- 刷新按钮 -->
    <el-col :span="1.5">
      <el-tooltip content="刷新" placement="top-start">
        <el-button icon="el-icon-refresh" size="mini" circle @click="$emit('query')"/>
      </el-tooltip>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'QueryFilters',
  props: {
    queryParams: { type: Object, required: true },
    filters: {
      type: Array,
      default: () => []
    }
  },
  computed: {
    dicts() {
      return this.$store.getters.dict || {}
    }
  },
  methods: {
    getSelectOptions(item) {
      // 优先使用外部传入的 options
      if (Array.isArray(item.options)) {
        return item.options
      }

      // 否则根据 dict 字典名获取
      if (item.dict && this.dicts[item.dict]) {
        return this.dicts[item.dict].map(d => ({
          label: d.dictLabel,
          value: d.dictValue
        }))
      }

      // 没有数据就返回空数组
      return []
    }
  }
}
</script>
