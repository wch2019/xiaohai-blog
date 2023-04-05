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
import com.xiaohai.note.service.ArticleTagService;
import com.xiaohai.note.pojo.entity.ArticleTag;
import com.xiaohai.note.pojo.query.ArticleTagQuery;
import com.xiaohai.note.pojo.vo.ArticleTagVo;
import com.xiaohai.note.pojo.dto.ArticleTagDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 文章标签关联Controller
*
* @author xiaohai
* @since 2023-04-04
*/
@Tag(name = "文章标签关联",description = "文章标签关联")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/article-tag")
public class ArticleTagController {

    private final ArticleTagService articleTagService;


//    @Operation(summary = "新增文章标签关联",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @PostMapping()
//    public Response<Integer> add(@RequestBody ArticleTagVo vo){
//        return  Response.success("新增文章标签关联成功！", articleTagService.add(vo));
//    }
//
//    @Operation(summary = "删除文章标签关联",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @DeleteMapping("{ids}")
//    public Response<Integer> delete(@PathVariable Long[] ids){
//        return  Response.success("删除文章标签关联成功！",articleTagService.delete(ids));
//    }
//
//    @Operation(summary = "更新文章标签关联",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @PutMapping()
//    public Response<Integer> update(@RequestBody ArticleTagVo vo){
//        return  Response.success("更新文章标签关联成功！",articleTagService.updateData(vo));
//    }
//
//
//    @Operation(summary = "id查询文章标签关联",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @GetMapping("{id}")
//    public Response<ArticleTag> findById(@PathVariable Long id){
//        return  Response.success("id查询文章标签关联成功！",articleTagService.findById(id));
//    }
//
//    @Operation(summary = "查询文章标签关联列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @Parameter(name = "pageNum", description = "页码", required = true)
//    @Parameter(name = "pageSize", description = "每页数量", required = true)
//    @GetMapping()
//    public Response<ReturnPageData<ArticleTagDto>> findListByPage(@ParameterObject ArticleTagQuery query){
//        return Response.success("查询文章标签关联列表成功！",articleTagService.findListByPage(query));
//    }

    }
