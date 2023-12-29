package com.xiaohai.file.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xiaohai
 * @description: 文件管理-重命名
 * @date 2023-12-27 15:02
 **/
@Data
public class FileManagerNameVo {
    @Schema(description = "id")
    @NotNull(message = "id为空")
    private Integer id;

    @Schema(description = "文件名称")
    @NotNull(message = "文件名称为空")
    private String fileName;

}
