FROM nginx:stable-alpine

# 作者
MAINTAINER xiaohai

# 管理页
COPY xiaohai-web /usr/share/nginx/html/xiaohai-web
# 展示页
COPY xiaohai-web-show /usr/share/nginx/html/xiaohai-web-show

# 复制 Nginx 配置文件到容器中
COPY nginx/nginx.conf /etc/nginx/nginx.conf
COPY nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf

# 暴露 Nginx 默认端口
EXPOSE 80

# 启动 Nginx 服务
CMD ["nginx", "-g", "daemon off;"]

