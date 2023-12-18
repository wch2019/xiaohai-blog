package com.xiaohai.file.pojo.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description: 文件数据传输对象
 * @author: xiaohai
 * @date: 2023-03-18 10:03
 **/
@Data
@Setter
@Schema(name = "FileMarkdownDto", description = "文件数据传输对象")
public class FileMarkdownDto {
    @Schema(description = "文件的名称")
    private String fileName;

    @Schema(description = "文件地址")
    private String filePath;

    @Schema(description = "文件类型(0文件，1文件夹)")
    private String fileType;

    @Schema(description = "文件大小")
    private String fileSize;

    @Schema(description = "创建人", example = "创建人")
    private Integer createdBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新人", example = "更新人")
    private Integer updatedBy;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;
}
