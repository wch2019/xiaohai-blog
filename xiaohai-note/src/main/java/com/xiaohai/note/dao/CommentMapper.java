package com.xiaohai.note.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.common.daomain.CommentTree;
import com.xiaohai.note.pojo.dto.CommentDto;
import com.xiaohai.note.pojo.entity.Comment;
import com.xiaohai.note.pojo.query.CommentQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-05-24
 */
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 查询评论列表数据
     *
     * @param wherePage
     * @return
     */
    IPage<CommentDto> findCommentListByPage(@Param("page") IPage<CommentDto> wherePage, @Param("query") CommentQuery query);

    /**
     * 文章id查询评论
     * @param articleId
     * @return
     */
    List<CommentTree> findCommentList(Long articleId);
}
