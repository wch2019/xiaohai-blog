package com.xiaohai.system.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2024/07/25 17:13:30
 */
@Data
public class UserNameDto {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "头像地址")
    private String avatar;
}
