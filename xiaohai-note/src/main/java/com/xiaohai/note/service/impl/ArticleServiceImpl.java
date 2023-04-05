package com.xiaohai.note.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.dao.ArticleMapper;
import com.xiaohai.note.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.ArticleQuery;
import com.xiaohai.note.pojo.vo.ArticleVo;
import com.xiaohai.note.pojo.dto.ArticleDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 文章表 服务实现类
 *
 * @author xiaohai
 * @since 2023-04-04
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Integer add(ArticleVo vo){
        Article article=new Article();
        BeanUtils.copyProperties(vo,article);
        return baseMapper.insert(article);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(ArticleVo vo){
        Article article=new Article();
        BeanUtils.copyProperties(vo,article);
        return baseMapper.updateById(article);
    }

    @Override
    public Article findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<ArticleDto> findListByPage(ArticleQuery query){
        Article article=new Article();
        BeanUtils.copyProperties(query,article);
        IPage<Article> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Article> iPage = baseMapper.selectPage(wherePage,Wrappers.query(article));
        List<ArticleDto> list=new ArrayList<>();
        for(Article articles:iPage.getRecords()){
            ArticleDto articleDto=new ArticleDto();
            BeanUtils.copyProperties(articles,articleDto);
            list.add(articleDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
