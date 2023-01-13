package com.xiaohai.system.controller;

import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.xiaohai.system.service.UserService;
import com.xiaohai.system.pojo.entity.UserEntity;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 用户表Controller
*
* @author xiaohai
* @since 2023-01-13
*/
@Tag(name = "用户表",description = "用户表")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user-entity")
public class UserController {

    private final UserService serService;


    @Operation(summary = "新增用户表")
    @PostMapping()
    public Response<Integer> add(@RequestBody UserEntity userEntity){
        return  Response.success("新增用户表成功！", serService.add(userEntity));
    }

    @Operation(summary = "删除用户表")
    @DeleteMapping("{id}")
    public Response<Integer> delete(@PathVariable("id") Long id){
        return  Response.success("删除用户表成功！",serService.delete(id));
    }

    @Operation(summary = "更新用户表")
    @PutMapping()
    public Response<Integer> update(@RequestBody UserEntity userEntity){
        return  Response.success("更新用户表成功！",serService.updateData(userEntity));
    }


    @Operation(summary = "id查询用户表")
    @GetMapping("{id}")
    public Response<UserEntity> findById(@PathVariable Long id){
        return  Response.success("id查询用户表成功！",serService.findById(id));
    }

    @Operation(summary = "查询用户表列表数据")
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<UserEntity>> findListByPage(UserEntity userEntity){
        return Response.success("查询用户表列表成功！",serService.findListByPage(userEntity));
    }

    }
