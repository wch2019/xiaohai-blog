package com.xiaohai.admin.confing;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Open Api
 *
 * @param
 * @author wangchenghai
 * @date 2023/01/09 15:35:32
 */
@Configuration
public class SwaggerConfig {
    public static final String SESSION_ID = "authentication";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(SESSION_ID))
                .components(securityComponent())
                .info(new Info().title("blog")
                        .description("blog接口文档").contact(new Contact().name("xiaohai"))
                        .version("v1.0.0"));
    }

    /**
     * 添加授权标头
     *  knife4j存在问题等待开发者解决
     * @param
     * @return io.swagger.v3.oas.models.Components
     * @author wangchenghai
     * @date 2023/01/09 15:41:17
     */
    private Components securityComponent() {
        return new Components()
                .addSecuritySchemes(SESSION_ID,
                        new SecurityScheme()
                                .name(SESSION_ID)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER));
    }
}