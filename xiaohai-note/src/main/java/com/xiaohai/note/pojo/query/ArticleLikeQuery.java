package com.xiaohai.note.pojo.query;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 用户文章点赞表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-07-01
*/
@Getter
@Setter
@Schema(name = "ArticleLikeQuery", description = "用户文章点赞表 Query 数据查询对象")
@ParameterObject
public class ArticleLikeQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description= "文章标题")
    private String title;

    @Parameter(description = "分类id")
    private Integer categoryId;

    @Schema(description = "标签id")
    private Long tagId;

    private Long userId;
}
