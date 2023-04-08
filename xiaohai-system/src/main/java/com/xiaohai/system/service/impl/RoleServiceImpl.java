package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.common.utils.StringUtils;
import com.xiaohai.system.dao.MenuMapper;
import com.xiaohai.system.dao.RoleMapper;
import com.xiaohai.system.dao.UserRoleMapper;
import com.xiaohai.system.pojo.dto.RoleDto;
import com.xiaohai.system.pojo.entity.Role;
import com.xiaohai.system.pojo.entity.UserRole;
import com.xiaohai.system.pojo.query.RoleQuery;
import com.xiaohai.system.pojo.vo.RoleVo;
import com.xiaohai.system.service.RoleMenuService;
import com.xiaohai.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final RoleMenuService roleMenuService;
    private final MenuMapper menuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(RoleVo vo) {
        //新增id为空
        vo.setId(null);
        Long codeCount = baseMapper.selectCount(new QueryWrapper<Role>().eq("code", vo.getCode()));
        Assert.isTrue(codeCount == 0, "新增角色：" + vo.getCode() + "失败，角色已存在");
        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        var count = baseMapper.insert(role);
        //添加菜单权限
        roleMenuService.add(vo.getMenuIds(), role.getId());
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            Long codeCount = userRoleMapper.selectCount(new QueryWrapper<UserRole>().eq("role_id", id));
            Assert.isTrue(codeCount == 0, "当前角色存在用户，无法删除");
            roleMenuService.delete(Math.toIntExact(id));
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateData(RoleVo vo) {
        Long codeCount = baseMapper.selectCount(new QueryWrapper<Role>().eq("code", vo.getCode()).ne("id", vo.getId()));
        Assert.isTrue(codeCount == 0, "更新角色：" + vo.getCode() + "失败，角色已存在");
        Role role = new Role();
        BeanUtils.copyProperties(vo, role);
        roleMenuService.rewriteRoleMenu(vo.getMenuIds(),role.getId());
        return baseMapper.updateById(role);
    }

    @Override
    public RoleDto findById(Long id) {
        Role role=baseMapper.selectById(id);
        RoleDto roleDto=new RoleDto();
        BeanUtils.copyProperties(role,roleDto);
        roleDto.setMenuIds(menuMapper.listByMenuIds(id));
        return roleDto;
    }

    @Override
    public ReturnPageData<Role> findListByPage(RoleQuery query) {
        IPage<Role> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Role> iPage = baseMapper.selectPage(wherePage,new QueryWrapper<Role>()
                .eq(StringUtils.isNotBlank(query.getStatus()),"status",query.getStatus())
                .like(StringUtils.isNotBlank(query.getName()),"name",query.getName()));
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public List<RoleDto> optionSelect() {
        List<Role> roles = baseMapper.selectList(new QueryWrapper<Role>().eq("status", 0));
        List<RoleDto> list = new ArrayList<>();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            list.add(roleDto);
        }
        return list;
    }
}
