package com.xiaohai.common.daomain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: xiaohai
 * @date: 2023/11/23 11:20
 */
@Data
public class RcAttachmentInfo {
    @Schema(description = "高度")
    private int width;

    @Schema(description = "宽度")
    private int height;

}