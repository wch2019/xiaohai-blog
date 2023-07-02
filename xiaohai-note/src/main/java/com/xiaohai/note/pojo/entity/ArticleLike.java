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
 * 用户文章点赞表
 * </p>
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Getter
@Setter
@TableName("b_article_like")
@Schema(name = "ArticleLike", description = "用户文章点赞表")
public class ArticleLike  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "用户id")
    private Integer userId;
}
