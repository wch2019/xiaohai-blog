package com.xiaohai.system.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.daomain.MenuTree;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.utils.ListUtils;
import com.xiaohai.common.utils.StringUtils;
import com.xiaohai.common.utils.TreeUtils;
import com.xiaohai.system.pojo.entity.Menu;
import com.xiaohai.system.dao.MenuMapper;
import com.xiaohai.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.system.pojo.query.MenuQuery;
import com.xiaohai.system.pojo.vo.MenuVo;
import com.xiaohai.system.pojo.dto.MenuDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

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
                        .eq(StringUtils.isNotBlank(query.getStatus()),"status",query.getStatus())
                        .like(StringUtils.isNotBlank(query.getMenuName()),"menu_name",query.getMenuName())
                        .last(" order by menu_sort asc"));
        List<MenuTree> menuTrees=ListUtils.copyWithCollection(menus,MenuTree.class);
        return TreeUtils.getTree(menuTrees);
    }
}
