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
* 分类表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-04-03
*/
@Getter
@Setter
@Schema(name = "CategoryQuery", description = "分类表 Query 数据查询对象")
@ParameterObject
public class CategoryQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "分类名称")
    private String name;

    @Parameter(description = "点击次数")
    private Integer click;

    @Parameter(description = "排序")
    private Integer sort;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
