package com.xiaohai.file.pojo.query;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 文件管理 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-12-09
*/
@Getter
@Setter
@Schema(name = "FileManagerQuery", description = "文件管理 Query 数据查询对象")
@ParameterObject
public class FileManagerQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "父id目录关联")
    private Integer parentId;

    @Parameter(description = "文件名称")
    private String fileName;

    @Parameter(description = "文件地址")
    private String filePath;

    @Parameter(description = "文件类型")
    private String fileType;

    @Parameter(description = "文件大小")
    private Integer fileSize;

    @Parameter(description = "文件内容哈希")
    private String fileHash;
}
