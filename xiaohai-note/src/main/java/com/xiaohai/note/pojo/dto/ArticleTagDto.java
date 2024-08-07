package com.xiaohai.note.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 文章标签关联 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-04-04
*/
@Getter
@Setter
@Schema(name = "ArticleTagDto", description = "文章标签关联 DTO 数据传输对象")
public class ArticleTagDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "标签id")
    private Integer tagId;
}
