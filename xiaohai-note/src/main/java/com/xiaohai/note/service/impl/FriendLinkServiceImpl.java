package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.note.pojo.entity.FriendLink;
import com.xiaohai.note.dao.FriendLinkMapper;
import com.xiaohai.note.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.FriendLinkQuery;
import com.xiaohai.note.pojo.vo.FriendLinkVo;
import com.xiaohai.note.pojo.dto.FriendLinkDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 友情链接表 服务实现类
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Override
    public Integer add(FriendLinkVo vo){
        //普通用户不可动参数
        //判断角色是否是管理员
        if(!StpUtil.hasRole(Constants.ADMIN)){
            vo.setSort(0);
            vo.setReason("");
            vo.setStatus(0);
        }
        FriendLink friendLink=new FriendLink();
        BeanUtils.copyProperties(vo,friendLink);
        return baseMapper.insert(friendLink);
    }

    @Override
    public Integer delete(Long[] ids){
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(FriendLinkVo vo){
        //普通用户不可动参数
        //判断角色是否是管理员
        if(!StpUtil.hasRole(Constants.ADMIN)){
            vo.setSort(null);
            vo.setReason(null);
            vo.setStatus(null);
        }
        FriendLink friendLink=new FriendLink();
        BeanUtils.copyProperties(vo,friendLink);
        return baseMapper.updateById(friendLink);
    }

    @Override
    public FriendLink findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<FriendLinkDto> findListByPage(FriendLinkQuery query){
        FriendLink friendLink=new FriendLink();
        BeanUtils.copyProperties(query,friendLink);
        IPage<FriendLink> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<FriendLink> iPage = baseMapper.selectPage(wherePage,Wrappers.query(friendLink));
        List<FriendLinkDto> list=new ArrayList<>();
        for(FriendLink friendLinks:iPage.getRecords()){
            FriendLinkDto friendLinkDto=new FriendLinkDto();
            BeanUtils.copyProperties(friendLinks,friendLinkDto);
            list.add(friendLinkDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }

    @Override
    public List<FriendLinkDto> findList() {
        List<FriendLink> list = baseMapper.selectList(new QueryWrapper<FriendLink>().eq("status",1).orderByDesc("sort"));
        List<FriendLinkDto> dtoList = new ArrayList<>(list.size());
        for (FriendLink friendLink : list) {
            FriendLinkDto friendLinkDto = new FriendLinkDto();
            BeanUtils.copyProperties(friendLink,friendLinkDto);
            dtoList.add(friendLinkDto);
        }
        return dtoList;
    }
}
