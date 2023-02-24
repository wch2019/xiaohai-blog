package com.xiaohai.system.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 字典类型表 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "DictTypeDto", description = "字典类型表 DTO 数据传输对象")
public class DictTypeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
