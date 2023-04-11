package com.xiaohai.admin.confing.satoken;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 在线用户信息
 * @author wangchenghai
 * @date 2023/04/11 16:36:35
 */
@Data
public class OnLineUser{

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "登录ip")
    private String loginIp;

    @Schema(description = "登录时间")
    private LocalDateTime loginDate;

    @Schema(description = "ip来源")
    private String loginSource;

    @Schema(description = "登录次数")
    private String loginCount;

    @Schema(description = "操作系统")
    private String loginOs;

    @Schema(description = "浏览器")
    private String loginBrowser;
}
