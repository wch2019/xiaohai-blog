package com.xiaohai.common.utils;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.exception.ServiceException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * <p>
 * Markdown文件处理
 * </p>
 *
 * @author xiaohai
 * @since 2023-07-08 07:12
 **/
@Slf4j
public class MarkdownUtils {

    /**
     * 将指定md进行解析处理
     *
     * @param filePath 文件目录
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author xiaohai
     * @since 2023/7/9 0:37
     */
    public static Map<String, Object> parseHexoPost(String filePath) {
        Map<String, Object> postData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int frontMatterCount = 0;
            StringBuilder contentBuilder = new StringBuilder();

            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("---")) {
                    frontMatterCount++;
                }
                if (frontMatterCount < 2 || line.equals("---")) {
                    // 解析Front Matter的属性
                    Pattern pattern = Pattern.compile("(\\w+)\\s*:\\s*(.*)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.matches()) {
                        String key = matcher.group(1);
                        String value = matcher.group(2);
                        // 处理分类categories属性仅为一个
                        if (key.equals("categories")) {
                            if (value.startsWith("[") && value.endsWith("]")) {
                                value = value.substring(1, value.length() - 1);
                            }
                            postData.put(key, value);
                        }
                        // 处理标签tags属性为列表数据
                        if (key.equals("tags")) {
                            List<String> tags = parseTags(value);
                            postData.put(key, tags);
                        } else {
                            postData.put(key, value);
                        }
                    }
                } else {
                    contentBuilder.append(line).append("\n");
                }
            }
            // 保存正文内容
            String content = contentBuilder.toString().trim();
            postData.put("content", content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return postData;
    }

    /**
     * 对标签进行list处理
     *
     * @param tagsStr 字符串
     * @return java.util.List<java.lang.String>
     * @author xiaohai
     * @since 2023/7/9 0:36
     */
    private static List<String> parseTags(String tagsStr) {
        if (tagsStr.startsWith("[") && tagsStr.endsWith("]")) {
            tagsStr = tagsStr.substring(1, tagsStr.length() - 1);
        }
        return Arrays.stream(tagsStr.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * zip解压 md文件解析
     *
     * @param srcFile     zip源文件
     * @param destDirPath 解压后的目标文件夹
     * @throws RuntimeException 解压失败会抛出运行时异常
     * @author xiaohai
     * @since 2023/7/9 0:33
     */

    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new RuntimeException(srcFile.getPath() + "所指文件不存在");

        }
        // 开始解压
        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(srcFile, Charset.forName("GBK"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                log.info("{}", entry.getName());

                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    String dirPath = destDirPath + "/" + entry.getName();
                    File dir = new File(dirPath);
                    boolean created = dir.mkdirs();

                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(destDirPath + "/" + entry.getName());
                    // 获取文件后缀名
                    String fileExtension = FileUtils.getFileExtension(entry.getName());

                    // 判断文件后缀名是否为图片类型类型
                    if (!FileUtils.isImageExtension(fileExtension) && !fileExtension.equals(Constants.MARKDOWN_EXTENSION)) {
                        throw new ServiceException("请查看上传文件格式是否正确");
                    }
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        boolean created = targetFile.getParentFile().mkdirs();
                    }
                    boolean created = targetFile.createNewFile();

                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];

                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);

                    }
                    // 关流顺序，先打开的后关闭
                    fos.close();
                    is.close();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("解压完成，耗时：" + (end - start) + " ms");

        } catch (Exception e) {
            throw new ServiceException("请查看上传文件格式是否正确", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取指定文件夹下所有md文件列表
     *
     * @param folderPath 文件目录
     * @return java.util.List<java.lang.String>
     * @author xiaohai
     * @since 2023/7/9 8:50
     */
    public static List<String> fileList(String folderPath) {
        List<String> list = new ArrayList<>();

        File folder = new File(folderPath);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        log.info("文件夹：" + file.getName());
                    } else {
                        log.info("文件：" + file.getName());
                        // md文件才获取
                        if (FileUtils.getFileExtension(file.getName()).equals(Constants.MARKDOWN_EXTENSION)) {
                            list.add(file.getPath());
                        }
                    }
                }
            } else {
                log.error("文件夹是空的.");
            }
        } else {
            log.error("指定路径不是一个文件夹.");
        }
        return list;
    }

    /**
     * 获取markdown中图片地址
     *
     * @param markdown markdown文本
     * @return java.util.List<java.lang.String>
     * @author xiaohai
     * @since 2023/7/9 13:28
     */
    public static List<String> photoList(String markdown) {
        List<String> list = new ArrayList<>();
        String pattern = "!\\[.*?\\]\\((.*?)\\)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(markdown);
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            list.add(imageUrl);
        }
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 获取指定字段长度文本
     *
     * @param text      文本
     * @param maxLength 最大长度
     * @return java.lang.String
     * @author xiaohai
     * @since 2023/7/9 14:25
     */
    public static String truncateText(String text, int maxLength) {
        if (text.length() <= maxLength) {
            return text;
        } else {
            return text.substring(0, maxLength);
        }
    }

    /**
     * 将图片复制到指定位置并重命名
     *
     * @param file    文件
     * @param newPath 位置
     * @return java.lang.String
     * @author xiaohai
     * @since 2023/7/9 15:01
     */
    public static String copyImage(String file, String newPath) {
        // 获取文件后缀名
        String fileExtension = FileUtils.getFileExtension(file);
        // 生成唯一的文件名
        String fileName = FileUtils.generateUniqueFileName(fileExtension);
        newPath = newPath + fileName;
        try {
            Path sourcePath = Path.of(file);
            Path targetPath = Path.of(newPath);
            // 复制文件并重命名
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newPath;
    }

    public static void main(String[] args) {
        String markdown = "这是一段Markdown文章，其中包含图片：\n\n![图片1](../images/1684113802808.jpg)\n\n![图片2](../images/1684113802808.jpg)";
        System.out.println(photoList(markdown));
        String filePath = "C:\\Users\\Code01\\Desktop\\新建文件夹\\note\\Chrome书签手动同步方法.md";
        Map<String, Object> postData = parseHexoPost(filePath);

        // 打印博文数据
        for (Map.Entry<String, Object> entry : postData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
