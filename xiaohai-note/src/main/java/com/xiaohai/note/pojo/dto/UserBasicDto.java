package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2023/06/02 14:20:29
 */
@Data
public class UserBasicDto {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "文章数")
    private int articleCount;

    @Schema(description = "分类数")
    private int categoryCount;

    @Schema(description = "标签数")
    private int tagsCount;

    @Schema(description = "评论数")
    private int messageCount;

    @Schema(description = "自我介绍")
    private String summary;

    @Schema(description = "gitee")
    private String gitee;

    @Schema(description = "github")
    private String github;

    @Schema(description = "qq号")
    private String qqNumber;

    @Schema(description = "微信号")
    private String weChat;

}
