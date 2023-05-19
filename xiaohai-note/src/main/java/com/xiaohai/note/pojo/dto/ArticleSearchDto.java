package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author wangchenghai
 * @date 2023/05/19 14:12:18
 */
@Data
public class ArticleSearchDto {
    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章简介")
    private String summary;

    @Schema(description = "内容")
    private String text;
}
