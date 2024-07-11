package com.xiaohai.common.utils;

import cn.hutool.core.util.NumberUtil;
import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.daomain.RcAttachmentInfo;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.server.Disk;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * @description: 文件操作工具类
 * @author: xiaohai
 * @date: 2023-03-18 09:39
 **/
@Slf4j
public class FileUtil extends org.apache.commons.io.FileUtils {
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
        if (fileSize == 0) {
            fileSizeString = "0B";
        } else if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else if (fileSize < 1099511627776L) {
            fileSizeString = df.format((double) fileSize / 1073741824) + "GB";
        } else {
            fileSizeString = df.format((double) fileSize / 1099511627776L) + "TB";
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
            } else {
                return true;
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
        Path path = Path.of(dir);
        try {
            // 使用 createDirectories 方法创建目录及其所有不存在的父目录
            Files.createDirectories(path);
            log.info("目录创建成功。");
            return true;
        } catch (FileAlreadyExistsException e) {
            log.error("目录已经存在。");
        } catch (IOException e) {
            log.error("创建目录时出错：" + e.getMessage());
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
     * 通过URL直接转换成MutipartFile
     *
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static MultipartFile getFileFromUrl(String url, String fileName) {
        try {
            // 根据URL创建资源
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(60000);
            connection.setDoOutput(true);
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            DiskFileItem fileItem = (DiskFileItem) fileItemFactory.createItem("file",
                    MediaType.ALL_VALUE, true, fileName);
            fileItem.getOutputStream().flush();
            try (ReadableByteChannel readableByteChannel = Channels.newChannel(connection.getInputStream());
                 OutputStream outputStream = fileItem.getOutputStream();
                 WritableByteChannel writableByteChannel = Channels.newChannel(outputStream)) {
                // 创建字节缓冲区以存储文件内容
                ByteBuffer buffer = ByteBuffer.allocateDirect(1024 << 2);

                // 将文件内容读入字节缓冲区
                while (readableByteChannel.read(buffer) != -1) {
                    // 准备字节缓冲区以进行再次读取
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        writableByteChannel.write(buffer);
                    }
                    buffer.clear();
                }
            } catch (IOException e) {
                // 在这里处理网络或文件IO异常
                log.error("上传文件时发生错误", e);
                // 根据需要处理或记录IOException
            }
            return new CommonsMultipartFile(fileItem);
        } catch (Exception e) {
            // 在这里处理其他异常
            log.error("下载文件时发生错误", e);
            // 根据需要处理或记录其他异常
            return null; // 或者抛出自定义异常
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
        String fileName = StringUtil.generateUUIDWithoutHyphens();
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
        // 参数检查
        if (path == null || fileName == null || file == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path savedFilePath = Paths.get(path, fileName);

            // 使用 Files.copy 替代 FileUtils.copyInputStreamToFile
            Files.copy(inputStream, savedFilePath, StandardCopyOption.REPLACE_EXISTING);

            // 返回保存的文件路径
            return savedFilePath.toString();
        } catch (IOException e) {
            // 捕获并重新抛出异常，添加更多上下文信息
            throw new ServiceException("文件保存失败: " + fileName, e);
        }
    }

    /**
     * 当前目录下是否存在重名文件，并使用其他名称
     *
     * @param path     路径
     * @param fileName 名称
     * @return
     */
    public static String getUniqueFileName(String path, String fileName) {
        // 获取文件名和扩展名
        String baseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);

        // 构造新的文件名
        int counter = 1;
        String uniqueFileName = fileName;
        while (Files.exists(Paths.get(path, uniqueFileName))) {
            // 如果文件已存在，则在文件名末尾添加计数器
            uniqueFileName = String.format("%s_%d.%s", baseName, counter++, extension);
        }

        return uniqueFileName;
    }


    /**
     * 重命名文件路径，如果目标路径已存在则生成新的唯一名称。
     *
     * @param targetPath 要重命名的目标路径
     * @return 返回新的唯一目标路径
     */
    public static String renameFile(String targetPath) {
        Path target = Paths.get(targetPath);

        if (Files.exists(target)) {
            // 如果目标名称已存在，生成新的唯一名称
            int counter = 1;
            String newTargetPath;
            do {
                String fileName = target.getFileName().toString();
                String fileExtension = "";
                int dotIndex = fileName.lastIndexOf(".");
                if (dotIndex != -1) {
                    fileExtension = fileName.substring(dotIndex);
                    fileName = fileName.substring(0, dotIndex);
                }
                // 构造新的唯一文件名
                newTargetPath = fileName + "_" + counter++ + fileExtension;
                // 生成新的路径对象
                target = target.resolveSibling(newTargetPath);
            } while (Files.exists(target));
            // 更新目标路径为新的唯一名称
            targetPath = target.toString();
        }
        // 返回新的目标路径
        return targetPath;
    }

    /**
     * 提取文件 checksum
     *
     * @param fileOrPath 文件或文件全路径
     * @param algorithm  算法名 例如 MD5、SHA-1、SHA-256等
     * @return checksum
     */
    public static String extractChecksum(Object fileOrPath, String algorithm) {
        try {
            // 根据算法名称初始化摘要算法
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] fileBytes;
            if (fileOrPath instanceof MultipartFile multipartFile) {
                // 如果输入是 MultipartFile
                fileBytes = multipartFile.getBytes();
            } else if (fileOrPath instanceof String filePath) {
                // 如果输入是文件路径
                fileBytes = Files.readAllBytes(Paths.get(filePath));
            } else {
                throw new IllegalArgumentException("不支持的文件类型");
            }
            // 摘要更新
            digest.update(fileBytes);
            //完成哈希摘要计算并返回特征值
            byte[] digested = digest.digest();
            // 进行十六进制的输出
            return HexUtils.toHexString(digested);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new ServiceException("无法计算当前文件校验值", e);
        }
    }

    /**
     * 计算指定文件夹下所有文件的哈希值，并将结果存储在Map中。
     *
     * @param folderPath 要计算哈希值的文件夹路径
     * @param algorithm  哈希算法
     * @return 包含哈希值和文件路径映射关系的Map
     */
    public static Map<String, String> calculateHashesInFolder(String folderPath, String algorithm) {
        Map<String, String> hashToPathMap = new HashMap<>();

        // 创建指向指定文件夹的File对象
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            // 获取文件夹中的所有文件
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        // 计算文件的哈希值
                        String hash = extractChecksum(file.getPath(), algorithm);
                        hashToPathMap.put(hash, file.getPath());
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("指定路径不是一个文件夹");
        }
        return hashToPathMap;
    }

    /**
     * 获取图片长宽
     *
     * @param fileOrPath     文件或文件全路径
     * @param attachmentInfo 保存图片属性的对象
     */
    public static void imageProperty(Object fileOrPath, RcAttachmentInfo attachmentInfo) {
        try {
            FileInputStream fileInputStream;
            if (fileOrPath instanceof MultipartFile multipartFile) {
                // 如果输入是 MultipartFile
                fileInputStream = (FileInputStream) multipartFile.getInputStream();
            } else if (fileOrPath instanceof String filePath) {
                // 如果输入是文件路径
                fileInputStream = new FileInputStream(filePath);
            } else {
                throw new IllegalArgumentException("不支持的文件类型");
            }
            BufferedImage bufferedImage = ImageIO.read(fileInputStream);
            fileInputStream.close(); // 记得关闭流

            if (null == bufferedImage) {
                // 证明文件不是图片，获取图片流失败，不进行下面的操作
                attachmentInfo.setWidth(0);
                attachmentInfo.setHeight(0);
                return;
            }
            attachmentInfo.setWidth(bufferedImage.getWidth());
            attachmentInfo.setHeight(bufferedImage.getHeight());
        } catch (Exception e) {
            log.warn("获取图片属性异常:", e);
        }
    }

    /**
     * 对文件名进行修正，确保符合Windows文件命名规则。
     * 如果文件名不符合规则，将不符合规则的字符替换为下划线。
     *
     * @param fileName 原文件名
     * @return 修正后的文件名
     */
    public static String sanitizeFileName(String fileName) {
        // 定义Windows文件命名规则的正则表达式
        String regex = "^(?!^(CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])$)[^<>:\"/\\\\|?*]+$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 验证文件名是否符合Windows文件命名规则
        boolean isValid = pattern.matcher(fileName).matches();

        if (!isValid) {
            // 将不符合规则的字符替换为下划线
            fileName = fileName.replaceAll("[<>:\"/\\\\|?*]", "_");
        }
        return fileName;
    }

    /**
     * 使用replace方法将反斜杠替换为正斜杠
     *
     * @param filePath
     * @return
     */
    public static String normalizeFilePath(String filePath) {
        if (filePath != null) {
            // 使用replace方法将反斜杠替换为正斜杠
            return filePath.replace("\\", "/");
        } else {
            // 如果输入为null，可以根据需要返回空字符串或者抛出异常
            return "";
        }
    }

    /**
     * 使用replace方法将正斜杠替换为系统兼容的斜杠
     *
     * @param filePath
     * @return
     */
    public static String systemFilePath(String filePath) {
        if (filePath != null) {
            // 使用replace方法将正斜杠替换为File.separator
            return filePath.replace("/", File.separator);
        } else {
            // 如果输入为null，可以根据需要返回空字符串或者抛出异常
            return "";
        }
    }

    /**
     * 获取最后一个 "/" 前面的数据
     *
     * @param input
     * @return
     */
    public static String getLastSegmentStart(String input) {
        int lastSlashIndex = input.lastIndexOf("/");

        // 检查是否找到了 "/"
        if (lastSlashIndex != -1) {
            // 使用substring获取最后一个 "/" 前面的数据
            return input.substring(0, lastSlashIndex + 1);
        } else {
            // 如果没有找到 "/", 可以根据实际需求返回空字符串或整个输入字符串
            return "";
        }
    }

    /**
     * 获取路径中最后一个 "/" 后的数据
     *
     * @param path 输入路径
     * @return 最后一个 "/" 后的数据，如果没有 "/" 则返回整个路径
     */
    public static String getLastSegmentEnd(String path) {
        // 使用路径分隔符来查找最后一个斜杠的索引
        int lastSlashIndex = path.lastIndexOf("/");

        if (lastSlashIndex != -1) {
            // 使用substring获取最后一个 "/" 后的数据
            return path.substring(lastSlashIndex + 1);
        } else {
            // 如果没有找到 "/", 返回整个路径
            return path;
        }
    }

    /**
     * 重命名文件或目录路径
     *
     * @param originalPath 原始文件/目录路径
     * @param newPath      新的文件/目录路径
     */
    public static void renamePath(String originalPath, String newPath) {
        // 使用Path对象表示原始路径
        Path original = Paths.get(originalPath);

        // 使用Path对象表示新路径
        Path destination = Paths.get(newPath);

        // 执行路径重命名操作
        try {
            Files.move(original, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("重命名时出错：" + e.getMessage(), e);
        }
    }

    /**
     * 复制文件到指定目录
     *
     * @param sourceFilePath      源文件路径
     * @param targetDirectoryPath 目标目录路径
     * @param replaceExisting     如果目标文件已存在，是否替换
     * @return 复制后的文件路径
     */
    public static String copyFile(String sourceFilePath, String targetDirectoryPath, boolean replaceExisting) {
        Path sourcePath = Paths.get(sourceFilePath);
        Path targetDirectory = Paths.get(targetDirectoryPath);
        Path targetPath = targetDirectory.resolve(sourcePath.getFileName());

        CopyOption[] options = replaceExisting ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{};
        try {
            // 复制文件
            Files.copy(sourcePath, targetPath, options);
        } catch (IOException e) {
            throw new RuntimeException("复制文件时出错：" + e.getMessage(), e);
        }

        // 返回复制后的文件路径
        return targetPath.toString();
    }

    /**
     * 获取硬盘使用量
     *
     * @param path 路径
     * @return
     */
    public static Disk getSystemDiskSize(String path) {
        Disk disk = new Disk();
        try {
            Path diskPath = FileSystems.getDefault().getPath(path);
            FileStore fileStore = Files.getFileStore(diskPath);

            long free = fileStore.getUsableSpace();
            long total = fileStore.getTotalSpace();
            long used = total - free;

            disk.setTotal(formatFileSize(total));
            disk.setTotalNoUnit(total);
            disk.setFree(formatFileSize(free));
            disk.setFreeNoUnit(free);
            disk.setUsed(formatFileSize(used));
            if (total == 0) {
                disk.setUsage(100);
            } else {
                disk.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            }
        } catch (IOException e) {
            throw new ServiceException("获取磁盘信息出错", e);
        }
        return disk;
    }

    /**
     * 获取硬盘剩余量
     *
     * @param path
     * @return
     */
    public static Long getSystemDiskSizeFree(String path) {
        long free = 0;
        try {
            Path diskPath = FileSystems.getDefault().getPath(path);
            FileStore fileStore = Files.getFileStore(diskPath);
            free = fileStore.getUsableSpace();
        } catch (IOException e) {
            throw new ServiceException("获取磁盘信息出错", e);
        }
        return free;
    }

    /**
     * 计算硬盘使用量
     *
     * @param total 总容量
     * @param used  使用容量
     * @return
     */
    public static Disk getUserDiskSize(long total, long used) {
        Disk disk = null;
        disk = new Disk();
        long free = total - used;
        disk.setTotal(formatFileSize(total));
        disk.setTotalNoUnit(total);
        disk.setFree(formatFileSize(free));
        disk.setFreeNoUnit(free);
        disk.setUsed(formatFileSize(used));
        if (total == 0) {
            disk.setUsage(100);
        } else {
            disk.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
        }
        return disk;
    }

    /**
     * 删除指定目录下所有文件，排除指定路径
     *
     * @param folder         文件夹
     * @param excludePaths   要排除的路径集合
     * @since 2023/7/9 9:14
     */
    public static void deleteFiles(File folder, Set<String> excludePaths) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                // 检查是否需要排除
                if (excludePaths.contains(file.getAbsolutePath())) {
                    continue; // 跳过排除的文件或文件夹
                }
                if (file.isDirectory()) {
                    deleteFiles(file, excludePaths); // 递归调用删除子目录中的文件
                    boolean deleted = file.delete();
                    if (deleted) {
                        log.error("已删除文件夹: " + file.getAbsolutePath());
                    } else {
                        log.error("无法删除文件夹: " + file.getAbsolutePath());
                    }
                } else {
                    boolean deleted = file.delete();
                    if (deleted) {
                        log.error("已删除文件: " + file.getAbsolutePath());
                    } else {
                        log.error("无法删除文件: " + file.getAbsolutePath());
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        //        String filePath = "C:\\Users\\wangchenghai\\Pictures\\1.jpg";
        //        String filePath1 = "C:\\Users\\wangchenghai\\Pictures\\4.jpg";
        //        File file = new File(filePath);
        //        long fileSize = file.length();
        //        System.out.println("文件大小：" + formatFileSize(fileSize));
        //        System.out.println("文件创建时间：" + fileCreationTime(filePath));
        //        String a = extractChecksum(filePath, "SHA-256");
        //        String b = extractChecksum(filePath1, "SHA-256");
        //        if (a.equals(b)) {
        //            System.out.println("相同");
        //        } else {
        //            System.out.println("不同");
        //        }
        //        calculateHashesInFolder("Z:\\Linux\\blog\\dev\\files\\1\\markdown", Constants.MD5);
        String input = "/files/1/markdown";
        String result = getLastSegmentStart(input);

        System.out.println(result); // 输出: markdown

        String path = "D:\\blog\\dev\\files\\1\\markdown";  // 指定硬盘路径

    }
}
