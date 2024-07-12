package com.xiaohai.system.service.impl;

import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.utils.DateUtils;
import com.xiaohai.common.utils.FileUtil;
import com.xiaohai.common.utils.StringUtil;
import com.xiaohai.common.utils.ZipUtils;
import com.xiaohai.file.pojo.vo.UploadVo;
import com.xiaohai.file.service.FileService;
import com.xiaohai.system.dao.BackupMapper;
import com.xiaohai.system.service.BackupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiaohai.common.constant.FileConstants.*;

/**
 * @author wangchenghai
 * @date 2024/07/08 17:40:01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackupServiceImpl implements BackupService {

    private final BackupMapper backupMapper;

    private final FileService fileService;

    private final FileConfig fileConfig;

    /**
     * 读取衔接地址
     */
    @Value("${spring.datasource.url}")
    private String url;
    /**
     * 读取用户名
     */
    @Value("${spring.datasource.username}")
    private String username;
    /**
     * 读取暗码
     */
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void backupDatabase() {
        String pathFile = fileConfig.getProfile() + FileConstants.BACKUP_FILE;
        String tempFile = StringUtil.generateUUIDWithoutHyphens();
        //备份文件临时路径
        String path = fileConfig.getProfile() + FileConstants.BACKUP_FILE + File.separator + tempFile + File.separator;
        //创建目录
        FileUtil.directory(path);
        try {
            backupSQL(path);
            backupFile(path);
            var vo = new UploadVo();
            vo.setFile(ZipUtils.zipDirectoryToMultipartFile(path, DateUtils.getCurrentTime()));
            vo.setPath(pathFile.replace(fileConfig.getProfile(), File.separator));
            vo.setFileSizeIgnore(true);
            fileService.upload(vo);
        } finally {
            //执行删除临时文件
            FileUtil.deleteFiles(new File(path));
            //删除当前目录
            FileUtil.deleteFile(path);
        }
    }

    @Override
    public void restore(String filePath) {
        //        backupFile(file);
    }

    /**
     * 备份文件还原
     *
     * @param file
     */
    public void backupFile(MultipartFile file) {
        String tempFile = StringUtil.generateUUIDWithoutHyphens();
        //备份文件还原临时路径
        String path = fileConfig.getProfile() + FileConstants.BACKUP_FILE + File.separator + tempFile + File.separator;
        //创建目录
        FileUtil.directory(path);
        // 保存文件并返回文件路径
        String filePath = FileUtil.saveFile(path, file.getOriginalFilename(), file);
        ZipUtils.unzip(filePath, path);
        try {
            //还原sql
            executeSqlScript(path + BLOG_SQL, url, username, password);
            Set<String> excludePaths = Set.of(fileConfig.getProfile() + FileConstants.BACKUP_FILE);
            //删除文件
            FileUtil.deleteFiles(new File(fileConfig.getProfile()), excludePaths);
            //恢复文件
            ZipUtils.unzip(path + FILE_ZIP, fileConfig.getProfile());
        } finally {
            //执行删除临时文件
            FileUtil.deleteFiles(new File(path));
            //删除当前目录
            FileUtil.deleteFile(path);
        }
    }

    /**
     * sql数据备份
     *
     * @return
     */
    private void backupSQL(String path) {

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path + BLOG_SQL)))) {

            List<String> tableNames = backupMapper.showTables();
            for (String table : tableNames) {
                // 获取表结构
                Map<String, String> createTableResult = backupMapper.showCreateTable(table);
                String createTableSql = createTableResult.get("Create Table");

                // 写入表结构
                writer.println("--");
                writer.println("-- Table structure for table `" + table + "`");
                writer.println("--");
                writer.println("DROP TABLE IF EXISTS `" + table + "`;");
                writer.println("/*!40101 SET @saved_cs_client     = @@character_set_client */;");
                writer.println("/*!50503 SET character_set_client = utf8mb4 */;");
                writer.println(createTableSql + ";");
                writer.println("/*!40101 SET character_set_client = @saved_cs_client */;");
                writer.println();
                // 运用URI类解析并拆解衔接地址，从头拼装
                URI databaseUrl = new URI(url.replace("jdbc:", ""));
                // 得到衔接地址中的库名
                String databaseName = databaseUrl.getPath().substring(1);
                // 获取表的所有列名
                List<String> columns = backupMapper.getTableColumns(databaseName, table);

                // 获取表数据
                List<LinkedHashMap<String, Object>> rows = backupMapper.selectAll(table);
                // 确保每一行数据包含所有列名，且列顺序与 columns 一致
                for (LinkedHashMap<String, Object> row : rows) {
                    LinkedHashMap<String, Object> orderedRow = new LinkedHashMap<>();
                    for (String column : columns) {
                        orderedRow.put(column, row.getOrDefault(column, null));
                    }
                    row.clear();
                    row.putAll(orderedRow);
                }
                if (!rows.isEmpty()) {
                    writer.println("--");
                    writer.println("-- Dumping data for table `" + table + "`");
                    writer.println("--");
                    writer.println("LOCK TABLES `" + table + "` WRITE;");
                    writer.println("/*!40000 ALTER TABLE `" + table + "` DISABLE KEYS */;");
                    writer.print("INSERT INTO `" + table + "` VALUES ");
                    for (int i = 0; i < rows.size(); i++) {
                        LinkedHashMap<String, Object> row = rows.get(i);
                        StringBuilder rowSql = new StringBuilder("(");
                        for (Object value : row.values()) {
                            if (value == null) {
                                rowSql.append("NULL,");
                            } else {
                                if (value instanceof Long || value instanceof Integer) {
                                    rowSql.append(value).append(",");
                                } else {
                                    rowSql.append("'").append(value.toString().replace("'", "''").replace("\n", "\\n")).append("',");
                                }
                            }
                        }
                        rowSql.deleteCharAt(rowSql.length() - 1);
                        rowSql.append(")");
                        if (i < rows.size() - 1) {
                            rowSql.append(",");
                        } else {
                            rowSql.append(";");
                        }
                        writer.print(rowSql.toString());
                    }
                    writer.println();
                    writer.println("/*!40000 ALTER TABLE `" + table + "` ENABLE KEYS */;");
                    writer.println("UNLOCK TABLES;");
                    writer.println();
                }
            }

            writer.flush();
        } catch (IOException e) {
            log.error("备份SQL异常：", e);
            throw new ServiceException("备份SQL出现意外");
        } catch (URISyntaxException e) {
            log.error("数据库衔接URL格局过错！", e);
            throw new ServiceException("数据库衔接URL格局过错！");
        }
    }

    /**
     * 系统文件备份
     *
     * @return
     */
    private void backupFile(String path) {
        ZipUtils.compressDirectories(fileConfig.getProfile(), fileConfig.getProfile() + FileConstants.BACKUP_FILE, path + FILE_ZIP);
    }

    /**
     * 执行导出的mysqldump脚本
     *
     * @param scriptFilePath SQL脚本文件路径
     * @param dbUrl          数据库连接URL
     * @param dbUser         数据库用户名
     * @param dbPassword     数据库密码
     */
    public static void executeSqlScript(String scriptFilePath, String dbUrl, String dbUser, String dbPassword) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {

            StringBuilder sql = new StringBuilder();
            String line;
            try (Statement statement = connection.createStatement()) {
                while ((line = reader.readLine()) != null) {
                    // 跳过注释和空行
                    if (line.trim().isEmpty() || line.trim().startsWith("--") || line.trim().startsWith("/*")) {
                        continue;
                    }

                    sql.append(line);
                    // 检查是否以分号结束，即一条SQL语句结束
                    if (line.trim().endsWith(";")) {
                        log.info("执行SQL: " + sql);
                        statement.execute(sql.toString());
                        // 清空StringBuilder，准备下一条SQL语句
                        sql.setLength(0);
                    }
                }
            }
        } catch (IOException e) {
            log.error("读取SQL文件时发生错误: " + e.getMessage());
            throw new ServiceException("读取SQL文件时发生错误");
        } catch (SQLException e) {
            log.error("执行SQL时发生错误: " + e.getMessage());
            throw new ServiceException("执行SQL时发生错误");
        }
    }

    public static String getDatabaseName(String jdbcUrl) throws URISyntaxException {
        // 运用URI类解析并拆解衔接地址，从头拼装
        URI databaseUrl = new URI(jdbcUrl.replace("jdbc:", ""));
        // 得到衔接地址中的库名
        String databaseName = databaseUrl.getPath().substring(1);
        return databaseName;
    }

    public static void main(String[] args) throws URISyntaxException {
        // Example JDBC URL
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/xiaohai_blog?characterEncoding=UTF-8&useUnicode=true&useSSL=false";

        // Extract and print the database name
        String databaseName = getDatabaseName(jdbcUrl);
        System.out.println("Database name: " + databaseName);
    }
}
