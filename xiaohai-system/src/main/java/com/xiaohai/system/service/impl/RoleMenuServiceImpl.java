package com.xiaohai.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.system.dao.RoleMenuMapper;
import com.xiaohai.system.pojo.dto.RoleMenuDto;
import com.xiaohai.system.pojo.entity.RoleMenu;
import com.xiaohai.system.pojo.query.RoleMenuQuery;
import com.xiaohai.system.pojo.vo.RoleMenuVo;
import com.xiaohai.system.service.RoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限关联表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void add(Long[] ids, Integer roleId) {
        //写入菜單
        for (Long id : ids) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(Math.toIntExact(id));
            roleMenu.setRoleId(roleId);
            baseMapper.insert(roleMenu);
        }
    }

    @Override
    public Integer delete(Integer roleId) {
        return baseMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
    }

    @Override
    public Integer updateData(RoleMenuVo vo) {
        RoleMenu roleMenu = new RoleMenu();
        BeanUtils.copyProperties(vo, roleMenu);
        return baseMapper.updateById(roleMenu);
    }

    @Override
    public RoleMenu findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<RoleMenuDto> findListByPage(RoleMenuQuery query) {
        RoleMenu roleMenu = new RoleMenu();
        BeanUtils.copyProperties(query, roleMenu);
        IPage<RoleMenu> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<RoleMenu> iPage = baseMapper.selectPage(wherePage, Wrappers.query(roleMenu));
        List<RoleMenuDto> list = new ArrayList<>();
        for (RoleMenu roleMenus : iPage.getRecords()) {
            RoleMenuDto roleMenuDto = new RoleMenuDto();
            BeanUtils.copyProperties(roleMenus, roleMenuDto);
            list.add(roleMenuDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public void rewriteRoleMenu(Long[] ids, Integer roleId) {
        //删除菜单
        delete(roleId);
        //重新写入菜单
        add(ids,roleId);
    }
}
