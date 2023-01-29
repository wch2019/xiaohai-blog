package com.xiaohai.admin.confing.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.xiaohai.system.dao.MenuMapper;
import com.xiaohai.system.dao.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 *
 * @author wangchenghai
 * @date 2023/01/29 16:25:16
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> list=new ArrayList<>();
        //用户对应的角色id列表
        List<Long> roleIds=roleMapper.listByRoleIds(loginId);
        for(Long roleId:roleIds){
            //角色对应权限
            list.addAll(menuMapper.selectByPerms(roleId));
        }
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleMapper.listByCodes(loginId);
    }
}
