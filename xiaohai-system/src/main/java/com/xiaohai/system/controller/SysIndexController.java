package com.xiaohai.system.controller;

import com.xiaohai.common.confing.BlogConfig;
import com.xiaohai.common.daomain.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaohai
 * @description: 系统信息
 * @date 2024-03-08 10:39
 **/
@Tag(name = "系统信息", description = "系统信息")
@RestController
@RequiredArgsConstructor
public class SysIndexController {

    private final BlogConfig blogConfig;

    @GetMapping("/index")
    public String index() {
        return "欢迎使用" + blogConfig.getName() + "后台管理框架，当前版本：v " + blogConfig.getVersion() + "，请通过前端地址访问。";
    }
    @GetMapping("/version")
    public Response<BlogConfig> version() {
        return Response.success("获取版本信息成功！",blogConfig);
    }
}
