package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.ArticleLike;
import com.xiaohai.note.dao.ArticleLikeMapper;
import com.xiaohai.note.service.ArticleLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.ArticleLikeQuery;
import com.xiaohai.note.pojo.vo.ArticleLikeVo;
import com.xiaohai.note.pojo.dto.ArticleLikeDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 用户文章点赞表 服务实现类
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    @Override
    public Integer add(ArticleLikeVo vo){
        // TODO:点赞消息推送
        if(vo.getClickLike()==1){
            ArticleLike like=new ArticleLike();
            like.setArticleId(vo.getArticleId());
            like.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
            return  baseMapper.insert(like);
        }else{
            return  baseMapper.delete(new QueryWrapper<ArticleLike>().eq("user_id",StpUtil.getLoginId()).eq("article_id",vo.getArticleId()));
        }
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(ArticleLikeVo vo){
        ArticleLike articleLike=new ArticleLike();
        BeanUtils.copyProperties(vo,articleLike);
        return baseMapper.updateById(articleLike);
    }

    @Override
    public ArticleLike findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<ArticleLikeDto> findListByPage(ArticleLikeQuery query){
        query.setUserId(Long.valueOf((String) StpUtil.getLoginId()));
        IPage<ArticleLikeDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<ArticleLikeDto> iPage = baseMapper.selectArticleLikePage(wherePage,query);
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,iPage.getRecords());
    }
}
