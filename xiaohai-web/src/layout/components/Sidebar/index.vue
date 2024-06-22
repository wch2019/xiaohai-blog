<template>
  <div
    :class="{'has-logo':showLogo}"
    :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }"
  >
    <logo v-if="showLogo" :collapse="isCollapse" />

    <el-scrollbar :class="settings.sideTheme" style="height: calc(100vh - 195px);" wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :unique-opened="true"
        :collapse-transition="false"
        :default-openeds="defaultOpeneds"
        mode="vertical"
      >
        <sidebar-item
          v-for="route in processedPermissionRoutes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
    <Avatar :collapse="isCollapse" @operation="operation" />
  </div>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import Logo from './Logo'
import Avatar from './Avatar.vue'
import SidebarItem from './SidebarItem'
import variables from '@/styles/variables.scss'

export default {
  components: { SidebarItem, Logo, Avatar },
  data() {
    return {
      defaultOpeneds: ['/basic', '/more'],
      operationValue: 'Basic'
    }
  },
  computed: {
    ...mapState(['settings']),
    ...mapGetters([
      'permission_routes',
      'sidebar'
    ]),
    // 新的计算属性，根据指定规则处理 permission_routes
    processedPermissionRoutes() {
      // 获取原始的 permission_routes
      const routes = this.permission_routes
      // 根据指定规则将其转换为数组
      // 这里假设规则是将 routes 对象的键值对转换为数组元素
      const processedRoutesBasic = []
      const processedRoutesMore = []
      for (const key in routes) {
        if (Object.prototype.hasOwnProperty.call(routes, key)) {
          // 存在不展示的直接跳过
          if (routes[key].hidden) {
            continue
          }
          if (routes[key].redirect === '/dashboard') {
            console.log('processedRoutesBasic', routes[key])
            processedRoutesBasic.push({
              name: key,
              ...routes[key]
            })
          }
          if (routes[key].name === 'Basic') {
            for (const routeBasic of routes[key].children) {
              processedRoutesBasic.push({
                path: routes[key].path + '/' + [routeBasic][0].path,
                redirect: routes[key].redirect,
                component: routes[key].component,
                children: [routeBasic]
              })
            }
          }
          if (routes[key].name === 'More') {
            for (const routeMore of routes[key].children) {
              processedRoutesMore.push({
                path: `${routes[key].path}/${routeMore.path}`,
                hidden: routeMore.hidden,
                redirect: routeMore.redirect,
                component: routes[key].component,
                alwaysShow: routeMore.alwaysShow,
                meta: routeMore.meta,
                children: routeMore.children
              })
            }
          }
        }
      }
      return this.operationValue === 'Basic' ? processedRoutesBasic : processedRoutesMore
    },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  methods: {
    operation(value) {
      this.operationValue = value
    }
  }
}
</script>
