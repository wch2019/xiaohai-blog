package com.xiaohai.note.pojo.dto;

import com.xiaohai.common.daomain.CommentTree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 评论和评论数返回类
 * </p>
 *
 * @author xiaohai
 * @since 2023-06-22 07:47
 **/
@Data
public class CommentCountDto {
    @Schema(description = "评论数")
    private  int CommentCount;

    @Schema(description = "评论列表")
    private  List<CommentTree> commentTrees;
}
