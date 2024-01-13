package com.xiaohai.admin.confing;

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
    public void run(String... args) throws Exception {
        //添加启动参数--initDatabase执行数据库初始化
        if (containsArgument(args, "--initDatabase")) {
            log.info("开始执行SQL文件导入");
            ResourceDatabasePopulator database = new ResourceDatabasePopulator();
            database.addScript(new ClassPathResource("xiaohai_blog.sql"));
            database.execute(dataSource);
            log.info("完成执行SQL文件导入");
        }else{
            log.info("跳过SQL文件导入");
        }

        log.info("加载必要数据");
        //重置字典缓存数据
        dictTypeService.refreshDict();
        //刷新邮箱配置
        emailService.init();
        log.info("加载必要数据完成");
    }
}
