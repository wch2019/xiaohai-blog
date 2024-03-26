package com.xiaohai.system.service.impl;

import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.utils.RedisUtils;
import com.xiaohai.common.utils.Spring.SpringUtils;
import com.xiaohai.system.dao.ConfigMapper;
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.service.ConfigService;
import com.xiaohai.system.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final ConfigMapper configMapper;

    private final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    private final ConfigService configService;
    private String form = "";


    @Override
    public void init() {
        ConfigDto systemConfig = configService.findByOne();
        if (systemConfig != null) {
            form = systemConfig.getEmailUsername();
            javaMailSender.setHost(systemConfig.getEmailHost());
            javaMailSender.setUsername(systemConfig.getEmailUsername());
            javaMailSender.setPassword(systemConfig.getEmailPassword());
            javaMailSender.setPort(systemConfig.getEmailPort());
            javaMailSender.setDefaultEncoding("UTF-8");
        }
    }


    /**
     * 通知我
     */
    @Override
    public void emailNoticeMe(String subject, String content, String email) {

        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件发送者
        message.setFrom(Objects.requireNonNull(javaMailSender.getUsername()));
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开
        message.setTo(email);
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText(content);
        // 发送邮件
        javaMailSender.send(message);
    }

    /**
     * 友链通过发送通知
     *
     * @param email 邮箱账号
     */
    @Override
    public void friendPassSendEmail(String email) {

        String content = """
                <html>
                <body>
                    <p>您在<a href='http://www.dotcode.top'>DotCode</a>站点申请友链加入审核通过!!</span>
                    <p style='padding: 20px;'>感谢您的选择，本站将会竭尽维护好站点稳定，分享高质量的文章，欢迎相互交流互访。</p>
                    <p>可前往<a href='http://www.dotcode.top/links'>本站友链</a>查阅您的站点。</p>
                </body>
                </html>
                 """;
        send(email, content, "友链通过发送通知");
    }

    /**
     * 友链未通过发送通知
     *
     * @param email  邮箱账号
     * @param reason 原因
     */
    @Override
    public void friendFailedSendEmail(String email, String reason) {
        String content = """
                <html>
                <body>
                    <p>您在<a href='http://www.dotcode.top'>DotCode</a>站点申请的友链加入审核未通过!具体原因为:%s</span>
                    <p style='padding: 20px;'>感谢您的选择，本站将会竭尽维护好站点稳定，分享高质量的文章，欢迎相互交流互访。</p>
                   <p>可前往<a href='http://www.dotcode.top/links'>本站友链</a>查阅您的站点。</p>
                </body>
                </html>
                """.formatted(reason);
        send(email, content, "友链未通过发送通知");
    }

    /**
     * 发送邮箱验证码
     * 异步处理
     */
    @Override
    @Async("syncExecutorPool")
    public void sendCode(String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        String content = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Email Template</title>
                    <style>
                        .body {
                            font-size: 14px;
                            padding: 15px;
                            line-height: 1.7;
                        }
                                
                        .qmbox h1, .qmbox h2, .qmbox h3 {
                            color: #00785a;
                        }
                                
                        .qmbox p {
                            margin: 0;
                            color: #333;
                            font-size: 16px;
                        }
                                
                        .qmbox .eo-link, .qmbox .eo-link:hover {
                            color: #0576b9;
                            text-decoration: none;
                            cursor: pointer;
                        }
                                
                        .qmbox .eo-link:hover {
                            color: #3498db;
                        }
                                
                        .qmbox .eo-p-link {
                            display: block;
                            margin-top: 20px;
                            color: #009cff;
                            text-decoration: underline;
                        }
                                
                        .mailContentContainer {
                            max-width: 800px;
                            margin: 20px auto 0 auto;
                            opacity: 1;
                            border-collapse: collapse;
                            border: 1px solid #e5e5e5;
                            box-shadow: 0 10px 15px rgba(0, 0, 0, 0.05);
                            text-align: left;
                            font-size: 14px;
                        }
                                
                        .mailContentContainer img {
                            padding: 15px 15px 15px 30px;
                            width: 50px;
                        }
                                
                        .mailContentContainer .p-code p {
                            color: #253858;
                            text-align: center;
                            line-height: 1.75em;
                            background-color: #f2f2f2;
                            min-width: 200px;
                            margin: 0 auto;
                            font-size: 28px;
                            border-radius: 5px;
                            border: 1px solid #d9d9d9;
                            font-weight: bold;
                        }
                                
                        .p-code {
                            padding: 0 30px;
                        }
                                
                        .p-intro {
                            padding: 30px;
                        }
                                
                        .p-news {
                            padding: 0 30px 30px;
                        }
                    </style>
                </head>
                                
                <body>
                    <div id="contentDiv" class="body">
                        <div id="qm_con_body">
                            <div class="mailContentContainer qmbox qm_con_body_content qqmail_webmail_only">
                                <table cellpadding="0" cellspacing="0">
                                    <tbody>
                                        <tr style="background-color: #f8f8f8;">
                                            <td style="display: flex; justify-content: flex-start; align-items: flex-end;">
                                                <img src="http://www.dotcode.top/api/document/upload/system/favicon.ico">
                                                <h4>DotCode</h4>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-intro">
                                                <h1>验证您的邮箱地址</h1>
                                                <p>感谢您使用 DotCode.</p>
                                                <p>以下是您的邮箱验证码，请将它输入到 DotCode 的邮箱验证码输入框中:</p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-code">
                                                <p>
                                                   %S
                                                </p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-intro">
                                                <p>这一封邮件包括一些您的私密的 DotCode 账号信息，请不要回复或转发它，以免带来不必要的信息泄露风险。</p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="p-intro">
                                                <hr>
                                                <p style="text-align: center;">xiaohai - DotCode</p>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </body>
                </html>""".formatted(code);
        send(email, content, "DotCode验证码");
        log.info("邮箱验证码发送成功,邮箱:{},验证码:{}", email, code);
        SpringUtils.getBean(RedisUtils.class).setCacheObject(RedisConstants.EMAIL_CODE + email, code, RedisConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    private void send(String email, String template, String subject) {
        try {
            //创建一个MINE消息
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mineHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置邮件主题
            mineHelper.setSubject(subject);
            // 设置邮件发送者
            mineHelper.setFrom("DotCode小站<" + form + ">");
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
