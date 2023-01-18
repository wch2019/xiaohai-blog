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
* 菜单权限表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-01-18
*/
@Getter
@Setter
@Schema(name = "MenuQuery", description = "菜单权限表 Query 数据查询对象")
@ParameterObject
public class MenuQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "父菜单ID")
    private Integer parentId;

    @Parameter(description = "菜单图标")
    private String icon;

    @Parameter(description = "显示顺序")
    private Integer menuSort;

    @Parameter(description = "菜单名称")
    private String menuName;

    @Parameter(description = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @Parameter(description = "路由地址")
    private String path;

    @Parameter(description = "路径")
    private String component;

    @Parameter(description = "权限标识")
    private String perms;

    @Parameter(description = "状态（0正常 1停用）")
    private String status;
}
