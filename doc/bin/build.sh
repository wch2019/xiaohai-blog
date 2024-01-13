#!/bin/bash

# 指定您的 Maven 可执行文件的路径
MAVEN_HOME=/path/to/apache-maven-3.9.2

# 指定项目的根目录
PROJECT_DIR=/path/to/your/project

# 进入项目目录
cd "$PROJECT_DIR"

echo "正在执行 Maven 清空命令..."
# 执行 Maven 清空命令
"$MAVEN_HOME"/bin/mvn clean
if [ $? -eq 0 ]; then
  echo "Maven 清空成功！"
else
  echo "Maven 清空失败。错误代码：$?"
  exit $?
fi

echo "正在执行 Maven 打包命令..."
# 执行 Maven 打包命令
"$MAVEN_HOME"/bin/mvn clean package -Dmaven.test.skip=true

# 检查打包命令是否成功
if [ $? -eq 0 ]; then
  echo "Maven 打包成功！"
else
  echo "Maven 打包失败。错误代码：$?"
fi

read -p "Press enter to exit"
