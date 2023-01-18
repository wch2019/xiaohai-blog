package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.RoleMenuQuery;
import com.xiaohai.system.pojo.vo.RoleMenuVo;
import com.xiaohai.system.pojo.dto.RoleMenuDto;

/**
 *
 * 角色权限关联表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-01-18
 */
public interface RoleMenuService extends IService<RoleMenu> {


    /**
     * 添加角色权限关联表
     *
     * @param vo 角色权限关联表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(RoleMenuVo vo);

    /**
     * 删除角色权限关联表
     *
     * @param id 主键
     * @return Integer
     */
    Integer delete(Long id);

    /**
     * 修改角色权限关联表
     *
     * @param vo 角色权限关联表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(RoleMenuVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    RoleMenu findById(Long id);

    /**
    * 查询角色权限关联表列表数据
    *
    * @param query 角色权限关联表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<RoleMenuDto> findListByPage(RoleMenuQuery query);
}
