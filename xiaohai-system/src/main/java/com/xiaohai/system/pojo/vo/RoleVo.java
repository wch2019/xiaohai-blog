package com.xiaohai.system.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
* <p>
* 角色表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "RoleVo", description = "角色表 VO（View Object）：显示层对象")
public class RoleVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "角色编码")
    @NotBlank(message = "角色编码为空")
    private String code;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String remarks;

    @Schema(description = "角色状态（0正常 1停用）")
    private String status;
}
