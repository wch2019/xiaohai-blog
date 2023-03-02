<template>
  <div class="icon-body">
    <el-input
      v-model="name"
      style="position: relative;"
      placeholder="请输入图标名称"
      @input.native="filterIcons"
    >
      <i slot="suffix" class="el-icon-search el-input__icon" />
    </el-input>
    <el-tabs type="border-card">
      <el-tab-pane label="Icons">
        <div class="icon-list">
          <div v-for="item of svgIcons" :key="item" @click="selectedIcon(item)">
            <svg-icon :icon-class="item" style="height: 30px;width: 16px;" />
            {{ item }}
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Element-UI Icons">
        <div class="icon-list">
          <div v-for="item of elementIcons" :key="item" @click="selectedIcon('el-icon-'+item)">
            <i :class="'el-icon-' + item" style="height: 30px;width: 16px;" />{{ item }}
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import svgIcons from './svg-icons'
import elementIcons from './element-icons'

export default {
  name: 'IconSelect',
  data() {
    return {
      name: '',
      svgIcons,
      elementIcons
    }
  },
  methods: {
    filterIcons() {
      this.svgIcons = svgIcons
      this.elementIcons = elementIcons
      if (this.name) {
        this.svgIcons = this.svgIcons.filter(item => item.includes(this.name))
        this.elementIcons = this.elementIcons.filter(item => item.includes(this.name))
      }
    },
    selectedIcon(name) {
      this.$emit('selected', name)
      console.log('name',name)
      document.body.click()
    },
    reset() {
      this.svgIcons = svgIcons
      this.elementIcons = elementIcons
      this.name = ''
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.icon-body {
  width: 100%;
  padding: 10px;

  .icon-list {
    height: 200px;
    overflow-y: scroll;

    div {
      height: 30px;
      line-height: 30px;
      margin-bottom: -5px;
      cursor: pointer;
      width: 33%;
      float: left;
    }

    span {
      display: inline-block;
      vertical-align: -0.15em;
      fill: currentColor;
      overflow: hidden;
    }
  }
}
</style>
