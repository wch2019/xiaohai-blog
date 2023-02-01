package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.utils.EncryptUtils;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.vo.LoginVo;
import com.xiaohai.system.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author wangchenghai
 * @date 2023/01/18 13:47:03
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    @Override
    public String login(LoginVo vo) {
        User user=userMapper.selectOne(new QueryWrapper<User>()
                .eq("username",vo.getUsername())
                .or().eq("email",vo.getUsername()).
                eq("password", EncryptUtils.aesEncrypt(vo.getPassword())));
        Assert.isTrue(user != null, "用户帐号或者密码错误!");
        if (vo.getRememberMe()) {
            //记住我 7天有效
            StpUtil.login(user.getId().longValue(),new SaLoginModel().setIsLastingCookie(true).setTimeout(60 * 60 * 24 * 7));
        }else {
            StpUtil.login(user.getId().longValue());
        }
        // 在登录时缓存user对象
        StpUtil.getSession().set(Constants.CURRENT_USER,userMapper.selectById(user.getId()));
        return StpUtil.getTokenValue();
    }
}
