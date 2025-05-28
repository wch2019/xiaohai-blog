<template>
  <el-row :gutter="10">
    <el-col
      v-for="btn in filteredButtons"
      :key="btn.type"
      :span="1.5"
    >
      <el-button
        :type="getButtonType(btn.type)"
        plain
        :icon="btn.icon"
        size="mini"
        :disabled="isDisabled(btn.type)"
        @click="$emit(btn.type)"
      >
        {{ btn.label }}
      </el-button>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'ActionButtons',
  props: {
    buttons: {
      type: Array,
      required: true,
      validator(arr) {
        return arr.every(
          (btn) => btn.type && btn.label && btn.icon && btn.permission
        )
      }
    },
    single: { type: Boolean, default: false },
    multiple: { type: Boolean, default: false }
  },
  computed: {
    userPermissions() {
      return this.$store.getters.permission || []
    },
    filteredButtons() {
      return this.buttons.filter(btn =>
        this.userPermissions.includes(btn.permission)
      )
    }
  },
  methods: {
    isDisabled(type) {
      if (type === 'update') return this.single
      if (type === 'delete') return this.multiple
      return false
    },
    // 按钮颜色
    getButtonType(type) {
      const map = {
        add: 'primary',
        update: 'success',
        delete: 'danger'
      }
      return map[type] || 'primary'
    }
  }
}
</script>
