package com.xiaohai.common.utils;

import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.time.LocalDateTime;
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
        // 获取文件名称
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        postData.put("title", fileName.replace(".md", ""));
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String frontMatter = "";
            int frontMatterCount = 0;
            StringBuilder contentBuilder = new StringBuilder();

            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {

                if (line.equals("---")) {
                    frontMatterCount++;
                    frontMatter = line;
                }
                if (frontMatterCount < 2 && frontMatter.equals("---")) {
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
                    String fileExtension = FileUtil.getFileExtension(entry.getName());

                    // 判断文件后缀名是否为图片类型类型
                    if (!FileUtil.isImageExtension(fileExtension) && !fileExtension.equals(FileConstants.MARKDOWN_EXTENSION)) {
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
                        if (FileUtil.getFileExtension(file.getName()).equals(FileConstants.MARKDOWN_EXTENSION)) {
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
     * 获取markdown中图片地址，将http网络数据排除
     *
     * @param markdown markdown文本
     * @return java.util.List<java.lang.String>
     * @author xiaohai
     * @since 2023/7/9 13:28
     */
    public static List<String> photoList(String markdown) {
        List<String> list = new ArrayList<>();
        //        String pattern = "!\\[.*?\\]\\((.*?)\\)";
        String pattern = "!\\[.*?\\]\\((?!http)(.*?)\\)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(markdown);
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            list.add(imageUrl);
        }
        return list.stream().distinct().toList();
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
     * @param sourcePath      文件
     * @param targetDirectory 位置
     * @return java.lang.String
     * @author xiaohai
     * @since 2023/7/9 15:01
     */
    public static String copyImage(String sourcePath, String targetDirectory) {
        // 获取文件后缀名
        String fileExtension = FileUtil.getFileExtension(sourcePath);
        // 生成唯一的文件名
        String fileName = FileUtil.generateUniqueFileName(fileExtension);
        // 构建目标文件路径
        String targetPath = Paths.get(targetDirectory, fileName).toString();
        try {
            // 复制文件并重命名
            Files.copy(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            log.info("文件复制成功。");
        } catch (IOException e) {
            throw new RuntimeException("复制文件时出错：" + e.getMessage(), e);
        }
        return targetPath;
    }

    /**
     * 组装一个 Markdown 文件的描述头（Front Matter）
     *
     * @param title      标题
     * @param date       建立日期
     * @param updated    更新日期
     * @param tags       标签
     * @param categories 分类
     * @param cover      封面
     * @param original   转载地址
     * @return java.lang.String
     * @author xiaohai
     * @date 2023/12/4 15:12
     */
    public static String buildMarkdownHeader(String title, LocalDateTime date, LocalDateTime updated, List<String> tags, String categories, String cover, String original) {
        StringBuilder header = new StringBuilder();
        header.append("---\n");
        header.append("title: ").append(title).append("\n");
        header.append("date: ").append(DateUtils.formatDateTime(date)).append("\n");
        header.append("updated: ").append(DateUtils.formatDateTime(updated)).append("\n");

        if (tags != null && !tags.isEmpty()) {
            header.append("tags: [");
            for (int i = 0; i < tags.size(); i++) {
                header.append(tags.get(i));
                if (i < tags.size() - 1) {
                    header.append(",");
                }
            }
            header.append("]\n");
        }

        header.append("categories: ").append(categories).append("\n");
        header.append("cover: ").append(cover).append("\n");
        header.append("original: ").append(original).append("\n");
        header.append("---\n");

        return header.toString();
    }

    /**
     * 创建一个MarkDown
     *
     * @param fileName 文件路径
     * @param content  文本内容
     * @author xiaohai
     * @date 2023/12/04 17:54:58
     */
    public static void createMarkdownFile(String fileName, String content) {
        try {
            // Specify the file path
            Path filePath = Paths.get(fileName);
            // 如果文件已存在，替换文件
            Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            log.info("Markdown文件创建成功：" + fileName);
        } catch (IOException e) {
            throw new ServiceException("Markdown文件创建失败", e);
        }
    }

    public static void main(String[] args) {
        //        String markdown = "这是一段Markdown文章，其中包含图片：\n\n![图片1](../images/1684113802808.jpg)\n\n![图片2](../images/16841138028.jpg)![1](https://raw.githubusercontent.com/xiaohai-store/notes-img/main/1.png)";
        //        System.out.println(photoList(markdown));
        String filePath = "Y:\\files\\blog\\dev\\files\\1\\markdown\\temporary\\0b0329b2d35642f68f19d76cb50dfdce\\note\\Java针对MultipartFile上传图片获取宽、高.md";
        Map<String, Object> postData = parseHexoPost(filePath);

        // 打印博文数据
        for (Map.Entry<String, Object> entry : postData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        String title = "Java单文件下载与打包zip文件下载";
        List<String> tags = List.of("Java", "SpringBoot");
        String categories = "Java技术";
        String cover = "ccccccccccccccc";
        String originalUrl = "ccccccccccccccc";
        String markdownHeader = buildMarkdownHeader(title, LocalDateTime.now(), LocalDateTime.now(), tags, categories, cover, originalUrl);
        System.out.println(markdownHeader);
    }
}
