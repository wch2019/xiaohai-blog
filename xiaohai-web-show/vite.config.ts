import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// 如果编辑器提示 path 模块找不到，则可以安装一下 @types/node -> npm i @types/node -D
import { resolve } from 'path'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons' // 版本不同引入方式不同

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    createSvgIconsPlugin({
      // 指定需要缓存的图标文件夹
      iconDirs: [resolve(process.cwd(), 'src/assets/icons/svg')],
      // 指定symbolId格式
      symbolId: 'icon-[dir]-[name]'
    })
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src') // 设置 `@` 指向 `src` 目录
    }
  },
  base: '/', // 设置打包路径
  server: {
    host: '0.0.0.0', // 设置host访问
    port: 4000, // 设置服务启动端口号
    open: false, // 设置服务启动时是否自动打开浏览器
    cors: true, // 允许跨域

    // 设置代理，根据我们项目实际情况配置
    proxy: {
      '/api': {
        target: 'http://localhost:8089',
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace('/^/api/', '/')
      }
    }
  }
})
