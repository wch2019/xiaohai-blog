package com.xiaohai.admin.confing;

import com.xiaohai.admin.confing.intercept.PageableInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangchenghai
 * @date 2023/01/13 17:18:27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //分页拦截器
        registry.addInterceptor(new PageableInterceptor());
    }
}
