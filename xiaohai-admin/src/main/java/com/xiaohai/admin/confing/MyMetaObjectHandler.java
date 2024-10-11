package com.xiaohai.admin.confing;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * 自动填充功能
 *
 * @author WCH
 * @version 1.0
 * @date 2021/11/18 15:48
 */
@Configuration
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Integer by = StpUtil.getLoginIdDefaultNull() == null ? null : Integer.valueOf((String) StpUtil.getLoginId());
        log.info(" -------------------- [MyBatisPlus自动填充处理] start insert fill ...  --------------------");
        this.setFieldValByName("createdBy", by, metaObject);
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updatedBy", by, metaObject);
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info(" -------------------- [MyBatisPlus自动填充处理] start update fill ...  --------------------");
        Integer by = StpUtil.getLoginIdDefaultNull() == null ? null : Integer.valueOf((String) StpUtil.getLoginId());
        this.setFieldValByName("updatedBy", by, metaObject);
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     *
     * @param
     * @return PaginationInterceptor
     * @author wangchenghai
     * @date 2023/01/13 17:33:37
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
