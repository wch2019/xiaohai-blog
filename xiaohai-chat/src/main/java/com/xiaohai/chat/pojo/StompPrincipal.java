package com.xiaohai.chat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

/**
 * 客户端信息实体类
 */
@Data
@AllArgsConstructor
public class StompPrincipal implements Principal {
    /**
     * 客户端主机名称，对应 HostName
     */
    private String name;
    /**
     * 客户端主机IP地址，对应 HostAddress
     */
    private String publicName;
}