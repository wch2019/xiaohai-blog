# Vue 3 + TypeScript + Vite + ElementPlus

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

## Recommended IDE Setup

- [VS Code](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

## Type Support For `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin) to make the TypeScript language service aware of `.vue` types.

If the standalone TypeScript plugin doesn't feel fast enough to you, Volar has also implemented a [Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669) that is more performant. You can enable it by the following steps:

1. Disable the built-in TypeScript Extension
   1. Run `Extensions: Show Built-in Extensions` from VSCode's command palette
   2. Find `TypeScript and JavaScript Language Features`, right click and select `Disable (Workspace)`
2. Reload the VSCode window by running `Developer: Reload Window` from the command palette.

```
  ├── publish/
  └── src/
  ├── assets/                    // 静态资源目录
  ├── common/                    // 通用类库目录
  ├── components/                // 公共组件目录
  ├── router/                    // 路由配置目录
  ├── store/                     // 状态管理目录
  ├── style/                     // 通用 CSS 目录
  ├── utils/                     // 工具函数目录
  ├── views/                     // 页面组件目录
  ├── App.vue
  ├── main.ts
  ├── shims-vue.d.ts
  ├── tests/                         // 单元测试目录
  ├── index.html
  ├── tsconfig.json                  // TypeScript 配置文件
  ├── vite.config.ts                 // Vite 配置文件
  └── package.json
```

# 格式化所有文件（. 表示所有文件）

npx prettier --write .
