package com.xiaohai.system.dao;

import com.xiaohai.system.pojo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询所对应的角色编码列表
     * @param userId
     * @return
     */
    List<String> listByCodes(Object userId);

    /**
     * 根据用户id查询所对应的角色id列表
     * @param userId
     * @return
     */
    List<Long> listByRoleIds(Object userId);

}
