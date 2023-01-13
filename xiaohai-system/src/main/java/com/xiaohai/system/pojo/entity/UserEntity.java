package com.xiaohai.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohai.common.daomain.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xiaohai
 * @since 2023-01-13
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Schema(name = "UserEntity", description = "用户表")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
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
    private String email;

    @Schema(description = "qq号")
    private String qqNumber;

    @Schema(description = "微信号")
    private String weChat;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "自我介绍")
    private String summary;

    @Schema(description = "帐号状态（0正常 1停用）")
    private String status;

    @Schema(description = "最后登录ip")
    private String loginIp;

    @Schema(description = "最后登录时间")
    private LocalDateTime loginDate;
}
