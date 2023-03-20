package com.xiaohai.file.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @description: 上传配置参数
 * @author: xiaohai
 * @date: 2023-03-17 21:55
 **/
@Data
@Schema(name = "UploadVo", description = "上传配置参数")
public class UploadVo {
    @Schema(description = "上传文件")
    @NotNull
    private MultipartFile file;

    @Schema(description = "上传类型 1md图片，2头像")
    @NotNull
    private Integer type;

    @Schema(description = "上传路径")
    private String path;
}
