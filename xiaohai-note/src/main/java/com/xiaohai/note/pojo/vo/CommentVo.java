package com.xiaohai.note.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
* <p>
* 评论表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-05-24
*/
@Getter
@Setter
@Schema(name = "CommentVo", description = "评论表 VO（View Object）：显示层对象")
public class CommentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "文章id")
    @NotNull(message = "文章id为空")
    private Integer articleId;

    @Schema(description = "回复人id")
    private Integer replyUserId;

    @Schema(description = "评论内容")
    @NotNull(message = "评论内容为空")
    private String content;
}
