package com.xiaohai.note.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.note.service.ClassService;
import com.xiaohai.note.pojo.entity.Class;
import com.xiaohai.note.pojo.query.ClassQuery;
import com.xiaohai.note.pojo.vo.ClassVo;
import com.xiaohai.note.pojo.dto.ClassDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 分类表Controller
*
* @author xiaohai
* @since 2023-03-31
*/
@Tag(name = "分类表",description = "分类表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/class")
public class ClassController {

    private final ClassService classService;


    @Operation(summary = "新增分类",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:class:add")
    @Log(title = "新增分类")
    @PostMapping()
    public Response<Integer> add(@RequestBody ClassVo vo){
        return  Response.success("新增分类表成功！", classService.add(vo));
    }

    @Operation(summary = "删除分类",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:config:save")
    @Log(title = "删除分类")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除分类表成功！",classService.delete(ids));
    }

    @Operation(summary = "更新分类表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("system:config:save")
    @Log(title = "更新分类")
    @PutMapping()
    public Response<Integer> update(@RequestBody ClassVo vo){
        return  Response.success("更新分类表成功！",classService.updateData(vo));
    }


    @Operation(summary = "id查询分类表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Class> findById(@PathVariable Long id){
        return  Response.success("id查询分类表成功！",classService.findById(id));
    }

    @Operation(summary = "查询分类表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<ClassDto>> findListByPage(@ParameterObject ClassQuery query){
        return Response.success("查询分类表列表成功！",classService.findListByPage(query));
    }

    }
