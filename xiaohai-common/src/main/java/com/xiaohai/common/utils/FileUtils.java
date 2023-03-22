package com.xiaohai.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 文件操作工具类
 * @author: xiaohai
 * @date: 2023-03-18 09:39
 **/
public class FileUtils extends org.apache.commons.io.FileUtils {
    /**
     * 获取文件创建时间的方法
     *
     * @param filePath
     * @return
     */
    public static String fileCreationTime(String filePath) {
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            FileTime createTime = attrs.creationTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(new Date(createTime.toMillis()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件大小并转换为KB、MB、GB等格式
     *
     * @param fileSize
     * @return
     */
    public static String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        String extension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1);
        }
        return extension;
    }

    /**
     * 删除文件或者目录
     *
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            //判断当前文件夹或文件是否存在
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建目录
     *
     * @param dir
     * @return
     */
    public static boolean directory(String dir) {
        File file = new File(dir);
        //判断当前文件夹或文件是否存在
        if (!file.exists()) {
            return file.mkdir();
        }
        return false;
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static boolean createNewFile(String filePath) {
        try {
            File file = new File(filePath);
            //判断当前文件夹或文件是否存在
            if (!file.exists()) {
                return file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\Code01\\Pictures\\图层 1.png";
        File file = new File(filePath);
        long fileSize = file.length();
        System.out.println("文件大小：" + formatFileSize(fileSize));
        System.out.println("文件创建时间：" + fileCreationTime(filePath));
    }
}
