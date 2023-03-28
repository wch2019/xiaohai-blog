package com.xiaohai.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2023/03/28 10:01:21
 */
@Data
public class PasswordVo {
    @Schema(description = "旧密码",required = true)
    private String oldPassword;

    @Schema(description = "新密码",required = true)
    private String newPassword;
}
