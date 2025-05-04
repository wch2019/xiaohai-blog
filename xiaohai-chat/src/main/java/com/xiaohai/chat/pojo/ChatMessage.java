package com.xiaohai.chat.pojo;

import lombok.Data;

@Data
public class ChatMessage {
    private String username;
    private String content;
    private String from;
    private String to;
}
