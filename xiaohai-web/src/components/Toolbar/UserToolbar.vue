<!-- components/UserToolbar.vue -->
<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5">
      <el-button
        v-if="hasPermission('system:user:add')"
        type="primary"
        plain
        icon="el-icon-plus"
        size="mini"
        @click="$emit('add')"
      >新增</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button
        v-if="hasPermission('system:user:update')"
        type="success"
        plain
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="$emit('update')"
      >修改</el-button>
    </el-col>
    <el-col :span="1.5">
      <el-button
        v-if="hasPermission('system:user:delete')"
        type="danger"
        plain
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="$emit('delete')"
      >删除</el-button>
    </el-col>

    <span style="float: right">
      <el-col :span="1.5">
        <el-tooltip effect="dark" content="清空" placement="top-start">
          <el-button icon="el-icon-delete" size="mini" circle style="min-width: 0;" @click="$emit('reset')" />
        </el-tooltip>
      </el-col>

      <el-col :span="1.5">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入用户名称"
          clearable
          size="small"
          style="width: 140px"
          @input="$emit('query')"
        />
      </el-col>

      <el-col :span="1.5">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          size="small"
          style="width: 140px"
          @input="$emit('query')"
        />
      </el-col>

      <el-col :span="1.5">
        <el-select
          v-model="queryParams.status"
          placeholder="状态"
          clearable
          size="small"
          style="width: 100px;"
          @clear="queryParams.status = null"
          @change="$emit('query')"
        >
          <el-option
            v-for="dict in dictStatus"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-col>

      <el-col :span="1.5">
        <el-tooltip effect="dark" content="刷新" placement="top-start">
          <el-button icon="el-icon-refresh" size="mini" circle style="min-width: 0;" @click="$emit('query')" />
        </el-tooltip>
      </el-col>
    </span>
  </el-row>
</template>

<script>
export default {
  name: 'UserToolbar',
  props: {
    queryParams: { type: Object, required: true },
    single: { type: Boolean, default: false },
    multiple: { type: Boolean, default: false }
  },
  computed: {
    dictStatus() {
      return this.$store.getters.dict?.sys_normal_disable || [];
    },
  },
  methods: {
    hasPermission(code) {
      return this.$store.getters.permission.includes(code);
    }
  }
}
</script>
