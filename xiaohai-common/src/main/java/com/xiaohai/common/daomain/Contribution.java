package com.xiaohai.common.daomain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 持续创作管理
 * @author wangchenghai
 * @date 2023/04/17 16:26:28
 */
@Data
public class Contribution {
    @Schema(description = "年份")
    private int year;

    @Schema(description = "最长连续创作")
    private long longest;

    @Schema(description = "最近持续创作")
    private long continuous;

    @Schema(description = "统计创作时间")
    private LocalDate time;


}
