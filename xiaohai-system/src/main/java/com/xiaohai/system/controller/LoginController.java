package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.system.pojo.vo.LoginVo;
import com.xiaohai.system.pojo.vo.RegisterVo;
import com.xiaohai.system.pojo.vo.RoleVo;
import com.xiaohai.system.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author wangchenghai
 * @date 2023/01/18 13:28:36
 */
@Tag(name = "登录模块",description = "登录模块")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "登录")
    @Log(title = "登录")
    @PostMapping("login")
    public Response<String> login(@Validated @RequestBody LoginVo vo){
        return  Response.success("登录成功！", loginService.login(vo));
    }

    @Operation(summary = "发送邮箱验证码")
    @Log(title = "发送邮箱验证码")
    @GetMapping( "/sendEmailCode")
    public Response<String> sendEmailCode(String email){
        return Response.success(loginService.sendEmailCode(email));
    }

    @Operation(summary = "用户信息注册")
    @Log(title = "用户信息注册")
    @PostMapping("register")
    public Response<Integer> register(@Validated @RequestBody RegisterVo vo){
        return  Response.success("注册成功！",loginService.register(vo));
    }

    @Operation(summary = "退出",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckLogin
    @GetMapping("logout")
    public Response<Object> logout() {
        StpUtil.logout();
        return Response.success("退出成功");
    }
}
