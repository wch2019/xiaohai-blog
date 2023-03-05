package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.MenuTree;
import com.xiaohai.common.utils.ListUtils;
import com.xiaohai.common.utils.StringUtils;
import com.xiaohai.common.utils.TreeUtils;
import com.xiaohai.system.dao.MenuMapper;
import com.xiaohai.system.dao.RoleMapper;
import com.xiaohai.system.pojo.dto.MetaDto;
import com.xiaohai.system.pojo.dto.RouterDto;
import com.xiaohai.system.pojo.entity.Menu;
import com.xiaohai.system.pojo.query.MenuQuery;
import com.xiaohai.system.pojo.vo.MenuVo;
import com.xiaohai.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 菜单权限表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    private final RoleMapper roleMapper;

    @Override
    public Integer add(MenuVo vo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(vo, menu);
        return baseMapper.insert(menu);
    }

    @Override
    public Integer delete(Long id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public Integer updateData(MenuVo vo) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(vo, menu);
        return baseMapper.updateById(menu);
    }

    @Override
    public Menu findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<MenuTree> findListByPage(MenuQuery query) {
        List<Menu> menus = baseMapper.selectList(new QueryWrapper<Menu>()
                .eq(StringUtils.isNotBlank(query.getStatus()), "status", query.getStatus())
                .like(StringUtils.isNotBlank(query.getMenuName()), "menu_name", query.getMenuName())
                .last(" order by menu_sort asc"));
        List<MenuTree> menuTrees = ListUtils.copyWithCollection(menus, MenuTree.class);
        return TreeUtils.getTree(menuTrees);
    }

    @Override
    public List<RouterDto> routers() {
        List<Menu> menus = new ArrayList<>();
        //获取角色
        List<Long> ids = roleMapper.listByRoleIds(StpUtil.getLoginId());
        for (Long id : ids) {
            menus.addAll(baseMapper.listByMenus(id));
        }
        //获取菜单
        List<MenuTree> menuTrees = ListUtils.copyWithCollection(menus, MenuTree.class);
        List<MenuTree> list = TreeUtils.getTree(menuTrees);

        List<RouterDto> routers = new LinkedList<RouterDto>();
        return buildMenus(list);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param list
     * @return java.util.List<com.xiaohai.system.pojo.dto.RouterDto>
     * @author: xiaohai
     * @date: 2023/3/4 15:55
     */

    public List<RouterDto> buildMenus(List<MenuTree> list) {
        List<RouterDto> routers = new LinkedList<>();
        for (MenuTree menu : list) {
            RouterDto router = new RouterDto();
            router.setHidden(false);
            router.setName(menu.getMenuName());
            router.setPath(menu.getPath());
            router.setComponent(StringUtils.isNotBlank(menu.getComponent()) ? menu.getComponent() : "Layout");
            router.setMeta(new MetaDto(menu.getMenuName(), menu.getIcon(), true));
            List<MenuTree> cMenus = menu.getChildren();
            if (!cMenus.isEmpty()) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }
}
