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
* 用户表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-01-29
*/
@Getter
@Setter
@Schema(name = "UserQuery", description = "用户表 Query 数据查询对象")
@ParameterObject
public class UserQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "用户名")
    private String username;

    @Parameter(description = "密码")
    private String password;

    @Parameter(description = "用户昵称")
    private String nickName;

    @Parameter(description = "用户性别（ 0女 1男2未知）")
    private String gender;

    @Parameter(description = "头像地址")
    private String avatar;

    @Parameter(description = "用户邮箱")
    private String email;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "gitee")
    private String gitee;

    @Schema(description = "github")
    private String github;

    @Parameter(description = "qq号")
    private String qqNumber;

    @Parameter(description = "微信号")
    private String weChat;

    @Parameter(description = "自我介绍")
    private String summary;

    @Parameter(description = "帐号状态（0正常 1停用）")
    private String status;

    @Parameter(description = "最后登录ip")
    private String loginIp;

    @Parameter(description = "最后登录时间")
    private LocalDateTime loginDate;
}
