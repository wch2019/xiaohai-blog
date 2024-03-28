package com.xiaohai.common.utils;

import com.xiaohai.common.daomain.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author wangchenghai
 * @date 2024/03/28 11:39:21
 */
@Slf4j
public class EmailUtils{
    private final JavaMailSenderImpl javaMailSender;
    private final EmailDto emailDto;

    // 构造函数注入依赖
    public EmailUtils(JavaMailSenderImpl javaMailSender, EmailDto emailDto) {
        this.javaMailSender = javaMailSender;
        this.emailDto = emailDto;
        if (this.emailDto != null) {
            this.emailDto.setForm(emailDto.getEmailUsername());
            this.javaMailSender.setHost(emailDto.getEmailHost());
            this.javaMailSender.setUsername(emailDto.getEmailUsername());
            this.javaMailSender.setPassword(emailDto.getEmailPassword());
            this.javaMailSender.setPort(emailDto.getEmailPort());
            this.javaMailSender.setDefaultEncoding("UTF-8");
        }
    }

    public void send(String email, String template, String subject) {
        try {
            //创建一个MINE消息
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            mineHelper.setSubject(subject);
            // 设置邮件发送者
            mineHelper.setFrom("DotCode小站<" + emailDto.getForm() + ">");
            //            mineHelper.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
            // 设置邮件接收者，可以有多个接收者，中间用逗号隔开
            mineHelper.setTo(email);
            // 设置邮件发送日期
            mineHelper.setSentDate(new Date());
            // 设置邮件的正文
            mineHelper.setText(template, true);
            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("系统异常，无法正常发送验证码");
        }

    }
}
