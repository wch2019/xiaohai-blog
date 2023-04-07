package com.xiaohai.note.dao;

import com.xiaohai.note.pojo.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 文章标签关联 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-04
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    /**
     * 根据文章id获取标签id列表
     * @param articleId
     * @return
     */
    List<Long> searchAllByArticleId(Long articleId);
}
