package com.xiaohai.system.service;

import com.xiaohai.common.daomain.MenuTree;
import com.xiaohai.system.pojo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.MenuQuery;
import com.xiaohai.system.pojo.vo.MenuVo;
import com.xiaohai.system.pojo.dto.MenuDto;

import java.util.List;

/**
 *
 * 菜单权限表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface MenuService extends IService<Menu> {


    /**
     * 添加菜单权限表
     *
     * @param vo 菜单权限表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(MenuVo vo);

    /**
     * 删除菜单权限表
     *
     * @param id 主键
     * @return Integer
     */
    Integer delete(Long id);

    /**
     * 修改菜单权限表
     *
     * @param vo 菜单权限表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(MenuVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    Menu findById(Long id);

    /**
    * 查询菜单权限表列表数据
    *
    * @param query 菜单权限表 Query 数据查询对象
    * @return Response
    */
    List<MenuTree> findListByPage(MenuQuery query);
}
