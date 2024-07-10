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

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

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
    public void restore() {
        String tempFile = StringUtil.generateUUIDWithoutHyphens();
        //备份文件临时路径
        String path = fileConfig.getProfile() + FileConstants.BACKUP_FILE + File.separator + tempFile + File.separator;
        //创建目录
        FileUtil.directory(path);
        try {
            //还原sql
            executeSqlScript(path + BLOG_SQL, url, username, password);
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

                // 获取表数据
                List<Map<String, Object>> rows = backupMapper.selectAll(table);
                if (!rows.isEmpty()) {
                    writer.println("--");
                    writer.println("-- Dumping data for table `" + table + "`");
                    writer.println("--");
                    writer.println("LOCK TABLES `" + table + "` WRITE;");
                    writer.println("/*!40000 ALTER TABLE `" + table + "` DISABLE KEYS */;");
                    writer.print("INSERT INTO `" + table + "` VALUES ");
                    for (int i = 0; i < rows.size(); i++) {
                        Map<String, Object> row = rows.get(i);
                        StringBuilder rowSql = new StringBuilder("(");
                        for (Object value : row.values()) {
                            if (value == null) {
                                rowSql.append("NULL,");
                            } else {
                                rowSql.append("'").append(value.toString().replace("'", "''")).append("',");
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
                        statement.execute(sql.toString());
                        log.info("执行SQL: " + sql);
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
}
