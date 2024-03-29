package com.xiaohai.admin.confing;

import cn.hutool.core.bean.BeanUtil;
import com.xiaohai.common.confing.MailSenderConfig;
import com.xiaohai.common.daomain.EmailDto;
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.service.ConfigService;
import com.xiaohai.system.service.DictTypeService;
import com.xiaohai.system.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

/**
 * 加载系统启动数据
 *
 * @author xiaohai
 * @date 2024/1/13 10:51
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MyCommandLineRunner implements CommandLineRunner {

    private final DictTypeService dictTypeService;

    private final EmailService emailService;

    private final MailSenderConfig mailSenderConfig;

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

    @Autowired
    private DataSource dataSource;

    private boolean containsArgument(String[] args, String arg) {
        for (String argument : args) {
            if (argument.equals(arg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(String... args) {
        log.info(url);
        //数据初始化
        initDatabase();

        log.info("加载必要数据");
        //重置字典缓存数据
        dictTypeService.refreshDict();
        //刷新邮箱配置
//        emailService.init();
        ConfigDto systemConfig = configService.findByOne();
        EmailDto email = new EmailDto();
        BeanUtil.copyProperties(systemConfig, email);
        mailSenderConfig.init(email);
        log.info("加载必要数据完成");
    }

    /**
     * 数据初始化
     */
    private void initDatabase() {
        log.info("检查数据库是否初始化");
        // 检测当前衔接数据库是否存在
        if (currentDatabaseExists()) {
            if (countTablesInDatabase() == 0) {
                //导入sql
                importSql();
            } else {
                log.info("数据库存在，无需初始化！");
            }
            return;
        }
        log.warn("数据库不存在！开始初始化过程...");
        // 先创立数据库
        createDatabase();
        //导入sql
        importSql();
    }

    /**
     * 检测当前衔接的库是否存在（衔接URL中的数据库）
     *
     * @return 当前衔接的库是否存在
     */
    private boolean currentDatabaseExists() {
        // 测验以装备文件中的URL树立衔接
        try {
            Connection connection = dataSource.getConnection();
            connection.close();
        } catch (SQLException e) {
            // 若衔接抛出反常则阐明衔接URL中指定数据库不存在
            return false;
        }
        return true;
    }

    /**
     * 履行SQL脚本以创立数据库
     */
    private void createDatabase() {
        try {
            // 修正衔接句子，从头树立衔接
            // 从头树立的衔接不再衔接到指定库，而是直接衔接到整个MySQL
            // 运用URI类解析并拆解衔接地址，从头拼装
            URI databaseUrl = new URI(url.replace("jdbc:", ""));
            // 得到衔接地址中的数据库平台名（例如mysql）
            String databasePlatform = databaseUrl.getScheme();
            // 得到衔接地址和端口
            String hostAndPort = databaseUrl.getAuthority();
            // 得到衔接地址中的库名
            String databaseName = databaseUrl.getPath().substring(1);
            // 拼装新的衔接URL，不衔接至指定库
            String newURL = "jdbc:" + databasePlatform + "://" + hostAndPort + "/";
            // 从头树立衔接
            Connection connection = DriverManager.getConnection(newURL, username, password);
            Statement statement = connection.createStatement();
            // 履行SQL句子创立数据库
            statement.execute("create database if not exists `" + databaseName + "`");
            // 关闭会话和衔接
            statement.close();
            connection.close();
            log.info("创立数据库完成！");
        } catch (URISyntaxException e) {
            log.error("数据库衔接URL格局过错！");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            log.error("衔接失利！");
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前数据库中的表的数量
     *
     * @return 表的数量
     */
    private int countTablesInDatabase() {
        int tableCount = 0;
        try (Connection connection = dataSource.getConnection()) {
            URI databaseUrl = new URI(url.replace("jdbc:", ""));
            // 得到衔接地址中的库名
            String databaseName = databaseUrl.getPath().substring(1);
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(*) AS TABLES " +
                    "FROM information_schema.TABLES " +
                    "WHERE table_schema = '" + databaseName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            // 获取查询结果
            if (resultSet.next()) {
                tableCount = resultSet.getInt("TABLES");
            }
            // 检查表的数量是否为0
            return tableCount;
        } catch (SQLException e) {
            log.error("数据库衔接URL格局过错！");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return tableCount;
    }

    /**
     * 导入sql
     */
    private void importSql() {
        log.info("开始执行SQL文件导入");
        ResourceDatabasePopulator database = new ResourceDatabasePopulator();
        database.addScript(new ClassPathResource("xiaohai_blog.sql"));
        database.execute(dataSource);
        log.info("完成执行SQL文件导入");
    }
}
