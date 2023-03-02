package com.xiaohai.common.daomain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
* <p>
* 菜单
* </p>
*
* @author xiaohai
* @since 2023-03-02
*/
@Getter
@Setter
@Schema(name = "MenuTree", description = "菜单")
public class MenuTree implements Serializable {
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

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
    private List<MenuTree> children;
}
