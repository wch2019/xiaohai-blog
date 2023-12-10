package com.xiaohai.file.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 文件管理 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-12-09
*/
@Getter
@Setter
@Schema(name = "FileManagerVo", description = "文件管理 VO（View Object）：显示层对象")
public class FileManagerVo implements Serializable {
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
    private Integer fileSize;

    @Schema(description = "文件内容哈希")
    private String fileHash;
}
