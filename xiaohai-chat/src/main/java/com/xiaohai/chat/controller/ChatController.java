package com.xiaohai.chat.controller;

import com.xiaohai.chat.pojo.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;

@RestController
public class ChatController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/group") // 群发 对应客户端发送的 /app/group
    @SendTo("/topic/chat")  // 广播到 /topic/chat
    public ChatMessage group(ChatMessage message, Principal principal) {
        message.setFrom(principal.getName());
        return message;
    }

    @MessageMapping("/chat") // 私聊
    public void privateChat(ChatMessage message, Principal principal) {
        message.setFrom(principal.getName());
        messagingTemplate.convertAndSendToUser(message.getTo(), "/queue/chat", message);
    }

}