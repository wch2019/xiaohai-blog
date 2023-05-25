package com.xiaohai.note.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohai.common.daomain.BaseEntity;
import java.io.Serializable;
import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
@EqualsAndHashCode(callSuper = true)
@TableName("b_comment")
@Schema(name = "Comment", description = "评论表")
public class Comment extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "评论内容")
    private String content;
}
