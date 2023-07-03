package com.xiaohai.note.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.note.pojo.dto.ArticleDto;
import com.xiaohai.note.pojo.dto.ArticleLikeDto;
import com.xiaohai.note.pojo.entity.ArticleLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohai.note.pojo.query.ArticleLikeQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户文章点赞表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-07-01
 */
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
    IPage<ArticleLikeDto>  selectArticleLikePage(@Param("page")IPage<ArticleLikeDto> iPage, @Param("param")ArticleLikeQuery query);
}
