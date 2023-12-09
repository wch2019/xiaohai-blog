package com.xiaohai.file.controller;

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
import com.xiaohai.file.service.FileManagerService;
import com.xiaohai.file.pojo.entity.FileManager;
import com.xiaohai.file.pojo.query.FileManagerQuery;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.pojo.dto.FileManagerDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 文件管理Controller
*
* @author xiaohai
* @since 2023-12-09
*/
@Tag(name = "文件管理",description = "文件管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file/file-manager")
public class FileManagerController {

    private final FileManagerService fileManagerService;


    @Operation(summary = "新增文件管理",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@RequestBody FileManagerVo vo){
        return  Response.success("新增文件管理成功！", fileManagerService.add(vo));
    }

    @Operation(summary = "删除文件管理",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除文件管理成功！",fileManagerService.delete(ids));
    }

    @Operation(summary = "更新文件管理",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping()
    public Response<Integer> update(@RequestBody FileManagerVo vo){
        return  Response.success("更新文件管理成功！",fileManagerService.updateData(vo));
    }


    @Operation(summary = "id查询文件管理",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<FileManager> findById(@PathVariable Long id){
        return  Response.success("id查询文件管理成功！",fileManagerService.findById(id));
    }

    @Operation(summary = "查询文件管理列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<FileManagerDto>> findListByPage(@ParameterObject FileManagerQuery query){
        return Response.success("查询文件管理列表成功！",fileManagerService.findListByPage(query));
    }

    }
