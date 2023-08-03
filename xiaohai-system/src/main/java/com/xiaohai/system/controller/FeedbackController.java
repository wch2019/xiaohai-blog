package com.xiaohai.system.controller;

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
import com.xiaohai.system.service.FeedbackService;
import com.xiaohai.system.pojo.entity.Feedback;
import com.xiaohai.system.pojo.query.FeedbackQuery;
import com.xiaohai.system.pojo.vo.FeedbackVo;
import com.xiaohai.system.pojo.dto.FeedbackDto;

import org.springframework.web.bind.annotation.RestController;

/**
*
* 用户反馈Controller
*
* @author xiaohai
* @since 2023-08-03
*/
@Tag(name = "用户反馈",description = "用户反馈")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;


    @Operation(summary = "新增用户反馈",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PostMapping()
    public Response<Integer> add(@RequestBody FeedbackVo vo){
        return  Response.success("新增用户反馈成功！", feedbackService.add(vo));
    }

    @Operation(summary = "删除用户反馈",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids){
        return  Response.success("删除用户反馈成功！",feedbackService.delete(ids));
    }

    @Operation(summary = "更新用户反馈",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping()
    public Response<Integer> update(@RequestBody FeedbackVo vo){
        return  Response.success("更新用户反馈成功！",feedbackService.updateData(vo));
    }


    @Operation(summary = "id查询用户反馈",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<Feedback> findById(@PathVariable Long id){
        return  Response.success("id查询用户反馈成功！",feedbackService.findById(id));
    }

    @Operation(summary = "查询用户反馈列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<FeedbackDto>> findListByPage(@ParameterObject FeedbackQuery query){
        return Response.success("查询用户反馈列表成功！",feedbackService.findListByPage(query));
    }

    }
