package com.xiaohai.common.constant;

/**
 * redis常量
 * @author wangchenghai
 * @date 2023/02/01 17:39:15
 */
public class RedisConstants {
    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 5;

    /**
     * 邮箱验证码
     */
    public static final String EMAIL_CODE = "email_code:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";
    /**
     * 在线用户管理 cache key
     */
    public static final String ON_LINE_USER = "on_line_user:";
    /**
     * 持续创作管理 cache key
     */
    public static final String CONTRIBUTION = "contribution:";
}
