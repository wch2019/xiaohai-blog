package com.xiaohai.admin.home;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.OnLineUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 管理后台首页
 * @author: xiaohai
 * @date: 2023-04-16 15:46
 **/
@Tag(name = "管理后台首页",description = "管理后台首页 ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/manage")
public class HomeManageController {
    @Operation(summary = "文章数,用户数,评论数，浏览量", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping()
    public Response<ReturnPageData<OnLineUser>> getCount() {
        return Response.success("获取文章数,用户数,评论数，浏览量成功！", OnLineUtils.getOnLineUserList());
    }
    @Operation(summary = "近一年文章数", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping()
    public Response<ReturnPageData<OnLineUser>> getNote() {
        return Response.success("获取近一年文章数成功！", OnLineUtils.getOnLineUserList());
    }
    @Operation(summary = "分类，标签，文章阅读量排行", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping()
    public Response<ReturnPageData<OnLineUser>> getRank() {
        return Response.success("获取分类，标签，文章阅读量排行成功！", OnLineUtils.getOnLineUserList());
    }
    @Operation(summary = "一周访问量", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping()
    public Response<ReturnPageData<OnLineUser>> getPv() {
        return Response.success("获取一周访问量成功！", OnLineUtils.getOnLineUserList());
    }
}
