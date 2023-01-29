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
* 角色表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "RoleQuery", description = "角色表 Query 数据查询对象")
@ParameterObject
public class RoleQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "角色编码")
    private String code;

    @Parameter(description = "角色名称")
    private String name;

    @Parameter(description = "角色描述")
    private String remarks;

    @Parameter(description = "角色状态（0正常 1停用）")
    private String status;
}
