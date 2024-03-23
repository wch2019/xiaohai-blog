package com.xiaohai.note.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 系统通知 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2024-03-09
*/
@Getter
@Setter
@Schema(name = "NotificationsVo", description = "系统通知 VO（View Object）：显示层对象")
public class NotificationsVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "通知类型")
    private String type;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "评论id")
    private Integer commentId;

    @Schema(description = "喜欢id")
    private Integer likeId;

    @Schema(description = "友链id")
    private Integer linkId;

    @Schema(description = "反馈id")
    private Integer feedbackId;

    @Schema(description = "描述")
    private String remark;

    @Schema(description = "是否已读(0否，1是)")
    private Integer isRead;
}
