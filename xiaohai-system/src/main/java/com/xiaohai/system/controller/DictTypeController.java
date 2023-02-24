package com.xiaohai.system.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.system.service.DictTypeService;
import com.xiaohai.system.pojo.entity.DictType;
import com.xiaohai.system.pojo.query.DictTypeQuery;
import com.xiaohai.system.pojo.vo.DictTypeVo;
import com.xiaohai.system.pojo.dto.DictTypeDto;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
*
* 字典类型Controller
*
* @author xiaohai
* @since 2023-01-29
*/
@Tag(name = "字典类型",description = "字典类型")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dict-type")
public class DictTypeController {

    private final DictTypeService dictTypeService;


    @Operation(summary = "新增字典类型",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@Validated @RequestBody DictTypeVo vo){
        return  Response.success("新增字典类型成功！", dictTypeService.add(vo));
    }

    @Operation(summary = "删除字典类型",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除字典类型成功！", dictTypeService.delete(ids));
    }

    @Operation(summary = "更新字典类型",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping()
    public Response<Integer> update(@RequestBody DictTypeVo vo){
        return  Response.success("更新字典类型成功！", dictTypeService.updateData(vo));
    }


    @Operation(summary = "id查询字典类型",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<DictType> findById(@PathVariable Long id){
        return  Response.success("id查询字典类型成功！", dictTypeService.findById(id));
    }

    @Operation(summary = "查询字典类型列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<DictTypeDto>> findListByPage(DictTypeQuery query){
        return Response.success("查询字典类型列表成功！", dictTypeService.findListByPage(query));
    }

    }
