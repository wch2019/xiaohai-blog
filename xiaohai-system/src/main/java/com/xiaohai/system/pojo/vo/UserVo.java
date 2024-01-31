package com.xiaohai.system.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* <p>
* 用户表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "UserVo", description = "用户表 VO（View Object）：显示层对象")
public class UserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户性别（ 0女 1男2未知）")
    private String gender;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
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

    @Schema(description = "存储容量")
    private Long diskSize;

    @Schema(description = "角色组")
    @NotNull(message = "角色为空")
    private Long[] roleIds;
}
