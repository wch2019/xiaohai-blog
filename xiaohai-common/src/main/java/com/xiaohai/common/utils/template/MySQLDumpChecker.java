package com.xiaohai.common.utils.template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class MySQLDumpChecker {
    /**
     * 备份mysql数据库
     * @param username 账号
     * @param pwd 密码
     * @param url 地址
     * @param path 路径
     * @param tableName 数据库名 表名
     * @throws Exception
     */
    public static void dbBackUpMysql(String username,String pwd,String url,String path,String tableName) throws Exception {
        //mysqldump -h 127.0.0.1 -uroot -proot mysql user >D:/info/server/var/backupdata/backups.sql
        String dbName = tableName;
        String pathSql = path+tableName+".sql";
        File fileSql = new File(pathSql);
        File filePath = new File(path);
        //创建备份sql文件
        if (!filePath.exists()){
            filePath.mkdirs();
        }
        if (!fileSql.exists()){
            fileSql.createNewFile();
        }
        //mysqldump -hlocalhost -uroot -p123456 db > /home/back.sql
        StringBuffer sb = new StringBuffer();
        sb.append("mysqldump ");
        sb.append(" -h"+url);
        sb.append(" -u"+username);
        sb.append(" -p"+pwd);
        sb.append(" "+dbName+" >");
        sb.append(pathSql);
        System.out.println("cmd命令为："+sb.toString());
        System.out.println("开始备份："+dbName);
        Process process = null;
        //判断操作系统 windwos与linux使用的语句不一样
        if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1){
            process = Runtime.getRuntime().exec("cmd /c"+sb.toString());
        }else if(System.getProperty("os.name").toLowerCase().indexOf("linux") > -1){
            process = Runtime.getRuntime().exec("/bin/sh -c"+sb.toString());
        }else{
            throw new Exception("暂不支持该操作系统，进行数据库备份或还原！");
        }
        //设置超时一分钟
        process.waitFor(60000, TimeUnit.MILLISECONDS);
        //输出返回的错误信息
        StringBuffer mes = new StringBuffer();
        String tmp = "";
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.ISO_8859_1));
        while((tmp = error.readLine()) != null){
            mes.append(tmp + "\n");
        }
        if(mes != null || !"".equals(mes) ){
            System.out.println("备份成功!==>"+mes.toString());
        }
        error.close();
    }

    /**
     * 数据库还原
     * @param username 账号
     * @param pwd 密码
     * @param url 地址
     * @param path 文件存放路径
     * @param tableName 数据库名 表名
     * @throws Exception
     */
    public static void dbRestoreMysql(String username,String pwd,String url,String path,String tableName) throws Exception{
        //mysql -hlocalhost -uroot -proot db < /home/back.sql
        String dbName = "mysql";
        StringBuilder sb = new StringBuilder();
        sb.append("mysql");
        sb.append(" -h"+url);
        sb.append(" -u"+username);
        sb.append(" -p"+pwd);
        sb.append(" "+dbName+" <");
        sb.append(path+tableName+".sql");
        System.out.println("cmd命令为："+sb.toString());
        Process process = null;
        //判断操作系统 windwos与linux使用的语句不一样
        if(System.getProperty("os.name").toLowerCase().indexOf("windows") > -1){
            process = Runtime.getRuntime().exec("cmd /c"+sb.toString());
        }else if(System.getProperty("os.name").toLowerCase().indexOf("linux") > -1){
            process = Runtime.getRuntime().exec("/bin/sh -c"+sb.toString());
        }else{
            throw new Exception("暂不支持该操作系统，进行数据库备份或还原！");
        }
        System.out.println("开始还原数据");
        InputStream is = process.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(is,"utf8"));
        String line = null;
        while ((line=bf.readLine())!=null){
            System.out.println(line);
        }
        is.close();
        bf.close();
        System.out.println("还原成功！");
    }


    public static void main(String[] args) throws Exception {
        dbBackUpMysql("root","123456","127.0.0.1","C:\\Users\\wangchenghai\\Downloads\\backup\\","xiaohai_blog");
//        if (isMySQLDumpAvailable()) {
//            System.out.println("mysqldump is available.");
//        } else {
//            System.out.println("mysqldump is not available.");
//        }
    }
}
