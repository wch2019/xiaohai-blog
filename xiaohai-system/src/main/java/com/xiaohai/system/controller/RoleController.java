package com.xiaohai.system.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.dto.RoleDto;
import com.xiaohai.system.pojo.entity.Role;
import com.xiaohai.system.pojo.query.RoleQuery;
import com.xiaohai.system.pojo.vo.RoleVo;
import com.xiaohai.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*
* 角色表Controller
*
* @author xiaohai
* @since 2023-01-29
*/
@Tag(name = "角色表",description = "角色表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role")
public class RoleController {

    private final RoleService roleService;


    @Operation(summary = "新增角色表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@Validated @RequestBody RoleVo vo){
        return  Response.success("新增角色表成功！", roleService.add(vo));
    }

    @Operation(summary = "删除角色表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除角色表成功！", roleService.delete(id));
    }

    @Operation(summary = "更新角色表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping()
    public Response<Integer> update(@Validated @RequestBody RoleVo vo){
        return  Response.success("更新角色表成功！", roleService.updateData(vo));
    }


    @Operation(summary = "id查询角色表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Role> findById(@PathVariable Long id){
        return  Response.success("id查询角色表成功！", roleService.findById(id));
    }

    @Operation(summary = "查询角色表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<Role>> findListByPage(RoleQuery query){
        return Response.success("查询角色表列表成功！", roleService.findListByPage(query));
    }

    @Operation(summary = "查询角色选择框列表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/option-select")
    public Response<List<RoleDto>> optionSelect() {
        return Response.success("查询角色选择框列表成功！", roleService.optionSelect());
    }

    }
