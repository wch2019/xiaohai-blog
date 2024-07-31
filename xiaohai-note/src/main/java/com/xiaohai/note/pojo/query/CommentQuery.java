package com.xiaohai.note.pojo.query;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import java.io.Serial;
import java.io.Serializable;

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

    @Parameter(description = "用户id")
    private Integer userId;

    @Parameter(description = "评论来源 0:全部,1:文章,2:留言")
    private Integer source;
}
