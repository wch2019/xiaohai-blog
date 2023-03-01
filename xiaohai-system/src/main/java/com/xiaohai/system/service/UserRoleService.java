package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.UserRoleQuery;
import com.xiaohai.system.pojo.vo.UserRoleVo;
import com.xiaohai.system.pojo.dto.UserRoleDto;

import java.util.List;

/**
 * 用户角色关联表 服务类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface UserRoleService extends IService<UserRole> {


    /**
     * 添加用户角色关联
     *
     * @param ids    新增角色id
     * @param userId 用户id
     */
    void add(Long[] ids, Integer userId);

    /**
     * 删除用户角色关联
     *
     * @param userId 主键
     * @return Integer
     */
    Integer delete(Integer userId);

    /**
     * 修改用户角色关联表
     *
     * @param vo 用户角色关联表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(UserRoleVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return Integer
     */
    UserRole findById(Long id);

    /**
     * 查询用户角色关联表列表数据
     *
     * @param query 用户角色关联表 Query 数据查询对象
     * @return Response
     */
    ReturnPageData<UserRoleDto> findListByPage(UserRoleQuery query);


    /**
     * 更新角色  删除角色再新增角色
     *
     * @param ids    新增角色id
     * @param userId 用户id
     */
    void rewriteUserRole(Long[] ids, Integer userId);
}
