package com.xiaohai.note.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.dto.NotificationsDto;
import com.xiaohai.note.pojo.query.NotificationsQuery;
import com.xiaohai.note.service.NotificationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
*
* 系统通知Controller
*
* @author xiaohai
* @since 2024-03-09
*/
@Tag(name = "系统通知",description = "系统通知")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    @Operation(summary = "通过sse连接通知给前端消息",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping(value = "/news")
    public SseEmitter getSseEmitter() {
        return notificationsService.getSseEmitter();
    }


//    @Operation(summary = "删除系统通知",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
//    @DeleteMapping("{ids}")
//    public Response<Integer> delete(@PathVariable Long[] ids){
//        return  Response.success("删除系统通知成功！",notificationsService.delete(ids));
//    }

    @Operation(summary = "更新系统通知（已读）",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @PutMapping("{ids}")
    public Response<Integer> update(@PathVariable Long[] ids){
        return  Response.success("更新系统通知成功！",notificationsService.updateData(ids));
    }


    @Operation(summary = "查询未读系统通知",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/unread")
    public Response<List<NotificationsDto>> findList(){
        return  Response.success("查询未读系统通知成功！",notificationsService.findList());
    }

    @Operation(summary = "查询系统通知列表数据",security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping()
    public Response<ReturnPageData<NotificationsDto>> findListByPage(@ParameterObject NotificationsQuery query){
        return Response.success("查询系统通知列表成功！",notificationsService.findListByPage(query));
    }

    }
