package com.xiaohai.note.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.dto.CommentDto;
import com.xiaohai.note.pojo.query.CommentQuery;
import com.xiaohai.note.pojo.vo.CommentVo;
import com.xiaohai.note.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 评论Controller
 *
 * @author xiaohai
 * @since 2023-05-24
 */
@Tag(name = "评论", description = "评论")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/comment")
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "新增评论", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@Validated @RequestBody CommentVo vo) {
        return Response.success("评论成功！", commentService.add(vo));
    }

    @Operation(summary = "删除评论", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids) {
        return Response.success("删除评论成功！", commentService.delete(ids));
    }

//    @Operation(summary = "更新评论",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @PutMapping()
//    public Response<Integer> update(@RequestBody CommentVo vo){
//        return  Response.success("更新评论成功！",commentService.updateData(vo));
//    }


//    @Operation(summary = "id查询评论",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @GetMapping("{id}")
//    public Response<Comment> findById(@PathVariable Long id){
//        return  Response.success("id查询评论成功！",commentService.findById(id));
//    }

    @Operation(summary = "查询评论列表数据", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<CommentDto>> findListByPage(@ParameterObject CommentQuery query) {
        return Response.success("查询评论列表成功！", commentService.findListByPage(query));
    }
}
