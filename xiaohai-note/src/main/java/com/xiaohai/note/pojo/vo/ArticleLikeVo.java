package com.xiaohai.note.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 用户文章点赞表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-07-01
*/
@Getter
@Setter
@Schema(name = "ArticleLikeVo", description = "用户文章点赞表 VO（View Object）：显示层对象")
public class ArticleLikeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description  = "文章id")
    private Integer articleId;

    @Schema(description = "是否点赞(0取消点赞，1点赞)")
    private Integer clickLike ;
}
