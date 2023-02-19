package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.utils.EncryptUtils;
import com.xiaohai.common.utils.RedisUtils;
import com.xiaohai.common.utils.SpringUtils;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.vo.LoginVo;
import com.xiaohai.system.pojo.vo.RegisterVo;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.service.EmailService;
import com.xiaohai.system.service.LoginService;
import com.xiaohai.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    private final EmailService emailService;


    private final UserService serService;


    @Override
    public String login(LoginVo vo) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", vo.getUsername())
                .or().eq("email", vo.getUsername()).
                eq("password", EncryptUtils.aesEncrypt(vo.getPassword())));
        Assert.isTrue(user != null, "用户帐号或者密码错误!");
        if (Boolean.TRUE.equals(vo.getRememberMe())) {
            //记住我 7天有效
            StpUtil.login(user.getId().longValue(), new SaLoginModel().setIsLastingCookie(true).setTimeout(60 * 60 * 24 * 7));
        } else {
            StpUtil.login(user.getId().longValue());
        }
        user.setPassword(null);
        // 在登录时缓存user对象
        StpUtil.getSession().set(Constants.CURRENT_USER,user);
        return StpUtil.getTokenValue();
    }

    @Override
    public String sendEmailCode(String email) {
        emailService.sendCode(email);
        return "验证码已发送，请前往邮箱查看!";
    }

    @Override
    public Integer register(RegisterVo vo) {
        String code = SpringUtils.getBean(RedisUtils.class).getCacheObject(RedisConstants.EMAIL_CODE + vo.getEmail());
        Assert.isTrue(vo.getCode().equals(code), "验证码不正确!");
        //密码加密
        vo.setPassword(EncryptUtils.aesEncrypt(vo.getPassword()));
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(vo, userVo);
        return serService.add(userVo);
    }
}
