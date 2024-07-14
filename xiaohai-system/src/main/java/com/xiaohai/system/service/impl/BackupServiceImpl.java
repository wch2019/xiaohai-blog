package com.xiaohai.system.service.impl;

import com.alibaba.fastjson.JSONObject;
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
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.service.BackupService;
import com.xiaohai.system.service.ConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.xiaohai.common.constant.FileConstants.BLOG_SQL;
import static com.xiaohai.common.constant.FileConstants.FILE_ZIP;

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

    private final ConfigService configService;

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
    public void restoreFileName(String fileName) {
        String path = fileConfig.getProfile() + FileConstants.BACKUP_FILE + File.separator + fileName;
        String tempFile = StringUtil.generateUUIDWithoutHyphens();
        //备份文件还原临时路径
        String backup = fileConfig.getProfile() + FileConstants.BACKUP_FILE + File.separator + tempFile + File.separator;
        try {
            //创建目录
            FileUtil.directory(backup);
            // 保存文件并返回文件路径
            String filePath = FileUtil.copyFile(path, backup, true);
            backupRestore(filePath, backup);
        } finally {
            //执行删除临时文件
            FileUtil.deleteFiles(new File(backup));
            //删除当前目录
            FileUtil.deleteFile(backup);
        }
    }

    /**
     * 备份文件还原
     *
     * @param file
     */
    @Override
    public void restoreBackupFile(MultipartFile file) {
        ConfigDto configDto = configService.findByOne();
        Assert.isTrue(configDto.getInitial().equals(0), "无法再次初始化！");
        String tempFile = StringUtil.generateUUIDWithoutHyphens();
        //备份文件还原临时路径
        String path = fileConfig.getProfile() + FileConstants.BACKUP_FILE + File.separator + tempFile + File.separator;
        try {
            //创建目录
            FileUtil.directory(path);
            // 保存文件并返回文件路径
            String filePath = FileUtil.saveFile(path, file.getOriginalFilename(), file);
            backupRestore(filePath, path);
        } finally {
            //执行删除临时文件
            FileUtil.deleteFiles(new File(path));
            //删除当前目录
            FileUtil.deleteFile(path);
        }
    }

    /**
     * 备份还原
     *
     * @param filePath 备份文件路径
     * @param path     备份文件还原临时路径
     */
    public void backupRestore(String filePath, String path) {
        ZipUtils.unzip(filePath, path);
        //还原sql
        executeSqlScript(path + BLOG_SQL, url, username, password);
        Set<String> excludePaths = Set.of(fileConfig.getProfile() + FileConstants.BACKUP_FILE);
        //删除文件
        FileUtil.deleteFiles(new File(fileConfig.getProfile()), excludePaths);
        //恢复文件
        ZipUtils.unzip(path + FILE_ZIP, fileConfig.getProfile());

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
                                } else if (value instanceof LocalDateTime) {
                                    rowSql.append("'");
                                    rowSql.append(LocalDateTime.parse(value.toString()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                    rowSql.append("'");
                                    rowSql.append(",");
                                } else {
                                    rowSql.append("'");
                                    rowSql.append(valueResult(value));
                                    rowSql.append("',");
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
     * 对字符数据处理
     *
     * @param value
     * @return
     */
    public static String valueResult(Object value) {
        var str = "";
        if (StringUtil.isNotBlank(value.toString()) && isValidJsonObject(value.toString())) {
            JSONObject jsonObject = JSONObject.parseObject(value.toString());
            str = jsonObject.toJSONString()
                    .replace("'", "\'")
                    .replace("'", "\\'")
                    .replace("\"", "\\\"")
                    .replace("\\n", "\\\\n");
            ;
            return str;
        }
        str = value.toString().replace("'", "\\'").replace("\n", "\\n");
        return str;
    }

    /**
     * 验证是不是Json
     *
     * @param jsonString
     * @return
     */
    public static boolean isValidJsonObject(String jsonString) {
        try {
            JSONObject.parseObject(jsonString);
            return true;
        } catch (Exception e) {
            return false;
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
            log.error("读取SQL文件时发生错误: " + e);
            throw new ServiceException("读取SQL文件时发生错误");
        } catch (SQLException e) {
            log.error("执行SQL时发生错误: " + e);
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
        var value = """
                {"msg":"站点信息展示成功！","code":200,"data":{"website":{"keywords":"DoteCode,点码,开源博客,Java技术分享,Spring教程","name":"xiaohai","logo":"/system/favicon.ico","description":"一个专注于技术分享的博客平台，大家以共同学习，乐于分享，拥抱开源的价值观进行学习交流","title":"DoteCode | 点码","securityRecordNum":" 鲁公网安备37021302001217","recordNum":" \\n鲁ICP备2024067656号","content":"console.log('Connected: ' + frame);"},"basic":{"summary":"用一点点代码，改变生活","tagsCount":37,"github":"https://github.com/wch2019","messageCount":15,"categoryCount":5,"weChat":"1","articleCount":86,"avatar":"/files/1/avatar/e8eca782392e4a95b91297270dbce11e.png","qqNumber":"1372195290","gitee":"https://gitee.com/wch2019","username":"小海"}}}
                """;
        valueResult(value);
//        // Example JDBC URL
//        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/xiaohai_blog?characterEncoding=UTF-8&useUnicode=true&useSSL=false";
//
//        // Extract and print the database name
//        String databaseName = getDatabaseName(jdbcUrl);
//        System.out.println("Database name: " + databaseName);
    }
}
