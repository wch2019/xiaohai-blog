package com.xiaohai.chat.config;

import com.xiaohai.chat.pojo.StompPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * WebSocket自定义握手管理器
 *
 * @author wangchenghai
 * @date 2024/02/19 11:49:16
 */
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    /**
     * 重写定义用户信息方法
     *
     * @param request    握手请求对象
     * @param wsHandler  WebSocket管理器，用于管理信息
     * @param attributes 用于传递WebSocket会话的握手属性
     * @return StompPrincipal 自定义用户信息
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //获取客户端主机名称
        String hostName = request.getRemoteAddress().getHostName();
        //获取客户端主机IP地址
        String hostAddress = request.getRemoteAddress().getAddress().getHostAddress();
        //StompPrincipal(name = hostName, publicName = hostAddress)
        return new StompPrincipal(hostName, hostAddress);
    }
}