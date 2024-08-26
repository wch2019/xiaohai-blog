package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohai.common.confing.MailSenderConfig;
import com.xiaohai.common.constant.RedisConstants;
import com.xiaohai.common.utils.EmailUtils;
import com.xiaohai.common.utils.EncryptUtils;
import com.xiaohai.common.utils.RedisUtils;
import com.xiaohai.common.utils.Spring.SpringUtils;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.vo.InitialVo;
import com.xiaohai.system.pojo.vo.LoginVo;
import com.xiaohai.system.pojo.vo.RegisterVo;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.service.ConfigService;
import com.xiaohai.system.pojo.entity.Config;
import com.xiaohai.system.service.LoginService;
import com.xiaohai.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wangchenghai
 * @date 2023/01/18 13:47:03
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    private final UserService serService;

    private final MailSenderConfig mailSenderConfig;

    private final ConfigService configService;


    @Override
    public String login(LoginVo vo) {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .and(i -> i.eq("username", vo.getUsername()).or().eq("email", vo.getUsername()))
                .eq("password", EncryptUtils.aesEncrypt(vo.getPassword())));
        Assert.isTrue(user != null, "用户帐号或者密码错误!");
        Assert.isTrue("0".equals(user.getStatus()), "账号停用，请联系管理员!");

        if (Boolean.TRUE.equals(vo.getRememberMe())) {
            //记住我 7天有效
            StpUtil.login(user.getId().longValue(), new SaLoginModel()
                    .setIsLastingCookie(true)
                    .setTimeout(60 * 60 * 24 * 7));
        } else {
            StpUtil.login(user.getId().longValue());
        }
//        user.setPassword(null);
        // 在登录时缓存user对象
//        StpUtil.getSession().set(Constants.CURRENT_USER,user);
        return StpUtil.getTokenValue();
    }

    @Override
    public String sendEmailCode(String email) {
        sendCode(email);
        return "验证码已发送，请前往邮箱查看!";
    }

    /**
     * 发送邮箱验证码
     * 异步处理
     */
    @Async("syncExecutorPool")
    public void sendCode(String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        EmailUtils.send(mailSenderConfig.getSender(), email, EmailUtils.authCode(code), "DotCode验证码");
        log.info("邮箱验证码发送成功,邮箱:{},验证码:{}", email, code);
        SpringUtils.getBean(RedisUtils.class).setCacheObject(RedisConstants.EMAIL_CODE + email, code, RedisConstants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public Integer register(RegisterVo vo) {
        String code = SpringUtils.getBean(RedisUtils.class).getCacheObject(RedisConstants.EMAIL_CODE + vo.getEmail());
        Assert.isTrue(vo.getCode().equals(code), "验证码不正确!");
        //密码加密
        vo.setPassword(EncryptUtils.aesEncrypt(vo.getPassword()));
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(vo, userVo);
        //普通用户默认2
        Long[] a = {2L};
        userVo.setRoleIds(a);
        return serService.add(userVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer initial(InitialVo vo) {
        ConfigDto configDto=configService.findByOne();
        Assert.isTrue(configDto.getInitial().equals(0), "无法再次初始化！");
        // 初始化
        User user = new User();
        user.setId(1);
        user.setPassword(EncryptUtils.aesEncrypt(vo.getPassword()));
        user.setUsername(vo.getUsername());
        user.setEmail(vo.getEmail());
        userMapper.updateById(user);
        Config config=new Config();
        config.setId(configDto.getId());
        config.setInitial(1);
        config.setName(vo.getSiteName());
        configService.updateById(config);
        return 1;
    }

    @Override
    public Integer uninitialized() {
        ConfigDto configDto=configService.findByOne();
        return configDto.getInitial();
    }
}
