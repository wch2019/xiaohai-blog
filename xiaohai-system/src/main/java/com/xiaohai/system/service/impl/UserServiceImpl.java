package com.xiaohai.system.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.UserQuery;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.pojo.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 用户表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Integer add(UserVo vo){
        User user=new User();
        BeanUtils.copyProperties(vo,user);
        return baseMapper.insert(user);
    }

    @Override
    public Integer delete(Long id){
        return  baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(UserVo vo){
        User user=new User();
        BeanUtils.copyProperties(vo,user);
        return  baseMapper.updateById(user);
    }

    @Override
    public  User findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<UserDto> findListByPage(UserQuery query){
        User user=new User();
        BeanUtils.copyProperties(query,user);
        IPage<User> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<User> iPage = baseMapper.selectPage(wherePage,Wrappers.query(user));
        List<UserDto> list=new ArrayList<>();
        for(User users:iPage.getRecords()){
        UserDto userDto=new UserDto();
        BeanUtils.copyProperties(users,userDto);
        list.add(userDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
