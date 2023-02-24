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
import com.xiaohai.system.service.DictDataService;
import com.xiaohai.system.pojo.entity.DictData;
import com.xiaohai.system.pojo.query.DictDataQuery;
import com.xiaohai.system.pojo.vo.DictDataVo;
import com.xiaohai.system.pojo.dto.DictDataDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 字典数据表Controller
*
* @author xiaohai
* @since 2023-02-03
*/
@Tag(name = "字典数据表",description = "字典数据表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dict-data")
public class DictDataController {

    private final DictDataService dictDataService;


    @Operation(summary = "新增字典数据表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@RequestBody DictDataVo vo){
        return  Response.success("新增字典数据表成功！", dictDataService.add(vo));
    }

    @Operation(summary = "删除字典数据表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除字典数据表成功！",dictDataService.delete(id));
    }

    @Operation(summary = "更新字典数据表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping()
    public Response<Integer> update(@RequestBody DictDataVo vo){
        return  Response.success("更新字典数据表成功！",dictDataService.updateData(vo));
    }


    @Operation(summary = "id查询字典数据表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<DictData> findById(@PathVariable Long id){
        return  Response.success("id查询字典数据表成功！",dictDataService.findById(id));
    }

    @Operation(summary = "查询字典数据表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<DictDataDto>> findListByPage(DictDataQuery query){
        return Response.success("查询字典数据表列表成功！",dictDataService.findListByPage(query));
    }

    }
