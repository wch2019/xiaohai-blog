<template>
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children,item) && (!onlyOneChild.children||onlyOneChild.noShowingChildren)&&!item.alwaysShow">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{'submenu-title-noDropdown': !isNest}">
          <item :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)" :title="onlyOneChild.meta.title" />
        </el-menu-item>
      </app-link>
    </template>
    <el-submenu v-else :index="resolvePath(item.path)" popper-append-to-body :class="{'custom-submenu': shouldHideTitle(resolvePath(item.path))}">
      <template slot="title">
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="item.meta.title" />
      </template>
      <sidebar-item
        v-for="child in item.children"
        :key="child.path"
        :is-nest="true"
        :item="child"
        :base-path="resolvePath(child.path)"
        class="nest-menu"
      />
    </el-submenu>
  </div>
</template>

<script>
import path from 'path'
import { isExternal } from '@/utils/validate'
import Item from './Item'
import AppLink from './Link'
import FixiOSBug from './FixiOSBug'

export default {
  name: 'SidebarItem',
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    // route object
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    // To fix https://github.com/PanJiaChen/vue-admin-template/issues/237
    // TODO: refactor with render function
    this.onlyOneChild = null
    return {}
  },
  methods: {
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false
        } else {
          // Temp set(will be used if only has one showing child)
          // 临时设置(将在只有一个显示子节点时使用)
          this.onlyOneChild = item
          return true
        }
      })
      // When there is only one child router, the child router is displayed by default
      // 当只有一个子路由器时，默认显示该子路由器
      if (showingChildren.length === 1) {
        return true
      }

      // Show parent if there are no child router to display
      // 如果没有要显示的子路由器，则显示父路由器
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: '', noShowingChildren: true }
        return true
      }
      return false
    },
    shouldShowOnlyOneChild(item) {
      return this.hasOneShowingChild(item.children, item) && (!this.onlyOneChild.children || this.onlyOneChild.noShowingChildren) && !item.alwaysShow
    },
    resolvePath(routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      // console.log(this.basePath, routePath, 'routePath')

      if (this.basePath.endsWith(routePath)) {
        // 基础
        return path.resolve(this.basePath, '')
      } else {
        // 更多
        return path.resolve(this.basePath, routePath)
      }
    },
    shouldHideTitle(index) {
      return index === '/basic' || index === '/more'
    }
  }
}
</script>
<style lang="scss" scoped>
::v-deep .custom-submenu > .el-submenu__title {
  //display: none;
}
</style>
