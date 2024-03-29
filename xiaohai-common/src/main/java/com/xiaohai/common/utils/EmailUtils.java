package com.xiaohai.common.utils;

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

    public static void send(JavaMailSenderImpl javaMailSender ,String email, String template, String subject) {
        try {
            //创建一个MINE消息
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            mineHelper.setSubject(subject);
            // 设置邮件发送者
            mineHelper.setFrom("DotCode小站<" + javaMailSender.getUsername() + ">");
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
