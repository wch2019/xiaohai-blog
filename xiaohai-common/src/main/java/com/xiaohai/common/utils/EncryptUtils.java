package com.xiaohai.common.utils;

import cn.dev33.satoken.secure.SaSecureUtil;

/**
 * 加密工具
 *
 * @author wangchenghai
 * @date 2023/01/18 16:31:01
 */
public class EncryptUtils {
    static final String KEY = "xiaohai2023";

    /**
     * 校验内容是否一直
     */
    public static boolean validate(String target, String target1) {
        return target.equalsIgnoreCase(aesEncrypt(target1));
    }

    /**
     * AES加密
     *
     * @param password：密码
     * @return
     */
    public static String aesEncrypt(String password) {
        return SaSecureUtil.aesEncrypt(KEY, password);
    }

    public static void main(String[] args) {
        System.out.println(aesEncrypt("123456"));
    }
}
