package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
* 系统通知喜欢 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2024-03-09
*/
@Getter
@Setter
@Schema(name = "NotificationsDto", description = "系统通知喜欢 DTO 数据传输对象")
public class NotificationsLikeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户")
    private String nickName;

    @Schema(description = "头像地址")
    private String avatar;

}
