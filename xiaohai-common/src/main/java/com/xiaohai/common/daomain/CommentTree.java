package com.xiaohai.common.daomain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Getter
@Setter
@Schema(name = "CommentTree", description = "评论")
public class CommentTree {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "父id")
    private Integer parentId;

    @Schema(description = "回复人用户名")
    private String replyUsername;

    @Schema(description = "文章id(0 代表留言)")
    private Integer articleId;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像地址")
    private String avatar;

    @Schema(description = "其他评论")
    private List<CommentTree> commentTrees;
}
