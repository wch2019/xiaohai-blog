package com.xiaohai.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@ComponentScan("com.xiaohai")
@MapperScan({"com.xiaohai.system.dao","com.xiaohai.note.dao","com.xiaohai.file.dao"})
@Slf4j
public class XiaohaiAdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(XiaohaiAdminApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String swaggerPath = env.getProperty("springdoc.swagger-ui.path");
        log.info("(♥◠‿◠)ﾉﾞ  启动成功   ლ(´ڡ`ლ)ﾞ ");
        log.info("Knife4j-ui: http://" + ip + ":" + port + path + "/doc.html");
        log.info("Swagger-ui: http://" + ip + ":" + port + path + swaggerPath);
    }
}
