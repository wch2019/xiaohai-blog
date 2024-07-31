package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.CommentTree;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.common.utils.RoleUtils;
import com.xiaohai.common.utils.TreeUtils;
import com.xiaohai.note.dao.ArticleMapper;
import com.xiaohai.note.dao.CommentMapper;
import com.xiaohai.note.pojo.dto.CommentCountDto;
import com.xiaohai.note.pojo.dto.CommentDto;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.entity.Comment;
import com.xiaohai.note.pojo.query.CommentQuery;
import com.xiaohai.note.pojo.vo.CommentVo;
import com.xiaohai.note.pojo.vo.NotificationsVo;
import com.xiaohai.note.service.CommentService;
import com.xiaohai.note.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论表 服务实现类
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final NotificationsService notificationsService;
    private final ArticleMapper articleMapper;
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
        var count=baseMapper.insert(comment);
        // 评论消息推送
        NotificationsVo notificationsVo=new NotificationsVo();
        if(vo.getArticleId()!=0){
            //文章作者通知
            Article article =articleMapper.selectById(vo.getArticleId());
            notificationsVo.setUserId(article.getUserId());
        }else {
            notificationsVo.setUserId(1);
        }
        notificationsVo.setArticleId(vo.getArticleId());
        notificationsVo.setCommentId(comment.getId());
        notificationsVo.setType("2");
        notificationsService.add(notificationsVo);
        if(comment.getReplyUserId()!=null){
            // 回复作者通知
            notificationsVo.setUserId(comment.getReplyUserId());
            notificationsService.add(notificationsVo);
        }
        // TODO 评论通知，不完善后期还需修改
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            baseMapper.deleteById(id);
            //对应的评论一起删掉
            List<Comment> commentList = baseMapper.selectList(new QueryWrapper<Comment>().select("id").eq("parent_id", id));
            baseMapper.delete(new QueryWrapper<Comment>().eq("parent_id", id));
            Long[] commentIds = new Long[commentList.size()];
            for (int i = 0; i < commentList.size(); i++) {
                Comment comment = commentList.get(i);
                commentIds[i] = Long.valueOf(comment.getId());
            }
            delete(commentIds);
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
    public List<CommentDto> findById(Long id) {
        return findCommentList(Math.toIntExact(id));
    }
    private List<CommentDto> findCommentList(Integer id) {
        List<CommentDto> commentList =  baseMapper.findCommentParentId(id);
        for(CommentDto comment : commentList){
            commentList.addAll(findCommentList(comment.getId()));
        }
        return commentList;
    }

    @Override
    public ReturnPageData<CommentDto> findListByPage(CommentQuery query) {
        Integer userId = query.getUserId();
        //判断角色是否是管理员和demo
        if (RoleUtils.checkRole()&&userId==null) {
            //不是管理员、demo只查询当前用户数据
            userId = Integer.valueOf((String) StpUtil.getLoginId());
        }
        IPage<CommentDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<CommentDto> iPage = baseMapper.findCommentListByPage(wherePage, query, userId);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public CommentCountDto findByArticleId(Long id) {
        CommentCountDto commentCountDto = new CommentCountDto();
        List<CommentTree> commentList = baseMapper.findCommentList(id);
        commentCountDto.setCommentCount(commentList.size());
        commentCountDto.setCommentTrees(TreeUtils.getCommentTree(commentList));
        return commentCountDto;
    }
}
