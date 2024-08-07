package com.xiaohai.file.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 文件管理 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-12-09
*/
@Getter
@Setter
@Schema(name = "FileManagerDto", description = "文件管理 DTO 数据传输对象")
public class FileManagerDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "父id目录关联")
    private Integer parentId;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件地址")
    private String filePath;

    @Schema(description = "文件类型(0文件，1文件夹)")
    private Integer fileType;

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
