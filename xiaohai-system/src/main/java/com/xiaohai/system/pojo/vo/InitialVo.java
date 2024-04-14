package com.xiaohai.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 初始化
 * </p>
 *
 * @author xiaohai
 * @since 2024-04-13
 */
@Getter
@Setter
@Schema(name = "InitialVo", description = "初始化参数")
public class InitialVo {

    @NotBlank(message = "站点名称不能为空")
    @Schema(description = "站点名称", example = "DotCode")
    private String siteName;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名/邮箱", example = "admin")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱", example = "1372195290@qq.com")
    private String email;
}
