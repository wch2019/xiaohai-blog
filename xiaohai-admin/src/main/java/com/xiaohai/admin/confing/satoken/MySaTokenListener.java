package com.xiaohai.admin.confing.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.utils.OnLineUtils;
import com.xiaohai.common.utils.ip.AddressUtils;
import com.xiaohai.common.utils.ip.IpUtils;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.entity.User;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 自定义侦听器的实现 
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MySaTokenListener implements SaTokenListener {
    private final UserMapper userMapper;

    private final HttpServletRequest request;
    /** 每次登录时触发 */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        User user=userMapper.selectById((Serializable) loginId);
        User userNew=new User();
        userNew.setId(user.getId());
        userNew.setLoginCount(user.getLoginCount()+1);
        userNew.setLoginIp(IpUtils.getIpAddr(request));
        userNew.setLoginSource(AddressUtils.getRealAddressByIP(userNew.getLoginIp()));
        //获取UA信息
        UserAgent userAgent = IpUtils.getUserAgent(request);
        userNew.setLoginOs(userAgent.getOperatingSystem().getName());
        userNew.setLoginBrowser(userAgent.getBrowser().getName());
        userNew.setLoginDate(LocalDateTime.now());
        //修改登录信息
        userMapper.updateById(userNew);
        //存储在线用户信息
        OnLineUser onLineUser=new OnLineUser();
        BeanUtils.copyProperties(userNew,onLineUser);
        onLineUser.setUsername(user.getUsername());
        onLineUser.setNickName(user.getNickName());
        onLineUser.setLogoutDate(onLineUser.getLoginDate().plus(loginModel.getCookieTimeout(),ChronoUnit.SECONDS));
        onLineUser.setTime(loginModel.getCookieTimeout());
        OnLineUtils.setOnLineCache(loginId.toString(),onLineUser);
        log.info("用户已登录,useId:{},token:{}", loginId, tokenValue);
    }

    /** 每次注销时触发 */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        log.info("---------- 自定义侦听器实现 doLogout");
    }

    /** 每次被踢下线时触发 */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        log.info("---------- 自定义侦听器实现 doKickout");
    }

    /** 每次被顶下线时触发 */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        log.info("---------- 自定义侦听器实现 doReplaced");
    }

    /** 每次被封禁时触发 */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
        log.info("---------- 自定义侦听器实现 doDisable");
    }

    /** 每次被解封时触发 */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
        log.info("---------- 自定义侦听器实现 doUntieDisable");
    }

    /** 每次二级认证时触发 */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
        log.info("---------- 自定义侦听器实现 doOpenSafe");
    }

    /** 每次退出二级认证时触发 */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
        log.info("---------- 自定义侦听器实现 doCloseSafe");
    }

    /** 每次创建Session时触发 */
    @Override
    public void doCreateSession(String id) {
        log.info("---------- 自定义侦听器实现 doCreateSession");
    }

    /** 每次注销Session时触发 */
    @Override
    public void doLogoutSession(String id) {
        log.info("---------- 自定义侦听器实现 doLogoutSession");
    }

    /** 每次Token续期时触发 */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
        log.info("---------- 自定义侦听器实现 doRenewTimeout");
    }
}
