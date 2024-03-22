package com.xiaohai.note.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统通知
 * </p>
 *
 * @author xiaohai
 * @since 2024-03-09
 */
@Getter
@Setter
@EqualsAndHashCode()
@TableName("b_notifications")
@Schema(name = "Notifications", description = "系统通知")
public class Notifications  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "系統通知")
    private String message;

    @Schema(description = "是否已读(0否，1是)")
    private Integer isRead;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}
