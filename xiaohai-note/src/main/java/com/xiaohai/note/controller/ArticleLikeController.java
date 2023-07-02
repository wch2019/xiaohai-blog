package com.xiaohai.note.controller;

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
import com.xiaohai.note.service.ArticleLikeService;
import com.xiaohai.note.pojo.entity.ArticleLike;
import com.xiaohai.note.pojo.query.ArticleLikeQuery;
import com.xiaohai.note.pojo.vo.ArticleLikeVo;
import com.xiaohai.note.pojo.dto.ArticleLikeDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 用户文章点赞Controller
*
* @author xiaohai
* @since 2023-07-01
*/
@Tag(name = "用户文章点赞",description = "用户文章点赞")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/article-like")
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;


    @Operation(summary = "新增、删除用户文章点赞",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@RequestBody ArticleLikeVo vo){
        return  Response.success("新增用户文章点赞成功！", articleLikeService.add(vo));
    }

    @Operation(summary = "删除用户文章点赞",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除用户文章点赞成功！",articleLikeService.delete(ids));
    }

//    @Operation(summary = "更新用户文章点赞",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @PutMapping()
//    public Response<Integer> update(@RequestBody ArticleLikeVo vo){
//        return  Response.success("更新用户文章点赞成功！",articleLikeService.updateData(vo));
//    }


//    @Operation(summary = "id查询用户文章点赞",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @GetMapping("{id}")
//    public Response<ArticleLike> findById(@PathVariable Long id){
//        return  Response.success("id查询用户文章点赞成功！",articleLikeService.findById(id));
//    }

    @Operation(summary = "查询用户文章点赞列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<ArticleLikeDto>> findListByPage(@ParameterObject ArticleLikeQuery query){
        return Response.success("查询用户文章点赞列表成功！",articleLikeService.findListByPage(query));
    }

    }
