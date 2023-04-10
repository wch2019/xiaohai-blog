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
* 文章表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-04-04
*/
@Getter
@Setter
@Schema(name = "ArticleQuery", description = "文章表 Query 数据查询对象")
@ParameterObject
public class ArticleQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "文章标题")
    private String title;

    @Parameter(description = "分类id")
    private Integer categoryId;

    @Schema(description = "标签id")
    private Long tagId;

    @Parameter(description = "是否发布(0否，1是)")
    private Integer isPush;

    @Parameter(description = "是否顶置(0否，1是)")
    private Integer isTop;

    @Parameter(description = "是否原创 (0原创，1转载)")
    private Integer isOriginal;
}
