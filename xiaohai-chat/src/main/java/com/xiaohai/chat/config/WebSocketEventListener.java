package com.xiaohai.chat.config;

import com.xiaohai.chat.pojo.ChatMessage;
import com.xiaohai.chat.pojo.StompPrincipal;
import lombok.extern.slf4j.Slf4j;
import static cn.hutool.json.JSONUtil.toBean;
import static cn.hutool.json.JSONUtil.toJsonStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import static java.util.Objects.requireNonNull;

/**
 * SessionConnectedEvent（对应WebSocket 的 @OnOpen 注解）
 * SessionDisconnectEvent （对应WebSocket 的 @OnClose 注解）
 * SessionSubscribeEvent （订阅事件会话）
 * SessionUnsubscribeEvent （取消订阅事件会话）
 */
@Component
@Slf4j
public class WebSocketEventListener {


    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        log.info("Received a new web socket connection");
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if (username != null) {
//            log.info("User Disconnected : " + username);
//
//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType(ChatMessage.MessageType.LEAVE);
//            chatMessage.setSender(username);
//
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
//    }
    /**
     * 监听客户端连接
     *
     * @param event 连接事件对象
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已连接: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 会话ID: " + requireNonNull(event.getMessage().getHeaders().get("simpSessionId")) + " }");
    }

    /**
     * 监听客户端关闭事件
     *
     * @param event 关闭事件对象
     */
    @EventListener
    public void handleWebSocketCloseListener(SessionDisconnectEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已关闭: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 会话ID: " + requireNonNull(event.getMessage().getHeaders().get("simpSessionId")) + " }");
    }

    /**
     * 监听客户端订阅事件
     *
     * @param event 订阅事件对象
     */
    @EventListener
    public void handleSubscription(SessionSubscribeEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已订阅: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 订阅节点: " + requireNonNull(event.getMessage().getHeaders().get("simpDestination")) + " }");
    }

    /**
     * 监听客户端取消订阅事件
     *
     * @param event 取消订阅事件对象
     */
    @EventListener
    public void handleUnSubscription(SessionUnsubscribeEvent event) {
        StompPrincipal principal = toBean(toJsonStr(event.getUser()), StompPrincipal.class);
        log.info("WebSocket 客户端已取消订阅: {}",
                "{ 客户端主机名: " + principal.getName() +
                        ", 客户端主机IP地址: " + principal.getPublicName() +
                        ", 取消订阅节点: " + requireNonNull(event.getMessage().getHeaders().get("simpDestination")) + " }");
    }
}
