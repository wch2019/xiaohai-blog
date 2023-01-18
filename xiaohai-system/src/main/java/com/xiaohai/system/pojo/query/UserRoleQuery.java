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
* 用户角色关联表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-01-18
*/
@Getter
@Setter
@Schema(name = "UserRoleQuery", description = "用户角色关联表 Query 数据查询对象")
@ParameterObject
public class UserRoleQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "用户id")
    private Integer userId;

    @Parameter(description = "角色id")
    private Integer roleId;
}
