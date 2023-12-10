package com.xiaohai.file.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohai.common.daomain.BaseEntity;
import java.io.Serializable;
import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文件管理
 * </p>
 *
 * @author xiaohai
 * @since 2023-12-09
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("file_manager")
@Schema(name = "FileManager", description = "文件管理")
public class FileManager extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
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
