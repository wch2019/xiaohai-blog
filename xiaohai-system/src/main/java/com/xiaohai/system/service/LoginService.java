package com.xiaohai.system.service;

import com.xiaohai.system.pojo.vo.InitialVo;
import com.xiaohai.system.pojo.vo.LoginVo;
import com.xiaohai.system.pojo.vo.RegisterVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

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

    /**
     * 用户绑定邮箱，发送验证码
     * @param email
     * @return
     */
    String sendEmailCode(String email);

    /**
     * 注册用户
     * @param vo
     */
    Integer register(RegisterVo vo);

    /**
     * 初始化
     * @param vo
     * @return
     */
    Integer initial(InitialVo vo);

    /**
     * 是否初始化
     * @return
     */
    Integer uninitialized();
}
