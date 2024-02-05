package com.xiaohai.note.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
* <p>
* 草稿文章表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-04-04
*/
@Getter
@Setter
@Schema(name = "ArticleVo", description = "文章表 VO（View Object）：显示层对象")
public class ArticleDraftVo implements Serializable {
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

    @Schema(description = "内容")
    @NotNull(message = "内容为空")
    private String text;

}
