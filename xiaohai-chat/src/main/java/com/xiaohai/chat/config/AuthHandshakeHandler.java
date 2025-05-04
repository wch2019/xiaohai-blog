package com.xiaohai.chat.config;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author xiaohai
 * @date 2025/5/4 08:54
 */
public class AuthHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        String token = null;

        // 从 URI 参数中获取 token
        List<String> query = List.of(request.getURI().getQuery().split("&"));
        for (String param : query) {
            if (param.startsWith("token=")) {
                token = param.split("=")[1];
                break;
            }
        }

        if (token == null) {
            throw new RuntimeException("缺少 token");
        }

        // TODO: 你这里可以接入 JWT 或 Sa-Token 等逻辑来校验
        String username = verifyTokenAndGetUsername(token);

        return new StompPrincipal(username);
    }

    private String verifyTokenAndGetUsername(String token) {
        // 简化示例，实际请做真实验证
        if ("abc123".equals(token)) {
            return "alice";
        } else if ("xyz789".equals(token)) {
            return "bob";
        }
        throw new RuntimeException("非法Token");
    }
}
