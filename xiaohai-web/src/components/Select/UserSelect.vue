<template>
  <el-select
    v-model="value"
    placeholder="作者"
    clearable
    size="small"
    style="width: 100px;"
    filterable
    @clear="clearSelection"
    @change="handleChange"
  >
    <el-option
      v-for="user in users"
      :key="user.id"
      :label="user.nickName || user.username"
      :value="user.id"
    >
      <span class="text-xs users">
        <el-avatar v-if="user.avatar" size="small" :src="image(user.avatar)" />
        <span v-else>
          <el-avatar v-if="user.nickName" size="small">{{ user.nickName }}</el-avatar>
          <el-avatar v-else size="small">{{ user.username }}</el-avatar>
        </span>
        <span v-if="user.nickName">{{ user.nickName }}</span>
        <span v-else>{{ user.username }}</span>
      </span>
    </el-option>
  </el-select>
</template>

<script>
import { image } from '@/utils/common'

export default {
  name: 'UserSelect',
  props: {
    value: {
      type: [String, Number],
      default: null
    }
  },
  computed: {
    users() {
      return this.$store.getters.users
    }
  },
  methods: {
    image,
    clearSelection() {
      this.$emit('input', null)
    },
    handleChange(value) {
      this.$emit('input', value)
      this.$emit('change', value)
    }
  }
}
</script>

<style scoped>
.users {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
