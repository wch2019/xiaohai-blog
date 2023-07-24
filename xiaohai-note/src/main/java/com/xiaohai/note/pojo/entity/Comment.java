package com.xiaohai.note.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Getter
@Setter
@TableName("b_comment")
@Schema(name = "Comment", description = "评论表")
public class Comment implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "文章id(0 代表留言)")
    private Integer articleId;

    @Schema(description = "评论人id")
    private Integer userId;

    @Schema(description = "回复人id")
    private Integer replyUserId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}
