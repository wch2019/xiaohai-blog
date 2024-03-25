package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xiaohai
 * @description: 反馈信息
 * @date 2024-03-25 10:57
 **/
@Data
public class NotificationsFeedbackDto {
    @Schema(description = "id")
    private Integer id;

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
