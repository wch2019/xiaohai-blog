package com.xiaohai.common.annotation;

import java.lang.annotation.*;
/**
 * 自定义限流注解
 *
 * @author xiaohai
 * @date 2024/9/28 16:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    // 每秒允许的请求数
    int value() default 5;
}
