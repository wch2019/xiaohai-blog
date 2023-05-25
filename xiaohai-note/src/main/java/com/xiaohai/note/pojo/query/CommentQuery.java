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
* 评论表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-05-24
*/
@Getter
@Setter
@Schema(name = "CommentQuery", description = "评论表 Query 数据查询对象")
@ParameterObject
public class CommentQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "父id")
    private Integer parentId;

    @Parameter(description = "文章id")
    private Integer articleId;

    @Parameter(description = "用户id")
    private Integer userId;

    @Parameter(description = "评论内容")
    private String content;
}
