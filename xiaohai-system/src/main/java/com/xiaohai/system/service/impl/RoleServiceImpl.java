package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.system.dao.UserRoleMapper;
import com.xiaohai.system.pojo.entity.Role;
import com.xiaohai.system.dao.RoleMapper;
import com.xiaohai.system.pojo.entity.UserRole;
import com.xiaohai.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.RoleQuery;
import com.xiaohai.system.pojo.vo.RoleVo;
import com.xiaohai.system.pojo.dto.RoleDto;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final UserRoleMapper userRoleMapper;

    @Override
    public Integer add(RoleVo vo) {
        //新增id为空
        vo.setId(null);
        Long codeCount = baseMapper.selectCount(new QueryWrapper<Role>().eq("code", vo.getCode()));
        Assert.isTrue(codeCount == 0, "新增角色：" + vo.getCode() + "失败，角色已存在");
        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        return baseMapper.insert(role);
    }

    @Override
    public Integer delete(Long id) {
        Long codeCount = userRoleMapper.selectCount(new QueryWrapper<UserRole>().eq("role_id",id));
        Assert.isTrue(codeCount != 0, "当前角色存在用户，无法删除");
        return baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(RoleVo vo) {
        Long codeCount = baseMapper.selectCount(new QueryWrapper<Role>().eq("code", vo.getCode()).ne("id", vo.getId()));
        Assert.isTrue(codeCount == 0, "更新角色：" + vo.getCode() + "失败，角色已存在");
        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        return baseMapper.updateById(role);
    }

    @Override
    public Role findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<Role> findListByPage(RoleQuery query) {
        Role role = new Role();
        BeanUtils.copyProperties(query, role);
        IPage<Role> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Role> iPage = baseMapper.selectPage(wherePage, Wrappers.query(role));
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }
}
