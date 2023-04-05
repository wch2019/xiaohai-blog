package com.xiaohai.note.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.ArticleTag;
import com.xiaohai.note.dao.ArticleTagMapper;
import com.xiaohai.note.service.ArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public Integer add(ArticleTagVo vo){
        ArticleTag articleTag=new ArticleTag();
        BeanUtils.copyProperties(vo,articleTag);
        return baseMapper.insert(articleTag);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
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
}
