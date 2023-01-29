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
import com.xiaohai.system.service.RoleMenuService;
import com.xiaohai.system.pojo.entity.RoleMenu;
import com.xiaohai.system.pojo.query.RoleMenuQuery;
import com.xiaohai.system.pojo.vo.RoleMenuVo;
import com.xiaohai.system.pojo.dto.RoleMenuDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 角色权限关联表Controller
*
* @author xiaohai
* @since 2023-01-29
*/
@Tag(name = "角色权限关联表",description = "角色权限关联表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role-menu")
public class RoleMenuController {

    private final RoleMenuService oleMenuService;


    @Operation(summary = "新增角色权限关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@RequestBody RoleMenuVo vo){
        return  Response.success("新增角色权限关联表成功！", oleMenuService.add(vo));
    }

    @Operation(summary = "删除角色权限关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除角色权限关联表成功！",oleMenuService.delete(id));
    }

    @Operation(summary = "更新角色权限关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping()
    public Response<Integer> update(@RequestBody RoleMenuVo vo){
        return  Response.success("更新角色权限关联表成功！",oleMenuService.updateData(vo));
    }


    @Operation(summary = "id查询角色权限关联表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<RoleMenu> findById(@PathVariable Long id){
        return  Response.success("id查询角色权限关联表成功！",oleMenuService.findById(id));
    }

    @Operation(summary = "查询角色权限关联表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<RoleMenuDto>> findListByPage(RoleMenuQuery query){
        return Response.success("查询角色权限关联表列表成功！",oleMenuService.findListByPage(query));
    }

    }
