package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
* 文章表 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-04-04
*/
@Getter
@Setter
@Schema(name = "ArticleDto", description = "文章表 DTO 数据传输对象")
public class ArticleDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章简介")
    private String summary;

    @Schema(description = "分类id")
    private Integer categoryId;

    @Schema(description = "标签列表")
    private String tags;

    @Schema(description = "是否发布(0否，1是)")
    private Integer isPush;

    @Schema(description = "是否顶置(0否，1是)")
    private Integer isTop;

    @Schema(description = "是否原创 (0原创，1转载)")
    private Integer isOriginal;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}
