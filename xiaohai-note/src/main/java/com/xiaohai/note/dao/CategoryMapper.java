package com.xiaohai.note.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohai.note.pojo.dto.CategoryDto;
import com.xiaohai.note.pojo.entity.Category;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-03
 */
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 查询标签展示列表(包含对应文章数)
     * @return
     */
    List<CategoryDto> getCategoryArticleCount();
}
