package com.xiaohai.common.exception;

/**
 * @author wangchenghai
 * @date 2023/01/29 14:21:00
 */
public class ServiceException  extends RuntimeException{
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
