package com.xiaohai.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohai.common.daomain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author xiaohai
 * @since 2023-02-01
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
@Schema(name = "Config", description = "系统配置")
public class Config extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "存储容量")
    private Long diskSize;

    @Schema(description = "关于介绍")
    private String content;
}
