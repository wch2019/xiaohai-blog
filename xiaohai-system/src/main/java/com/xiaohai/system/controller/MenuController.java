package com.xiaohai.system.controller;

import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.system.service.MenuService;
import com.xiaohai.system.pojo.entity.Menu;
import com.xiaohai.system.pojo.query.MenuQuery;
import com.xiaohai.system.pojo.vo.MenuVo;
import com.xiaohai.system.pojo.dto.MenuDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 菜单权限表Controller
*
* @author xiaohai
* @since 2023-01-18
*/
@Tag(name = "菜单权限表",description = "菜单权限表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/menu")
public class MenuController {

    private final MenuService enuService;


    @Operation(summary = "新增菜单权限表",security = {@SecurityRequirement(name = "authentication")})
    @PostMapping()
    public Response<Integer> add(@RequestBody MenuVo vo){
        return  Response.success("新增菜单权限表成功！", enuService.add(vo));
    }

    @Operation(summary = "删除菜单权限表",security = {@SecurityRequirement(name = "authentication")})
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除菜单权限表成功！",enuService.delete(id));
    }

    @Operation(summary = "更新菜单权限表",security = {@SecurityRequirement(name = "authentication")})
    @PutMapping()
    public Response<Integer> update(@RequestBody MenuVo vo){
        return  Response.success("更新菜单权限表成功！",enuService.updateData(vo));
    }


    @Operation(summary = "id查询菜单权限表",security = {@SecurityRequirement(name = "authentication")})
    @GetMapping("{id}")
    public Response<Menu> findById(@PathVariable Long id){
        return  Response.success("id查询菜单权限表成功！",enuService.findById(id));
    }

    @Operation(summary = "查询菜单权限表列表数据",security = {@SecurityRequirement(name = "authentication")})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<MenuDto>> findListByPage(MenuQuery query){
        return Response.success("查询菜单权限表列表成功！",enuService.findListByPage(query));
    }

    }
