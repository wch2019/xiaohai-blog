package com.xiaohai.note.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.Comment;
import com.xiaohai.note.dao.CommentMapper;
import com.xiaohai.note.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.CommentQuery;
import com.xiaohai.note.pojo.vo.CommentVo;
import com.xiaohai.note.pojo.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 评论表 服务实现类
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Integer add(CommentVo vo){
        Comment comment=new Comment();
        BeanUtils.copyProperties(vo,comment);
        return baseMapper.insert(comment);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(CommentVo vo){
        Comment comment=new Comment();
        BeanUtils.copyProperties(vo,comment);
        return baseMapper.updateById(comment);
    }

    @Override
    public Comment findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<CommentDto> findListByPage(CommentQuery query){
        Comment comment=new Comment();
        BeanUtils.copyProperties(query,comment);
        IPage<Comment> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Comment> iPage = baseMapper.selectPage(wherePage,Wrappers.query(comment));
        List<CommentDto> list=new ArrayList<>();
        for(Comment comments:iPage.getRecords()){
            CommentDto commentDto=new CommentDto();
            BeanUtils.copyProperties(comments,commentDto);
            list.add(commentDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
