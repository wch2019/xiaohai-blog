package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.system.pojo.dto.ConfigDto;
import com.xiaohai.system.pojo.vo.ConfigVo;
import com.xiaohai.system.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置Controller
 *
 * @author xiaohai
 * @since 2023-02-01
 */
@Tag(name = "系统配置", description = "系统配置")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/config")
public class ConfigController {

    private final ConfigService configService;


    @Operation(summary = "新增系统配置", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:config:save")
    @Log(title = "新增系统配置")
    @PostMapping()
    public Response<Integer> add(@RequestBody ConfigVo vo) {
        return Response.success("新增系统配置成功！", configService.add(vo));
    }

    @Operation(summary = "更新系统配置", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:config:save")
    @Log(title = "更新系统配置")
    @PutMapping()
    public Response<Integer> update(@RequestBody ConfigVo vo) {
        return Response.success("更新系统配置成功！", configService.updateData(vo));
    }


    @Operation(summary = "查询系统配置", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:config:select")
    @GetMapping()
    public Response<ConfigDto> findByOne() {
        return Response.success("查询系统配置成功！", configService.findByOne());
    }

}
