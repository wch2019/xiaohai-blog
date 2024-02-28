package com.xiaohai.spider.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wangchenghai
 * @date 2024/02/28 16:13:46
 */
@Getter
@Setter
@Schema(name = "ArticleAcquire", description = "获取文章数据")
public class ArticleAcquire {
    @Schema(description = "文章地址")
    private String originalUrl;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章简介")
    private String summary;

    @Schema(description = "内容")
    private String text;
}
