package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.FriendLink;
import com.xiaohai.note.pojo.vo.NotificationsVo;
import com.xiaohai.note.service.NotificationsService;
import com.xiaohai.system.pojo.entity.Feedback;
import com.xiaohai.system.dao.FeedbackMapper;
import com.xiaohai.system.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.FeedbackQuery;
import com.xiaohai.system.pojo.vo.FeedbackVo;
import com.xiaohai.system.pojo.dto.FeedbackDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户反馈 服务实现类
 *
 * @author xiaohai
 * @since 2023-08-03
 */
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    private final NotificationsService notificationsService;

    @Override
    public Integer add(FeedbackVo vo) {
        //普通用户不可动参数
        //判断角色是否是管理员
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            vo.setReason("");
            vo.setStatus("0");
        }
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(vo, feedback);
        feedback.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        Integer count = baseMapper.insert(feedback);
        // 反馈通知
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            NotificationsVo notificationsVo = new NotificationsVo();
            notificationsVo.setUserId(1);
            notificationsVo.setType("3");
            notificationsVo.setFeedbackId(feedback.getId());
            notificationsVo.setRemark("有新的反馈");
            notificationsService.add(notificationsVo);
        }
        return count;
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(FeedbackVo vo) {
        //普通用户不可动参数
        //判断角色是否是管理员
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            vo.setReason(null);
            vo.setStatus(null);
        }
        Feedback feed = baseMapper.selectById(vo.getId());
        Feedback feedback = new Feedback();
        BeanUtils.copyProperties(vo, feedback);
        Integer count = baseMapper.updateById(feedback);
        //管理员修改状态推送
        if (StpUtil.hasRole(Constants.ADMIN)) {
            if (!feedback.getStatus().equals(feed.getStatus()) && !feed.getStatus().equals("0")) {
                NotificationsVo notificationsVo = new NotificationsVo();
                notificationsVo.setUserId(feed.getUserId());
                notificationsVo.setType("3");
                notificationsVo.setFeedbackId(feed.getId());
                if (feed.getStatus().equals("1")) {
                    notificationsVo.setRemark("反馈申请已通过");
                }
                if (feed.getStatus().equals("2")) {
                    notificationsVo.setRemark("反馈申请未通过");
                }
                notificationsService.add(notificationsVo);
            }
        }
        return count;
    }

    @Override
    public Feedback findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<FeedbackDto> findListByPage(FeedbackQuery query) {
        //不是管理员查询用户自己的
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            query.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        }
        IPage<FeedbackDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<FeedbackDto> iPage = baseMapper.findFeedbackListByPage(wherePage, query);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }
}
