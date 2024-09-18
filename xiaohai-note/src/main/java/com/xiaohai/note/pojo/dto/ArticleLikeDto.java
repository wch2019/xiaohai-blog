package com.xiaohai.note.pojo.dto;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 用户文章点赞表 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-07-01
*/
@Getter
@Setter
@Schema(name = "ArticleLikeDto", description = "用户文章点赞表 DTO 数据传输对象")
public class ArticleLikeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "分类id")
    private Integer categoryId;

    @Schema(description = "标签列表")
    private String tags;

    @Schema(description = "喜欢量")
    private Integer likeCount;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}
