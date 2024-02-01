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

    @Schema(description = "剩余大小")
    private String free;

    @Schema(description = "已经使用量")
    private String used;

    @Schema(description = "其他使用量")
    private String otherUsed;

    @Schema(description = "markdown使用量")
    private String markUsed;;

    @Schema(description = "资源的使用率")
    private double usage;
}
