package com.xiaohai.admin.controller;

import com.xiaohai.common.daomain.BaseEntity;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.server.Server;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @GetMapping()
    @Operation(security = {@SecurityRequirement(name = "authentication")})
    public Response<Object> getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return Response.success(server);
    }
}