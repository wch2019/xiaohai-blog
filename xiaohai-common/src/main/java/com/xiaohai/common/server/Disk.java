package com.xiaohai.common.server;

import lombok.Data;

/**
 * @author wangchenghai
 * @date 2024/01/25 10:07:40
 */
@Data
public class Disk {
    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已经使用量
     */
    private String used;

    /**
     * 资源的使用率
     */
    private double usage;
}
