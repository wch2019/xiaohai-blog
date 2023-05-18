## DotCode(点码）
<p align=center>
  <a href="">
    <img src="../xiaohai-blog/xiaohai-web/public/favicon.ico" alt="DotCode" style="border-radius: 50%">
  </a>
</p>

<p align="center">
   <a target="_blank" href="https://github.com/wch2019">
      <img src="https://img.shields.io/hexpm/l/plug.svg"/>
      <img src="https://img.shields.io/badge/JDK-17+-green.svg"/>
      <img src="https://img.shields.io/badge/springboot-2.7.7-green"/>
      <img src="https://img.shields.io/badge/vue-2.6.10-green"/>
      <img src="https://img.shields.io/badge/mysql-8.0+-green"/>
      <img src="https://img.shields.io/badge/mybatis--plus-3.5.3.1-green"/>
      <img src="https://img.shields.io/badge/redis-6.0.5-green"/>
   </a>
</p>

## 前言

​		本着不想造轮子的理念用过**halo**和**hexo**，见过 **[蘑菇博客](https://gitee.com/moxi159753/mogu_blog_v2)**和**[拾壹博客](https://gitee.com/quequnlong/shiyi-blog)**。别人的用着终究不是很顺手。最终觉得还是自己写个用也是不错的，就当做练手，也是为了折腾。在2023年初开始本项目的开发，开始打算简单写写能用就行，不过最后感觉既然都写了，那就多写点东西吧。目的打造一个**笔记+博客+网盘**的项目，现在还没完全构思好，边写边构思。(本项目还未完成，持续更新中，如果有想法的小伙伴也可以分享自己的看法)

##  项目介绍

DotCode(点码），是一个前后分离的博客系统。

前端使用 **Vue** + **ElementUi** 和**Vue 3 + TypeScript + Vite + ElementPlus**

后端使用 **SpringBoot** + **Mybatis-plus**进行开发，使用**Sa-Token**作为登录验证和权限校验。

## 项目特点

- 后台采用父子包的方式以及友好的代码结构及注释，便于阅读及二次开发

- 实现前后端分离，通过 **Json** 进行数据交互，前端再也不用关注后端技术

- 页面交互，后台管理使用 **Vue2.x**，门户展示采用**Vue3**。

- 引入 **RBAC** 权限管理设计，灵活的权限控制，按钮级别的细粒度权限控制，满足绝大部分的权限需求

- 采用**Markdown** 编辑器([mavonEditor](www.mavoneditor.com)），更符合开发者的编辑方式

  
