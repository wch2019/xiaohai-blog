package com.xiaohai.note.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.dao.TagsMapper;
import com.xiaohai.note.service.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.TagsQuery;
import com.xiaohai.note.pojo.vo.TagsVo;
import com.xiaohai.note.pojo.dto.TagsDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 标签表 服务实现类
 *
 * @author xiaohai
 * @since 2023-03-31
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Override
    public Integer add(TagsVo vo){
        Tags tags=new Tags();
        BeanUtils.copyProperties(vo,tags);
        return baseMapper.insert(tags);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(TagsVo vo){
        Tags tags=new Tags();
        BeanUtils.copyProperties(vo,tags);
        return baseMapper.updateById(tags);
    }

    @Override
    public Tags findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<TagsDto> findListByPage(TagsQuery query){
        Tags tags=new Tags();
        BeanUtils.copyProperties(query,tags);
        IPage<Tags> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Tags> iPage = baseMapper.selectPage(wherePage,Wrappers.query(tags).orderByAsc("sort"));
        List<TagsDto> list=new ArrayList<>();
        for(Tags tagss:iPage.getRecords()){
            TagsDto tagsDto=new TagsDto();
            BeanUtils.copyProperties(tagss,tagsDto);
            list.add(tagsDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
