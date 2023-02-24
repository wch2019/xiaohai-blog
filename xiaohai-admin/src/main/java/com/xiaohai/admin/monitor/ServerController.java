package com.xiaohai.admin.monitor;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.server.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}