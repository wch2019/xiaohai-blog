package com.xiaohai.common.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.constant.Constants;

/**
 * @author xiaohai
 * @description: 用户信息工具
 * @date 2024-05-30 23:33
 **/
public class RoleUtils {
    /**
     * 角色验证
     * @return
     */
    public static boolean checkRole() {
        return !StpUtil.hasRole(Constants.ADMIN) && !StpUtil.hasRole(Constants.DEMO);
    }
}
