package com.xiaohai.system.service;

import com.xiaohai.system.pojo.vo.LoginVo;

/**
 * @author wangchenghai
 * @date 2023/01/18 13:46:50
 */
public interface LoginService {

    /**
     * 登录
     * @param vo
     * @return
     */
    String login(LoginVo vo);
}
