@echo off
chcp 65001  > nul 2>&1

rem 指定您的 Maven 可执行文件的路径
set MAVEN_HOME=D:\SoftWare\apache-maven-3.9.2

rem 指定项目的根目录
set PROJECT_DIR=D:\Project\gitee\xiaohai-blog

rem 进入项目目录
cd %PROJECT_DIR%

echo 正在执行 Maven 清空命令...
rem 执行 Maven 清空命令
call %MAVEN_HOME%\bin\mvn clean
if %errorlevel%==0 (
  echo Maven 清空成功！
) else (
   echo Maven 清空失败。错误代码：%errorlevel%
    pause
    exit /b %errorlevel%
)

echo 正在执行 Maven 打包命令...

rem 执行 Maven 打包命令
call %MAVEN_HOME%\bin\mvn clean package -Dmaven.test.skip=true

rem 检查打包命令是否成功
if %errorlevel%==0 (
  echo Maven 打包成功！
) else (
  echo Maven 打包失败。错误代码：%errorlevel%
)

pause
