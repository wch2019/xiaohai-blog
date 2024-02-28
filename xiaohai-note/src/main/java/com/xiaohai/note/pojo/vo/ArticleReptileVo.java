package com.xiaohai.note.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author wangchenghai
 * @date 2024/02/28 17:11:07
 */
@Getter
@Setter
@Schema(name = "ArticleReptileVo", description = "爬取类")
public class ArticleReptileVo {
    @Schema(description = "网站类型")
    @NotNull(message = "网站类型为空")
    private String type;

    @Schema(description = "地址")
    @NotNull(message = "url为空")
    private String url;
}
