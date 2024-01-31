package com.xiaohai.system.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统配置 DTO 数据传输对象
 * </p>
 *
 * @author xiaohai
 * @since 2023-02-01
 */
@Getter
@Setter
@Schema(name = "ConfigDto", description = "系统配置 DTO 数据传输对象")
public class ConfigDto{
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "网站logo")
    private String logo;

    @Schema(description = "网站名称")
    private String name;

    @Schema(description = "网站标题")
    private String title;

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "网站描述")
    private String description;

    @Schema(description = "备案号")
    private String recordNum;

    @Schema(description = "邮箱地址")
    private String emailHost;

    @Schema(description = "邮箱发件人")
    private String emailUsername;

    @Schema(description = "邮箱授权码")
    private String emailPassword;

    @Schema(description = "邮箱发送端口")
    private Integer emailPort;

    @Schema(description = "本地文件地址")
    private String filePath;

    @Schema(description = "关于介绍")
    private String content;

    @Schema(description = "本地文件地址")
    private String profile;

    @Schema(description = "获取图片上传路径")
    private String imagePath;

    @Schema(description = "获取头像上传路径")
    private String avatarPath;

    @Schema(description = "存储容量")
    private Long diskSize;
}
