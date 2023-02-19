package com.xiaohai.admin.confing;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.admin.confing.intercept.PageableInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author wangchenghai
 * @date 2023/01/13 17:18:27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private CorsInterceptor corsInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 跨域拦截器需放在最上面
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new SaInterceptor(handle-> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                //swagger
                .excludePathPatterns("/favicon.ico", "/webjars/**", "/img.icons/**", "/swagger-resources/**", "/v3/api-docs/**", "/swagger-ui/**", "/doc.html", "/swagger-ui.html")
                //登录、验证码、注册
                .excludePathPatterns("/login","/sendEmailCode","/register");
        //分页拦截器
        registry.addInterceptor(new PageableInterceptor());
    }
    /**
     * 开启跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 设置访问源请求头
                .allowedHeaders("*")
                // 设置允许的方法
                .allowedMethods("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 跨域允许时间
                .maxAge(3600);
    }
}
