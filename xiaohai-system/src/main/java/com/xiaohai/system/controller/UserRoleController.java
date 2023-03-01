package com.xiaohai.system.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.system.service.UserRoleService;
import com.xiaohai.system.pojo.entity.UserRole;
import com.xiaohai.system.pojo.query.UserRoleQuery;
import com.xiaohai.system.pojo.vo.UserRoleVo;
import com.xiaohai.system.pojo.dto.UserRoleDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 用户角色关联表Controller
*
* @author xiaohai
* @since 2023-01-29
*/
@Tag(name = "用户角色关联表",description = "用户角色关联表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user-role")
public class UserRoleController {

    private final UserRoleService userRoleService;


//    @Operation(summary = "新增用户角色关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @PostMapping()
//    public Response<Integer> add(@RequestBody UserRoleVo vo){
//        return  Response.success("新增用户角色关联表成功！", userRoleService.add(vo));
//    }
//
//    @Operation(summary = "删除用户角色关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @DeleteMapping("{id}")
//    public Response<Integer> delete(@PathVariable("id") Long id){
//        return  Response.success("删除用户角色关联表成功！", userRoleService.delete(id));
//    }
//
//    @Operation(summary = "更新用户角色关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @PutMapping()
//    public Response<Integer> update(@RequestBody UserRoleVo vo){
//        return  Response.success("更新用户角色关联表成功！", userRoleService.updateData(vo));
//    }
//
//
//    @Operation(summary = "id查询用户角色关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @GetMapping("{id}")
//    public Response<UserRole> findById(@PathVariable Long id){
//        return  Response.success("id查询用户角色关联表成功！", userRoleService.findById(id));
//    }
//
//    @Operation(summary = "查询用户角色关联表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @Parameter(name = "pageNum", description = "页码", required = true)
//    @Parameter(name = "pageSize", description = "每页数量", required = true)
//    @GetMapping()
//    public Response<ReturnPageData<UserRoleDto>> findListByPage(UserRoleQuery query){
//        return Response.success("查询用户角色关联表列表成功！", userRoleService.findListByPage(query));
//    }

    }
