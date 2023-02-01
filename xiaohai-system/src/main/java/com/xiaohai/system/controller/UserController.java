package com.xiaohai.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.system.service.UserService;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.query.UserQuery;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.pojo.dto.UserDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 用户表Controller
*
* @author xiaohai
* @since 2023-01-29
*/
@Tag(name = "用户表",description = "用户表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class UserController {

    private final UserService serService;


    @Operation(summary = "新增用户表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@RequestBody UserVo vo){
        return  Response.success("新增用户表成功！", serService.add(vo));
    }

    @Operation(summary = "删除用户表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除用户表成功！",serService.delete(id));
    }

    @Operation(summary = "更新用户表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:user:put")
    @PutMapping()
    public Response<Integer> update(@RequestBody UserVo vo){
        return  Response.success("更新用户表成功！",serService.updateData(vo));
    }


    @Operation(summary = "id查询用户表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<User> findById(@PathVariable Long id){
        return  Response.success("id查询用户表成功！",serService.findById(id));
    }

    @Operation(summary = "查询用户表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<UserDto>> findListByPage(UserQuery query){
        return Response.success("查询用户表列表成功！",serService.findListByPage(query));
    }

    }
