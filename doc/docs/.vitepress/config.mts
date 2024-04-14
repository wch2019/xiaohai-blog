import {defineConfig} from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
    // 忽略死链接
    ignoreDeadLinks: true,
    // 更新时间
    lastUpdated: true,
    title: "DotCode",
    description: "DotCode(点码），是一个前后分离的博客系统。",
    themeConfig: {
        outline: {
            level: [2, 6],
            label: '目录'
        },
        search: {
            provider: 'local'
        },
        logo: '/favicon.ico',
        // https://vitepress.dev/reference/default-theme-config
        nav: [
            {text: '主页', link: '/'},
            {text: '指南', link: '/介绍'}
        ],

        sidebar: [
            {
                text: '简介',
                collapsed: false,
                items: [
                    {text: '介绍', link: '/介绍'},
                    {text: '安装指南',
                        items: [
                            {text: '使用源码部署', link: '/使用源码部署'},
                            {text: '使用 Docker Compose 部署', link: '/使用 Docker Compose 部署'},
                            {text: '使用 Docker 部署', link: '/使用 Docker 部署'},
                        ]
                    },
                    {text: '初始化', link: '/初始化'},
                ]
            },
            {
                text: '用户指南',
                collapsed: false,
                items: [
                    {text: '基础说明', link: '/基础说明'},
                ]
            },
            {
                text: '关于文档',
                collapsed: false,
                link: '/关于文档'
            }
        ],
        socialLinks: [
            {icon: 'github', link: 'https://github.com/wch2019/xiaohai-blog'}
        ],
        footer: {
            message: 'Released under the <a href="https://github.com/wch2019/xiaohai-blog/blob/master/LICENSE">Apache License 2.0',
            copyright: 'Copyright © 2023-present xiaohai'
        }
    }
})
