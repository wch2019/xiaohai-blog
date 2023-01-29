package com.xiaohai.system.service.impl;

import com.xiaohai.common.daomain.PageData;
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
    public ReturnPageData<MenuDto> findListByPage(MenuQuery query) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(query, menu);
        IPage<Menu> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<Menu> iPage = baseMapper.selectPage(wherePage, Wrappers.query(menu));
        List<MenuDto> list = new ArrayList<>();
        for (Menu menus : iPage.getRecords()) {
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(menus, menuDto);
            list.add(menuDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }
}
