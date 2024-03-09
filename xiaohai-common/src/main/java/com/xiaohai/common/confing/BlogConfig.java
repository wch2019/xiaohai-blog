package com.xiaohai.common.confing;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangchenghai
 * @date 2024/03/07 15:08:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "blog")
public class BlogConfig {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;
}
