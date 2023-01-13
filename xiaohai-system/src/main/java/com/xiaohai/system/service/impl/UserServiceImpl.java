package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.system.pojo.entity.UserEntity;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.MapUtils;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 *
 * 用户表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public Integer add(UserEntity userEntity){
        return baseMapper.insert(userEntity);
    }

    @Override
    public Integer delete(Long id){
        return  baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(UserEntity userEntity){
        return  baseMapper.updateById(userEntity);
    }

    @Override
    public  UserEntity findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<UserEntity> findListByPage(UserEntity userEntity){
        IPage<UserEntity> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<UserEntity> iPage = baseMapper.selectPage(wherePage, Wrappers.query(userEntity));
        return ReturnPageData.fillingData(iPage);
    }
}
