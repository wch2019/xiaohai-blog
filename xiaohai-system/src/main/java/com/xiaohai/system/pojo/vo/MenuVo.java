package com.xiaohai.system.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 菜单权限表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "MenuVo", description = "菜单权限表 VO（View Object）：显示层对象")
public class MenuVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "父菜单ID")
    private Integer parentId;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "显示顺序")
    private Integer menuSort;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "路径")
    private String component;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
