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
import com.xiaohai.note.service.ArticleService;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.query.ArticleQuery;
import com.xiaohai.note.pojo.vo.ArticleVo;
import com.xiaohai.note.pojo.dto.ArticleDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 文章表Controller
*
* @author xiaohai
* @since 2023-04-04
*/
@Tag(name = "文章",description = "文章")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/article")
public class ArticleController {

    private final ArticleService articleService;


    @Operation(summary = "新增文章",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:add")
    @Log(title = "新增文章")
    @PostMapping()
    public Response<Integer> add(@RequestBody ArticleVo vo){
        return  Response.success("新增文章表成功！", articleService.add(vo));
    }

    @Operation(summary = "删除文章",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:delete")
    @Log(title = "删除文章")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除文章表成功！",articleService.delete(ids));
    }

    @Operation(summary = "更新文章",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:update")
    @Log(title = "更新文章")
    @PutMapping()
    public Response<Integer> update(@RequestBody ArticleVo vo){
        return  Response.success("更新文章表成功！",articleService.updateData(vo));
    }


    @Operation(summary = "id查询文章表",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Article> findById(@PathVariable Long id){
        return  Response.success("id查询文章表成功！",articleService.findById(id));
    }

    @Operation(summary = "查询文章表列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("note:article:list")
    @GetMapping()
    public Response<ReturnPageData<ArticleDto>> findListByPage(@ParameterObject ArticleQuery query){
        return Response.success("查询文章表列表成功！",articleService.findListByPage(query));
    }

    }
