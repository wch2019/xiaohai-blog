package com.xiaohai.system.pojo.query;

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
* 字典数据表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-02-03
*/
@Getter
@Setter
@Schema(name = "DictDataQuery", description = "字典数据表 Query 数据查询对象")
@ParameterObject
public class DictDataQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "字典排序")
    private Integer dictSort;

    @Parameter(description = "字典类型")
    private String dictType;

    @Parameter(description = "字典标签")
    private String dictLabel;

    @Parameter(description = "字典键值")
    private String dictValue;

    @Parameter(description = "状态（0正常 1停用）")
    private String status;
}
