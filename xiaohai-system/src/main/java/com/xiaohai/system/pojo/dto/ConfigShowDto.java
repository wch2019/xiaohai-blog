package com.xiaohai.system.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统配置展示 DTO 数据传输对象
 * </p>
 *
 * @author xiaohai
 * @since 2023-07-21
 */
@Getter
@Setter
@Schema(name = "ConfigShowDto", description = "系统配置 DTO 数据传输对象")
public class ConfigShowDto {

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

    @Schema(description = "ICP备案号")
    private String recordNum;

    @Schema(description = "公安备案号")
    private String securityRecordNum;

    @Schema(description = "关于介绍")
    private String content;

}
