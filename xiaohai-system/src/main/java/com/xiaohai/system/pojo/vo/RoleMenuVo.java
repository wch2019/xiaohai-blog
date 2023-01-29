package com.xiaohai.system.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 角色权限关联表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "RoleMenuVo", description = "角色权限关联表 VO（View Object）：显示层对象")
public class RoleMenuVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "角色id")
    private String roleId;

    @Schema(description = "菜单权限id")
    private String menuId;
}
