package com.xiaohai.system.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
* <p>
* 角色表 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "RoleDto", description = "角色表 DTO 数据传输对象")
public class RoleDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String remarks;

    @Schema(description = "角色状态（0正常 1停用）")
    private String status;

    @Schema(description = "菜单组")
    private List<Long> menuIds;
}
