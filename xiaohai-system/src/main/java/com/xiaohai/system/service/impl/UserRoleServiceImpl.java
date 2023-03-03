package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.system.dao.UserRoleMapper;
import com.xiaohai.system.pojo.dto.UserRoleDto;
import com.xiaohai.system.pojo.entity.UserRole;
import com.xiaohai.system.pojo.query.UserRoleQuery;
import com.xiaohai.system.pojo.vo.UserRoleVo;
import com.xiaohai.system.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色关联表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public void add(Long[] ids, Integer userId) {
        //写入角色
        for (Long id : ids) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(Math.toIntExact(id));
            baseMapper.insert(userRole);
        }
    }

    @Override
    public Integer delete(Integer userId) {
        return baseMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userId));
    }

    @Override
    public Integer updateData(UserRoleVo vo) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(vo, userRole);
        return baseMapper.updateById(userRole);
    }

    @Override
    public UserRole findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<UserRoleDto> findListByPage(UserRoleQuery query) {
        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(query, userRole);
        IPage<UserRole> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<UserRole> iPage = baseMapper.selectPage(wherePage, Wrappers.query(userRole));
        List<UserRoleDto> list = new ArrayList<>();
        for (UserRole userRoles : iPage.getRecords()) {
            UserRoleDto userRoleDto = new UserRoleDto();
            BeanUtils.copyProperties(userRoles, userRoleDto);
            list.add(userRoleDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public void rewriteUserRole(Long[] ids, Integer userId) {
        //删除角色
        delete(userId);
        //重新写入角色
        add(ids, userId);
    }
}
