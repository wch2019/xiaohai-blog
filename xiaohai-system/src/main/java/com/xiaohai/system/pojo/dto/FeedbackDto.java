package com.xiaohai.system.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 用户反馈 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-08-03
*/
@Getter
@Setter
@Schema(name = "FeedbackDto", description = "用户反馈 DTO 数据传输对象")
public class FeedbackDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "回复")
    private String reason;

    @Schema(description = "状态（0-待审核,1-已通过,2-未通过）")
    private String status;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}
