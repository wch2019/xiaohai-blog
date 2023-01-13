package com.xiaohai.common.daomain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wangchenghai
 * @date 2023/01/09 13:47:42
 */
@Data
public class BaseEntity {
    @JsonProperty
    @Schema(description = "创建人", example = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Integer createdBy;


    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;


    @Schema(description = "更新人", example = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updatedBy;


    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
