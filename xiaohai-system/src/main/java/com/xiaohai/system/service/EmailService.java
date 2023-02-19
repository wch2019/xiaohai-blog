package com.xiaohai.system.service;

/**
 * @author Code01
 */
public interface EmailService {
    /**
     * 发送验证码
     * @param email 邮箱账号
     */
    void sendCode(String email);


    /**
     * 友链通过通知
     * @param email 邮箱账号
     */
    void friendPassSendEmail(String email);

    /**
     * 友链未通过通知
     * @param email 邮箱账号
     * @param reason 未通过原因
     */
    void friendFailedSendEmail(String email,String reason);

    /**
     * 邮箱通知我
     * @param subject 邮箱主题
     * @param content 内容
     * @param email   邮件接收者，可以有多个接收者，中间用逗号隔开
     */
    void emailNoticeMe(String subject,String content,String email);
}
