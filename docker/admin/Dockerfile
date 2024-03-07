FROM openjdk:17.0.2

# 作者
MAINTAINER xiaohai

WORKDIR /app

# 添加jar到镜像并命名为xiaohai-blog.jar
ADD xiaohai-blog.jar xiaohai-blog.jar
# 添加yml
ADD application.yml application.yml

# 镜像启动后暴露的端口
EXPOSE 8089

# jar运行命令，参数使用逗号隔开
ENTRYPOINT ["java","-jar","xiaohai-blog.jar","--spring.config.additional-location=application.yml"]

# 设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

