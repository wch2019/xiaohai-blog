package com.xiaohai.admin.controller;

import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.server.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @GetMapping()
    public Response<Object> getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return Response.success(server);
    }
}