package com.xiaohai.file.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Setter;

/**
 * @description: 文件数据传输对象
 * @author: xiaohai
 * @date: 2023-03-18 10:03
 **/
@Data
@Setter
@Schema(name = "FileDto", description = "文件数据传输对象")
public class FileDto {
    @Schema(description = "文件的名称")
    private String name;

    @Schema(description = "文件的后缀")
    private String nameSuffix;

    @Schema(description = "文件或目录的路径")
    private String path;

    @Schema(description = "文件或目录的大小")
    private String size;

    @Schema(description = "文件或目录的创建时间")
    private String createTime;

    @Schema(description = "文件或目录的最后修改时间")
    private String updateTime;
}
