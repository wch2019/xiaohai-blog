package com.xiaohai.system.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 系统配置 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-02-01
*/
@Getter
@Setter
@Schema(name = "ConfigDto", description = "系统配置 DTO 数据传输对象")
public class ConfigDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "邮箱地址")
    private String emailHost;

    @Schema(description = "邮箱发件人")
    private String emailUsername;

    @Schema(description = "邮箱授权码")
    private String emailPassword;

    @Schema(description = "邮箱发送端口")
    private Integer emailPort;

    @Schema(description = "本地文件地址")
    private String filePath;
}
