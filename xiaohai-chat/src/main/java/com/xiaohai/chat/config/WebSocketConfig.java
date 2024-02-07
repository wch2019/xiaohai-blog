package com.xiaohai.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置
 * @author wangchenghai
 * @date 2024/02/07 16:45:49
 */
@Configuration
// @EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）
// 开始支持@MessageMapping,就像是使用@requestMapping一样。
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 注册Stomp端点
     *
     * @param registry 注册中心
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //指定 WebSocket 的端点路径为 "/messages"，客户端将使用这个路径连接 WebSocket 服务,并指定使用 SockJS 协议。
        registry.addEndpoint("/messages").withSockJS();
    }
    /**
     * 配置消息Broker
     *
     * @param registry 注册配置
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 这一行配置了消息代理，指定了可以通过消息代理发送的目的地前缀。/queue/ 和 /topic/ 是两种不同的消息发送目的地，分别用于点对点消息和发布/订阅消息。
        registry.enableSimpleBroker("/queue/", "/topic/");
        //配置了应用程序的消息目的地前缀，客户端发送的消息将会以这个前缀开头。
        registry.setApplicationDestinationPrefixes("/app");
    }
}
