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
import com.xiaohai.note.service.TagsService;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.pojo.query.TagsQuery;
import com.xiaohai.note.pojo.vo.TagsVo;
import com.xiaohai.note.pojo.dto.TagsDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 标签Controller
*
* @author xiaohai
* @since 2023-03-31
*/
@Tag(name = "标签",description = "标签")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/tags")
public class TagsController {

    private final TagsService tagsService;


    @Operation(summary = "新增标签",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:tags:add")
    @Log(title = "新增标签")
    @PostMapping()
    public Response<Integer> add(@RequestBody TagsVo vo){
        return  Response.success("新增标签成功！", tagsService.add(vo));
    }

    @Operation(summary = "删除标签",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:tags:delete")
    @Log(title = "删除标签")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除标签成功！",tagsService.delete(ids));
    }

    @Operation(summary = "更新标签",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:tags:update")
    @Log(title = "更新标签")
    @PutMapping()
    public Response<Integer> update(@RequestBody TagsVo vo){
        return  Response.success("更新标签成功！",tagsService.updateData(vo));
    }


    @Operation(summary = "id查询标签",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Tags> findById(@PathVariable Long id){
        return  Response.success("id查询标签成功！",tagsService.findById(id));
    }

    @Operation(summary = "查询标签列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("note:tags:list")
    @GetMapping()
    public Response<ReturnPageData<TagsDto>> findListByPage(@ParameterObject TagsQuery query){
        return Response.success("查询标签列表成功！",tagsService.findListByPage(query));
    }

    }
