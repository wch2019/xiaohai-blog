package com.xiaohai.common.utils;

import com.xiaohai.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

/**
 * @author wangchenghai
 * @date 2024/07/03 16:56:24
 */
@Slf4j
public class ZipUtils {
    /**
     * 将指定文件夹下的所有文件和子文件夹打包到一个ZIP文件
     *
     * @param sourceDirPath 源文件夹路径
     * @param zipFilePath   目标ZIP文件路径
     * @throws IOException 如果打包过程中发生错误
     */
    public static void zipDirectory(String sourceDirPath, String zipFilePath) {
        long startTime = System.currentTimeMillis();
        try {
            Path zipPath = Files.createFile(Paths.get(zipFilePath));
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
                Path sourcePath = Paths.get(sourceDirPath);
                Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(file).toString());
                        zos.putNextEntry(zipEntry);
                        Files.copy(file, zos);
                        zos.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (!sourcePath.equals(dir)) {
                            ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(dir).toString() + "/");
                            zos.putNextEntry(zipEntry);
                            zos.closeEntry();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (IOException e) {
            throw new ServiceException("压缩操作出错", e);
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("压缩操作耗时: " + duration + " 毫秒");
    }

    /**
     * 将指定文件夹下的所有文件和子文件夹打包到一个ZIP文件，并转换为MultipartFile
     *
     * @param sourceDirPath 源文件夹路径
     * @param fileName      文件名称
     * @return 目标ZIP文件的MultipartFile表示
     * @throws IOException 如果打包过程中发生错误
     */
    public static MultipartFile zipDirectoryToMultipartFile(String sourceDirPath, String fileName) {
        long startTime = System.currentTimeMillis();
        try {
            // 创建一个字节输出流来保存ZIP文件数据
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            try (ZipOutputStream zos = new ZipOutputStream(byteArrayOutputStream)) {
                Path sourcePath = Paths.get(sourceDirPath);
                Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(file).toString());
                        zos.putNextEntry(zipEntry);
                        Files.copy(file, zos);
                        zos.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (!sourcePath.equals(dir)) {
                            ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(dir).toString() + "/");
                            zos.putNextEntry(zipEntry);
                            zos.closeEntry();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

            // 创建MultipartFile
            FileItem fileItem = new DiskFileItem("file", "application/zip", true, fileName + ".zip", (int) byteArrayOutputStream.size(), new File(System.getProperty("java.io.tmpdir")));
            try (OutputStream os = fileItem.getOutputStream()) {
                byteArrayOutputStream.writeTo(os);
            }
            // 创建MultipartFile
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.info("压缩操作耗时: " + duration + " 毫秒");
            return multipartFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩成ZIP
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            log.info("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将指定文件夹下的所有文件和子文件夹打包到一个ZIP文件，并转换为MultipartFile
     *
     * @param sourceDirPath 源文件夹路径
     * @param skipDirectory 跳过文件夹路径
     * @param zipFilePath   目标ZIP文件路径
     * @return
     */
    public static void compressDirectories(String sourceDirPath, String skipDirectory, String zipFilePath) {
        long startTime = System.currentTimeMillis();
        try {
            // Create a temporary file for the zip
            Path zipPath = Files.createFile(Paths.get(zipFilePath));
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
                Path sourceDir = Paths.get(sourceDirPath);
                Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // Create zip entry
                        zos.putNextEntry(new ZipEntry(sourceDir.relativize(file).toString()));
                        // Write file to zip
                        Files.copy(file, zos);
                        zos.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (dir.equals(sourceDir.resolve(skipDirectory))) {
                            // Skip 指定 directory
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        if (!sourceDir.equals(dir)) {
                            zos.putNextEntry(new ZipEntry(sourceDir.relativize(dir).toString() + "/"));
                            zos.closeEntry();
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });

            }
        } catch (IOException e) {
            throw new ServiceException("压缩操作出错", e);
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("压缩操作耗时: " + duration + " 毫秒");
    }

    /**
     * 解压缩指定的ZIP文件到目标目录
     *
     * @param zipFilePath ZIP文件路径
     * @param destDir 目标目录
     */
    public static void unzip(String zipFilePath, String destDir){
        File dir = new File(destDir);
        // 如果目标目录不存在，则创建
        if (!dir.exists()) dir.mkdirs();

        // 用于读取和写入文件的缓冲区
        byte[] buffer = new byte[1024];

        // 使用 try-with-resources 来确保资源关闭
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry ze = zis.getNextEntry();
            // 遍历ZIP文件中的每个条目
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                log.info("解压到 " + newFile.getAbsolutePath());

                // 为ZIP中的子目录创建目录
                new File(newFile.getParent()).mkdirs();

                // 使用 try-with-resources 来管理 FileOutputStream
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    int len;
                    // 将条目数据写入文件
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }

                // 关闭当前的ZIP条目
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String sourceDir = "D:\\blog\\dev\\backup";
        String zipFile = "D:\\blog\\dev\\backup\\2024-07-10 133225.zip";


        unzip(zipFile,sourceDir);


    }
}
