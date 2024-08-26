package com.xiaohai.note.pojo.query;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.NotNull;

/**
 * @author xiaohai
 * @description: 文章存在状态
 * @date 2024-08-26 17:14
 **/
@Getter
@Setter
@Schema(name = "ArticleQuery", description = "文章表 Query 数据查询对象")
@ParameterObject
public class ArticleExistQuery {
    @Parameter(description = "文章标题", required = true)
    @NotNull(message = "文章标题为空")
    private String title;

    @Parameter(description = "创建时间", required = true)
    @NotNull(message = "创建时间为空")
    private String date;
}
