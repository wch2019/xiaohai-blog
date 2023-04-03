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
import com.xiaohai.note.service.CategoryService;
import com.xiaohai.note.pojo.entity.Category;
import com.xiaohai.note.pojo.query.CategoryQuery;
import com.xiaohai.note.pojo.vo.CategoryVo;
import com.xiaohai.note.pojo.dto.CategoryDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 分类Controller
*
* @author xiaohai
* @since 2023-04-03
*/
@Tag(name = "分类",description = "分类")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/category")
public class CategoryController {

    private final CategoryService categoryService;


    @Operation(summary = "新增分类",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:category:add")
    @Log(title = "新增分类")
    @PostMapping()
    public Response<Integer> add(@RequestBody CategoryVo vo){
        return  Response.success("新增分类成功！", categoryService.add(vo));
    }

    @Operation(summary = "删除分类",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:category:delete")
    @Log(title = "删除分类")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除分类成功！",categoryService.delete(ids));
    }

    @Operation(summary = "更新分类",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:category:update")
    @Log(title = "更新分类")
    @PutMapping()
    public Response<Integer> update(@RequestBody CategoryVo vo){
        return  Response.success("更新分类成功！",categoryService.updateData(vo));
    }


    @Operation(summary = "id查询分类",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Category> findById(@PathVariable Long id){
        return  Response.success("id查询分类成功！",categoryService.findById(id));
    }

    @Operation(summary = "查询分类列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("note:category:list")
    @GetMapping()
    public Response<ReturnPageData<CategoryDto>> findListByPage(@ParameterObject CategoryQuery query){
        return Response.success("查询分类列表成功！",categoryService.findListByPage(query));
    }

    }
