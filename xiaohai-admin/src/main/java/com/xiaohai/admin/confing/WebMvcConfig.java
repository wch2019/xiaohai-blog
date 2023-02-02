package com.xiaohai.admin.confing;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
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
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle-> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                //swagger
                .excludePathPatterns("/favicon.ico", "/webjars/**", "/img.icons/**", "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui/**", "/doc.html", "/swagger-ui.html")
                //登录
                .excludePathPatterns("/login","/sendEmailCode");
        //分页拦截器
        registry.addInterceptor(new PageableInterceptor());
    }
}
