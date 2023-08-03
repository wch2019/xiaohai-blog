package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.system.pojo.entity.Feedback;
import com.xiaohai.system.dao.FeedbackMapper;
import com.xiaohai.system.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.FeedbackQuery;
import com.xiaohai.system.pojo.vo.FeedbackVo;
import com.xiaohai.system.pojo.dto.FeedbackDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 用户反馈 服务实现类
 *
 * @author xiaohai
 * @since 2023-08-03
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public Integer add(FeedbackVo vo){
        //普通用户不可动参数
        //判断角色是否是管理员
        if(!StpUtil.hasRole(Constants.ADMIN)){
            vo.setReason("");
            vo.setStatus("0");
        }
        Feedback feedback=new Feedback();
        BeanUtils.copyProperties(vo,feedback);
        feedback.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));

        return baseMapper.insert(feedback);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(FeedbackVo vo){
        //普通用户不可动参数
        //判断角色是否是管理员
        if(!StpUtil.hasRole(Constants.ADMIN)){
            vo.setReason(null);
            vo.setStatus(null);
        }
        Feedback feedback=new Feedback();
        BeanUtils.copyProperties(vo,feedback);
        return baseMapper.updateById(feedback);
    }

    @Override
    public Feedback findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<FeedbackDto> findListByPage(FeedbackQuery query){
        //不是管理员查询用户自己的
        if(!StpUtil.hasRole(Constants.ADMIN)){
            query.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        }
        IPage<FeedbackDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<FeedbackDto> iPage = baseMapper.findFeedbackListByPage(wherePage,query);
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,iPage.getRecords());
    }
}
