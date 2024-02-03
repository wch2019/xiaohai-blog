package com.xiaohai.common.server;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2024/01/25 10:07:40
 */
@Data
public class Disk {

    @Schema(description = "总大小")
    private String total;

    @Schema(description = "总大小没有单位")
    private long totalNoUnit;

    @Schema(description = "剩余大小")
    private String free;

    @Schema(description = "剩余大小没有单位")
    private long freeNoUnit;

    @Schema(description = "已经使用量")
    private String used;

    @Schema(description = "其他使用量")
    private String otherUsed;

    @Schema(description = "其他使用率")
    private double otherUsage;

    @Schema(description = "markdown使用量")
    private String markUsed;

    @Schema(description = "markdown使用率")
    private double markUsage;

    @Schema(description = "资源的使用率")
    private double usage;
}
