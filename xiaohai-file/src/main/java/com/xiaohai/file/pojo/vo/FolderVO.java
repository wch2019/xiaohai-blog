package com.xiaohai.file.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author xiaohai
 * @description:
 * @date 2023-12-27 16:32
 **/
@Data
public class FolderVO {
    @Schema(description = "文件夹路径")
    @NotNull
    private String path;
}
