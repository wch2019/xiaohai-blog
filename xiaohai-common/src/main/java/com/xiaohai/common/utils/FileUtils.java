package com.xiaohai.common.utils;

import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
     * 获取文件的后缀名
     *
     * @param fileName 文件名
     * @return 文件后缀名
     */
    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        throw new ServiceException("无效的文件名");
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
     * 删除指定目录下所有文件
     *
     * @param folder 文件
     * @author xiaohai
     * @since 2023/7/9 9:14
     */
    public static void deleteFiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFiles(file); // 递归调用删除子目录中的文件
                    file.delete();// 删除文件夹
                } else {
                    file.delete(); // 删除文件
                }
            }
        }
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
     *
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

    /**
     * 文件下载到指定路径
     *
     * @param urlString 链接
     * @param savePath  保存路径
     * @param filename  文件名
     */
    public static void download(String urlString, String savePath, String filename) {
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为20s
            con.setConnectTimeout(20 * 1000);
            //文件路径不存在 则创建
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            //jdk 1.7 新特性自动关闭
            try (InputStream in = con.getInputStream();
                 OutputStream out = new FileOutputStream(sf.getPath() + File.separator + filename)) {
                //创建缓冲区
                byte[] buff = new byte[1024];
                int n;
                // 开始读取
                while ((n = in.read(buff)) >= 0) {
                    out.write(buff, 0, n);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断文件后缀名是否为图片类型
     *
     * @param fileExtension 文件后缀名
     * @return 是否为图片类型
     */
    public static boolean isImageExtension(String fileExtension) {
        return Arrays.asList(FileConstants.IMAGE_EXTENSION).contains(fileExtension);
    }

    /**
     * 判断文件后缀名是否为压缩类型
     *
     * @param fileExtension 文件后缀名
     * @return 是否为压缩类型
     */
    public static boolean isCompressExtension(String fileExtension) {
        return Arrays.asList(FileConstants.COMPRESS_EXTENSION).contains(fileExtension);
    }

    /**
     * 生成唯一的文件名
     *
     * @param fileExtension 文件后缀名
     * @return 唯一的文件名
     */
    public static String generateUniqueFileName(String fileExtension) {
        String fileName = StringUtils.generateUUIDWithoutHyphens();
        return fileName + "." + fileExtension;
    }

    /**
     * 保存文件到指定路径
     *
     * @param path     文件保存路径
     * @param fileName 文件名
     * @param file     要保存的文件
     * @return 文件保存路径
     */
    public static String saveFile(String path, String fileName, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            File savedFile = new File(path, fileName);
            FileUtils.copyInputStreamToFile(inputStream, savedFile);
            return savedFile.getPath();
        } catch (IOException e) {
            throw new ServiceException("文件保存失败", e);
        }
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\Code01\\Pictures\\图层 1.png";
        File file = new File(filePath);
        long fileSize = file.length();
        System.out.println("文件大小：" + formatFileSize(fileSize));
        System.out.println("文件创建时间：" + fileCreationTime(filePath));

    }
}
