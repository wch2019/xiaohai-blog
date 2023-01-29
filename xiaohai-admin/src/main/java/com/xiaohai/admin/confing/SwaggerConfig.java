package com.xiaohai.admin.confing;


import com.xiaohai.common.constant.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Open Api
 *
 * @param
 * @author wangchenghai
 * @date 2023/01/09 15:35:32
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(Constants.SESSION_ID))
                .components(securityComponent())
                .info(new Info().title("blog")
                        .description("blog接口文档").contact(new Contact().name("xiaohai"))
                        .version("v1.0.0"));
    }

    /**
     * 添加授权标头
     *
     * @param
     * @return io.swagger.v3.oas.models.Components
     * @author wangchenghai
     * @date 2023/01/09 15:41:17
     */
    private Components securityComponent() {
        return new Components()
                .addSecuritySchemes(Constants.SESSION_ID,
                        new SecurityScheme()
                                .name(Constants.SESSION_ID)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER));
    }
}