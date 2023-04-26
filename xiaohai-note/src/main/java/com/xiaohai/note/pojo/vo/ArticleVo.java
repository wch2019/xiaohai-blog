package com.xiaohai.note.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
* <p>
* 文章表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-04-04
*/
@Getter
@Setter
@Schema(name = "ArticleVo", description = "文章表 VO（View Object）：显示层对象")
public class ArticleVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;
    @Schema(description = "文章标题")
    @NotNull(message = "文章标题为空")
    private String title;

    @Schema(description = "文章简介")
    @NotNull(message = "文章简介为空")
    private String summary;

    @Schema(description = "分类id")
    @NotNull(message = "分类为空")
    private Integer categoryId;

    @Schema(description = "标签列表")
    @NotNull(message = "标签为空")
    private Long[] tags;

    @Schema(description = "封面")
    @NotNull(message = "封面为空")
    private String cover;

    @Schema(description = "是否顶置(0否，1是)")
    @NotNull(message = "是否顶置为空")
    private Integer isTop;

    @Schema(description = "是否发布(0否，1是)")
    @NotNull(message = "是否发布为空")
    private Integer isPush;

    @Schema(description = "是否原创 (0原创，1转载)")
    @NotNull(message = "是否原创为空")
    private Integer isOriginal;

    @Schema(description = "转载地址")
    private String originalUrl;

    @Schema(description = "内容")
    @NotNull(message = "内容为空")
    private String text;

}
