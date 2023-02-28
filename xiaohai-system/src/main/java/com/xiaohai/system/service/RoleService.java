package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.RoleQuery;
import com.xiaohai.system.pojo.vo.RoleVo;
import com.xiaohai.system.pojo.dto.RoleDto;

/**
 *
 * 角色表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface RoleService extends IService<Role> {


    /**
     * 添加角色表
     *
     * @param vo 角色表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(RoleVo vo);

    /**
     * 删除角色表
     *
     * @param id 主键
     * @return Integer
     */
    Integer delete(Long id);

    /**
     * 修改角色表
     *
     * @param vo 角色表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(RoleVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    Role findById(Long id);

    /**
    * 查询角色表列表数据
    *
    * @param query 角色表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<Role> findListByPage(RoleQuery query);
}
