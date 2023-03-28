package com.xiaohai.system.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2023/03/28 10:01:00
 */
@Data
public class EmailVo {

    @Schema(description = "新邮箱",required = true)
    private String newEmail;

    @Schema(description = "验证码",required = true)
    private String code;
}
