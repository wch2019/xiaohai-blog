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
 * 文章标签关联
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-04
 */
@Getter
@Setter
@EqualsAndHashCode()
@TableName("b_article_tag")
@Schema(name = "ArticleTag", description = "文章标签关联")
public class ArticleTag  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "标签id")
    private Integer tagId;
}
