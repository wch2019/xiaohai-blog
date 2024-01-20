import {defineConfig} from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
    ignoreDeadLinks: true,
    title: "DotCode",
    description: "DotCode(点码），是一个前后分离的博客系统。",
    themeConfig: {
        logo: './image/favicon.ico',
        // https://vitepress.dev/reference/default-theme-config
        nav: [
            {text: '主页', link: '/'},
            {text: '指南', link: '/介绍'}
        ],

        sidebar: [
            {
                text: '简介',
                items: [
                    {text: '介绍', link: '/介绍'},
                    {text: '安装指南',
                        items: [
                            {text: '使用源码部署', link: '/使用源码部署'},
                            {text: '使用 Docker Compose 部署', link: '/使用 Docker Compose 部署'},
                            {text: '使用 Docker 部署', link: '/使用 Docker 部署'},
                        ]
                    },
                ]
            },
            {
                text: '用户指南',
                items: [
                    {text: '基础说明', link: '/基础说明'},
                ]
            },
            {
                text: '关于文档',
                link: '/关于文档'
            }
        ],

        socialLinks: [
            {icon: 'github', link: 'https://github.com/vuejs/vitepress'}
        ]
    }
})
