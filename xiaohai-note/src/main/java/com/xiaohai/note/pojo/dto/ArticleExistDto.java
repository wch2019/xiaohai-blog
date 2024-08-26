package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author xiaohai
 * @description: 文章存在
 * @date 2024-08-26 17:29
 **/
@Getter
@Setter
@Schema(name = "ArticleExistDto", description = "文章存在")
public class ArticleExistDto {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "是否发布(0否，1是)")
    private Integer isPush;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;
}
