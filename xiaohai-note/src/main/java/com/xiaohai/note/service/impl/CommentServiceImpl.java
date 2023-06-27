package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.CommentTree;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.common.utils.TreeUtils;
import com.xiaohai.note.dao.CommentMapper;
import com.xiaohai.note.pojo.dto.CommentCountDto;
import com.xiaohai.note.pojo.dto.CommentDto;
import com.xiaohai.note.pojo.entity.Comment;
import com.xiaohai.note.pojo.query.CommentQuery;
import com.xiaohai.note.pojo.vo.CommentVo;
import com.xiaohai.note.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论表 服务实现类
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Integer add(CommentVo vo) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(vo, comment);
        //当前登录人id
        comment.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        // 存在父id就去获取父评论用户
        if (vo.getParentId() != null) {
            comment.setReplyUserId(baseMapper.selectById(vo.getParentId()).getUserId());
        }
        comment.setCreatedTime(LocalDateTime.now());
        return baseMapper.insert(comment);
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(CommentVo vo) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(vo, comment);
        return baseMapper.updateById(comment);
    }

    @Override
    public Comment findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<CommentDto> findListByPage(CommentQuery query) {
        //当前登录用户
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        //判断角色是否是管理员
        if (StpUtil.hasRole(Constants.ADMIN)) {
            query.setUserRole(true);
        }
        IPage<CommentDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<CommentDto> iPage = baseMapper.findCommentListByPage(wherePage, query, userId);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public CommentCountDto findByArticleId(Long id) {
        CommentCountDto commentCountDto=new CommentCountDto();
        List<CommentTree> commentList = baseMapper.findCommentList(id);
        commentCountDto.setCommentCount(commentList.size());
        commentCountDto.setCommentTrees(TreeUtils.getCommentTree(commentList));
        return commentCountDto;
    }
}
