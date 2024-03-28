package com.xiaohai.common.daomain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2024/03/28 11:40:53
 */
@Data
public class EmailDto {
    @Schema(description = "邮箱地址")
    private String emailHost;

    @Schema(description = "邮箱发件人")
    private String emailUsername;

    @Schema(description = "邮箱授权码")
    private String emailPassword;

    @Schema(description = "邮箱发送端口")
    private Integer emailPort;

    @Schema(description = "邮件发送者")
    private String form;
}
