package com.xiaohai.admin.controller;

import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.server.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @GetMapping()
    @Operation(security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    public Response<Object> getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Response.success(server);
    }
}