package com.xiaohai.note.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.note.dao.ArticleTagMapper;
import com.xiaohai.note.dao.TagsMapper;
import com.xiaohai.note.pojo.dto.TagsDto;
import com.xiaohai.note.pojo.entity.ArticleTag;
import com.xiaohai.note.pojo.entity.Category;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.pojo.query.TagsQuery;
import com.xiaohai.note.pojo.vo.TagsVo;
import com.xiaohai.note.service.TagsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签表 服务实现类
 *
 * @author xiaohai
 * @since 2023-03-31
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    private final ArticleTagMapper articleTagMapper;

    @Override
    public Integer add(TagsVo vo) {
        Long codeCount = baseMapper.selectCount(new LambdaQueryWrapper<Tags>().eq(Tags::getName, vo.getName()));
        Assert.isTrue(codeCount == 0, "当前标签已存在");
        Tags tags = new Tags();
        BeanUtils.copyProperties(vo, tags);
        baseMapper.insert(tags);
        return tags.getId();
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            Long codeCount = articleTagMapper.selectCount(new QueryWrapper<ArticleTag>().eq("tag_id", id));
            Assert.isTrue(codeCount == 0, "当前标签已引用，无法删除");
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(TagsVo vo) {
        Long codeCount = baseMapper.selectCount(new LambdaQueryWrapper<Tags>().eq(Tags::getName, vo.getName()).ne(Tags::getId, vo.getId()));
        Assert.isTrue(codeCount == 0, "当前标签已存在");
        Tags tags = new Tags();
        BeanUtils.copyProperties(vo, tags);
        return baseMapper.updateById(tags);
    }

    @Override
    public Tags findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<TagsDto> findListByPage(TagsQuery query) {
        IPage<Tags> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Tags> iPage = baseMapper.selectPage(wherePage, new LambdaQueryWrapper<Tags>()
                .eq(StringUtils.isNotBlank(query.getName()), Tags::getName, query.getName())
                .eq(StringUtils.isNotBlank(query.getStatus()), Tags::getStatus, query.getStatus())
                .orderByAsc(Tags::getSort)
                .orderByAsc(Tags::getId));
        List<TagsDto> list = new ArrayList<>();
        for (Tags tags : iPage.getRecords()) {
            TagsDto tagsDto = new TagsDto();
            BeanUtils.copyProperties(tags, tagsDto);
            list.add(tagsDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public List<TagsDto> optionSelect() {
        List<Tags> tags = baseMapper.selectList(new QueryWrapper<Tags>().eq("status", 0).orderByAsc("sort"));
        List<TagsDto> list = new ArrayList<>();
        for (Tags tag : tags) {
            TagsDto tagsDto = new TagsDto();
            BeanUtils.copyProperties(tag, tagsDto);
            list.add(tagsDto);
        }
        return list;
    }

    @Override
    public List<TagsDto> getTagsArticleCount() {
        return baseMapper.selectTagsArticleCount();
    }
}
