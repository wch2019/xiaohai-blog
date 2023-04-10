package com.xiaohai.note.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.note.pojo.dto.ArticleDto;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.query.ArticleQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-04
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 查询文章表列表数据
     * @param wherePage
     * @param query
     * @return
     */
    IPage<ArticleDto> selectPageArticleQuery(@Param("page")IPage<ArticleDto> wherePage, @Param("param") ArticleQuery query);
}
