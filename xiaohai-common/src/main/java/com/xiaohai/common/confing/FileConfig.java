package com.xiaohai.common.confing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author wangchenghai
 * @date 2023/03/16 13:56:07
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    @Schema(description = "本地文件地址")
    private String profile;

    @Schema(description = "获取图片上传路径")
    private String imagePath;

    @Schema(description = "获取头像上传路径")
    private String avatarPath;

    @Schema(description = "获取文件上传路径")
    private String filePath;

    public String getImagePath() {
        return this.profile+"image"+ File.separator;
    }

    public String getAvatarPath() {
        return this.profile+"avatar"+ File.separator;
    }
    public String getFilePath() {
        return this.profile+"files"+ File.separator;
    }
}
