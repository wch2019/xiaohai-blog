@echo off
chcp 65001  > nul 2>&1

rem 设置 Java 可执行文件的路径
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.3.1
set PATH=%JAVA_HOME%\bin;%PATH%
rem 设置 JAR 文件的完整路径
set JAR_FILE=D:\Project\gitee\xiaohai-blog\xiaohai-admin\target\xiaohai-blog.jar

rem 设置日志文件夹路径
set LOG_FOLDER=D:\Project\gitee\xiaohai-blog\xiaohai-admin\target\logs

rem 设置YML配置文件路径
set YML_FILE=--spring.config.additional-location=D:\Project\gitee\xiaohai-blog\doc\logs\application.yml

rem 设置 Java 启动参数 添加启动参数--initDatabase执行数据库初始化,注意进初始化使用该命令
set JAVA_OPTS=

rem 获取当前日期和时间（格式：YYYYMMDD_HHMMSS）
for /f "tokens=2-4 delims=/ " %%a in ('date /t') do (
    set "year=%%a"
    set "month=%%b"
    set "day=%%c"
)
for /f "tokens=1-2 delims=: " %%a in ('time /t') do (
    set "hour=%%a"
    set "minute=%%b"
)
set "dateTime=%year%%month%%day%%hour%%minute%"

rem 创建日志文件夹
if not exist "%LOG_FOLDER%" mkdir "%LOG_FOLDER%"

rem 设置日志文件路径
set LOG_FILE=%LOG_FOLDER%\xiaohai-blog_%dateTime%.log

rem 执行自关闭窗口脚本
%1 mshta vbscript:CreateObject("WScript.Shell").Run("%~s0 ::",0,FALSE)(window.close)&&exit

rem 执行 Java 应用程序
java -Dfile.encoding=UTF-8  -jar "%JAR_FILE%"  %YML_FILE%  %JAVA_OPTS% > "%LOG_FILE%" 2>&1

