package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.xiaohai.common.utils.EncryptUtils;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.vo.LoginVo;
import com.xiaohai.system.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Wrapper;

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
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("username",vo.getUsername()).eq("password", EncryptUtils.aesEncrypt(vo.getPassword())));
        Assert.isTrue(user != null, "sss");
        StpUtil.login(user.getId().longValue());
        return StpUtil.getTokenValue();
    }
}
