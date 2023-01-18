package com.xiaohai.system.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 用户角色关联表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-01-18
*/
@Getter
@Setter
@Schema(name = "UserRoleVo", description = "用户角色关联表 VO（View Object）：显示层对象")
public class UserRoleVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "角色id")
    private Integer roleId;
}
