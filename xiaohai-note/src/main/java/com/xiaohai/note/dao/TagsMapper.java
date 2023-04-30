package com.xiaohai.note.dao;

import com.xiaohai.note.pojo.dto.TagsDto;
import com.xiaohai.note.pojo.entity.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-03-31
 */
public interface TagsMapper extends BaseMapper<Tags> {
    /**
     * 查询标签展示列表(包含对应文章数)
     * @return
     */
    List<TagsDto> selectTagsArticleCount();
}
