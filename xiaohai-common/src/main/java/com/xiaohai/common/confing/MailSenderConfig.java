package com.xiaohai.common.confing;

import com.xiaohai.common.daomain.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @author xiaohai
 * @description: 邮箱配置
 * @date 2024-03-29 22:19
 **/
@Slf4j
@Component
public class MailSenderConfig {
    private final JavaMailSenderImpl javaMailSender= new JavaMailSenderImpl();


    public void init(EmailDto emailDto) {
        if (emailDto != null) {
            javaMailSender.setHost(emailDto.getEmailHost());
            javaMailSender.setUsername(emailDto.getEmailUsername());
            javaMailSender.setPassword(emailDto.getEmailPassword());
            javaMailSender.setPort(emailDto.getEmailPort());
            javaMailSender.setDefaultEncoding("UTF-8");
        }
    }

    /**
     * 获取MailSender
     * @return CustomMailSender
     */
    public JavaMailSenderImpl getSender(){
        return javaMailSender;
    }
}
