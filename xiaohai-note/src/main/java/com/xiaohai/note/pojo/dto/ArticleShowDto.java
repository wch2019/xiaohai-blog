package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 展示页文章列表DTO数据对象
 * @author: xiaohai
 * @date: 2023-04-30 20:24
 **/
@Data
@Schema(name = "ArticleShowDto", description = "展示页文章列表DTO数据对象")
public class ArticleShowDto {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

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

    @Schema(description = "文章简介")
    private String summary;

    @Schema(description = "分类id")
    private Integer categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "标签列表")
    private String tags;

    @Schema(description = "是否顶置(0否，1是)")
    private Integer isTop;

    @Schema(description = "是否原创 (0原创，1转载)")
    private Integer isOriginal;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "消息数")
    private Integer commentCount;

    @Schema(description = "点赞数")
    private Integer likeCount;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}
