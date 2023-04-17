FROM openjdk:17.0.3.1

# 作者
MAINTAINER wangchenghai

# 配置环境变量
ENV JAVA_HOME=/usr/local/jdk-17.0.3.1
ENV PATH=$JAVA_HOME/bin:$PATH
ENV CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

# 添加jar到镜像并命名为server.jar
ADD server-0.0.1-SNAPSHOT.jar server.jar

# 镜像启动后暴露的端口
EXPOSE 18888

# jar运行命令，参数使用逗号隔开
ENTRYPOINT ["java","-jar","server-0.0.1.jar"]

# 设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

