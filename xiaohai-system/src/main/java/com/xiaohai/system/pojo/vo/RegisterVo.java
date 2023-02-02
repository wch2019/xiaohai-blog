package com.xiaohai.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wangchenghai
 * @date 2023/02/02 14:53:10
 */
@Getter
@Setter
@Schema(name = "RegisterVo", description = "注册参数")
public class RegisterVo {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "邮箱验证码")
    @NotBlank(message = "邮箱验证码不能为空")
    private String code;


}
