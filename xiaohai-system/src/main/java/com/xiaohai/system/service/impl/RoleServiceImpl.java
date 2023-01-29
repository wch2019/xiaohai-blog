package com.xiaohai.system.service.impl;

import com.xiaohai.common.daomain.PageData;
import com.xiaohai.system.pojo.entity.Role;
import com.xiaohai.system.dao.RoleMapper;
import com.xiaohai.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.RoleQuery;
import com.xiaohai.system.pojo.vo.RoleVo;
import com.xiaohai.system.pojo.dto.RoleDto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 角色表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Integer add(RoleVo vo){
        Role role=new Role();
        BeanUtils.copyProperties(vo,role);
        return baseMapper.insert(role);
    }

    @Override
    public Integer delete(Long id){
        return  baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(RoleVo vo){
        Role role=new Role();
        BeanUtils.copyProperties(vo,role);
        return  baseMapper.updateById(role);
    }

    @Override
    public  Role findById(Long id){
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<RoleDto> findListByPage(RoleQuery query){
        Role role=new Role();
        BeanUtils.copyProperties(query,role);
        IPage<Role> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Role> iPage = baseMapper.selectPage(wherePage,Wrappers.query(role));
        List<RoleDto> list=new ArrayList<>();
        for(Role roles:iPage.getRecords()){
        RoleDto roleDto=new RoleDto();
        BeanUtils.copyProperties(roles,roleDto);
        list.add(roleDto);
        }
        PageData pageData=new PageData();
        BeanUtils.copyProperties(iPage,pageData);
        return ReturnPageData.fillingData(pageData,list);
    }
}
