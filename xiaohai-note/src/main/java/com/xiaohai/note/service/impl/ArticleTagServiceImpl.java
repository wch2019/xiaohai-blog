package com.xiaohai.note.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.utils.StringUtil;
import com.xiaohai.note.dao.TagsMapper;
import com.xiaohai.note.pojo.entity.ArticleTag;
import com.xiaohai.note.dao.ArticleTagMapper;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.ArticleTagQuery;
import com.xiaohai.note.pojo.vo.ArticleTagVo;
import com.xiaohai.note.pojo.dto.ArticleTagDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 文章标签关联 服务实现类
 *
 * @author xiaohai
 * @since 2023-04-04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    private final TagsMapper tagsMapper;

    @Override
    public void add(Long[] ids, Integer articleId){
        //写入标签
        for (Long id : ids) {
            ArticleTag articleTag=new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(Math.toIntExact(id));
            baseMapper.insert(articleTag);
        }
    }

    @Override
    public Integer delete(Integer articleId){
        return baseMapper.delete(new QueryWrapper<ArticleTag>().eq("article_id", articleId));
    }

    @Override
    public Integer updateData(ArticleTagVo vo){
        ArticleTag articleTag=new ArticleTag();
        BeanUtils.copyProperties(vo,articleTag);
        return baseMapper.updateById(articleTag);
    }

    @Override
    public ArticleTag findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<ArticleTagDto> findListByPage(ArticleTagQuery query){
        ArticleTag articleTag=new ArticleTag();
        BeanUtils.copyProperties(query,articleTag);
        IPage<ArticleTag> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<ArticleTag> iPage = baseMapper.selectPage(wherePage,Wrappers.query(articleTag));
        List<ArticleTagDto> list=new ArrayList<>();
        for(ArticleTag articleTags:iPage.getRecords()){
            ArticleTagDto articleTagDto=new ArticleTagDto();
            BeanUtils.copyProperties(articleTags,articleTagDto);
            list.add(articleTagDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }

    @Override
    public void rewriteArticleTag(Long[] ids, Integer articleId) {
        //删除关联
        delete(articleId);
        //重新写入关联
        add(ids, articleId);
    }

    @Override
    public void addTagName(List<String> names, Integer articleId) {
        //删除关联
        delete(articleId);

        //写入标签
        for (String name : names) {
            if(StringUtil.isNotBlank(name)){
                Tags tags =tagsMapper.selectOne(new QueryWrapper<Tags>().eq("name",name));
                //没有就新增
                if(tags==null){
                    tags=new Tags();
                    tags.setName(name);
                    tagsMapper.insert(tags);
                }
                ArticleTag articleTag=new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tags.getId());
                baseMapper.insert(articleTag);
            }
        }
    }
}
