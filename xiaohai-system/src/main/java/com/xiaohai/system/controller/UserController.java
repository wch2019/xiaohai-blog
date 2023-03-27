package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.enums.BusinessType;
import com.xiaohai.common.utils.EncryptUtils;
import com.xiaohai.system.pojo.dto.UserDto;
import com.xiaohai.system.pojo.query.UserQuery;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户表Controller
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Tag(name = "用户表", description = "用户表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "获取用户登录信息", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/info")
    public Response<Map<String, Object>> findByInfo() {
        return Response.success("获取用户登录信息成功！", userService.findByInfo());
    }

    @Operation(summary = "新增用户表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:user:add")
    @Log(title = "用户模块", businessType = BusinessType.INSERT)
    @PostMapping()
    public Response<Integer> add(@Validated @RequestBody UserVo vo) {
        //密码默认
        vo.setPassword(EncryptUtils.aesEncrypt(Constants.SYSTEM_PASSWORD));
        return Response.success("新增用户表成功！", userService.add(vo));
    }

    @Operation(summary = "删除用户表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:user:delete")
    @Log(title = "用户模块", businessType = BusinessType.DELETE)
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids) {
        return Response.success("删除用户表成功！", userService.delete(ids));
    }

    @Operation(summary = "更新用户表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:user:update")
    @Log(title = "用户模块", businessType = BusinessType.UPDATE)
    @PutMapping()
    public Response<Integer> update(@Validated @RequestBody UserVo vo) {
        return Response.success("更新用户表成功！", userService.updateData(vo));
    }


    @Operation(summary = "id查询用户表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<UserDto> findById(@PathVariable Long id) {
        return Response.success("id查询用户表成功！", userService.findById(id));
    }

    @Operation(summary = "查询用户表列表数据", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("system:user:list")
    @GetMapping()
    public Response<ReturnPageData<UserDto>> findListByPage(UserQuery query) {
        return Response.success("查询用户表列表成功！", userService.findListByPage(query));
    }

    @Operation(summary = "修改密码", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "oldPassword", description = "旧密码", required = true)
    @Parameter(name = "newPassword", description = "新密码", required = true)
    @Log(title = "用户模块", businessType = BusinessType.UPDATE)
    @PutMapping("/password")
    public Response<Integer> updatePwd(String oldPassword,String newPassword) {
        return Response.success("修改密码成功！", userService.updatePwd(oldPassword,newPassword));
    }
    @Operation(summary = "修改邮箱", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "newEmail", description = "新邮箱", required = true)
    @Parameter(name = "code", description = "验证码", required = true)
    @Log(title = "用户模块", businessType = BusinessType.UPDATE)
    @PutMapping("/email")
    public Response<Integer> updateEmail(String newEmail,String code) {
        return Response.success("修改邮箱成功！", userService.updateEmail(newEmail,code));
    }
}
