package com.xiaohai.system.dao;

import com.xiaohai.system.pojo.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-01-29
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色id获取当前角色权限编码列表
     * @param roleId
     * @return
     */
    List<String> selectByPerms(Long roleId);

}
