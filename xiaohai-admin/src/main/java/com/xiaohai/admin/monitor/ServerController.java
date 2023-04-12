package com.xiaohai.admin.monitor;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.server.Server;
import com.xiaohai.common.utils.OnLineUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "系统监控", description = "系统监控")
@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @Operation(summary = "系统服务", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping()
    public Response<Object> getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Response.success(server);
    }

    @Operation(summary = "在线用户", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @GetMapping("/on-line-user")
    public Response<Object> getOnLineUser(){
        ReturnPageData<OnLineUser> list= OnLineUtils.getOnLineUserList();
        return Response.success(list);
    }
}