package com.xiaohai.system.pojo.dto;

import com.xiaohai.common.server.Disk;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
* <p>
* 用户表 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "UserDto", description = "用户表 DTO 数据传输对象")
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户性别（ 0女 1男2未知）")
    private String gender;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "gitee")
    private String gitee;

    @Schema(description = "github")
    private String github;

    @Schema(description = "qq号")
    private String qqNumber;

    @Schema(description = "微信号")
    private String weChat;

    @Schema(description = "自我介绍")
    private String summary;

    @Schema(description = "帐号状态（0正常 1停用）")
    private String status;

    @Schema(description = "登录次数")
    private String loginCount;

    @Schema(description = "最后登录ip")
    private String loginIp;

    @Schema(description = "ip来源")
    private String loginSource;

    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;

    @Schema(description = "操作系统")
    private String loginOs;

    @Schema(description = "浏览器")
    private String loginBrowser;

    @Schema(description = "存储容量")
    private Long diskSize;

    @Schema(description = "存储容量详情")
    private Disk disk;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "角色组")
    private List<Long> roleIds;
}
