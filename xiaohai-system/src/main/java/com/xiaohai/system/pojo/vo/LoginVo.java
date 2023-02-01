package com.xiaohai.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 菜单权限表 VO（View Object）：显示层对象
 * </p>
 *
 * @author xiaohai
 * @since 2023-01-18
 */
@Getter
@Setter
@Schema(name = "LoginVo", description = "登录参数")
public class LoginVo {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "admin")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "记住我", example = "false")
    private Boolean rememberMe;
}
