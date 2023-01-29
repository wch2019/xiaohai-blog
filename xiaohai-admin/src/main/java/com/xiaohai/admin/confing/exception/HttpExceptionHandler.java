package com.xiaohai.admin.confing.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.xiaohai.common.daomain.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @author xiaohai
 * @date 2021/12/20 18:04
 */
@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() +":"+ error.getDefaultMessage()).collect(Collectors.joining(";"));
        log.error(message);
        return Response.failure(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleRequestExceptions(Exception ex) {
        if (ex instanceof BindException) {
            StringBuilder msg = new StringBuilder();
            for (FieldError error : ((BindException) ex).getFieldErrors()) {
                msg.append("字段[").append(error.getField()).append("]的值[").append(error.getRejectedValue()).append("]无效;");
            }
            return Response.failure(HttpStatus.BAD_REQUEST, msg.toString());
        }
        String message = StringUtils.isBlank(ex.getMessage()) ? "请求参数异常" : ex.getMessage();
        return Response.failure(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 登录异常
     * @param ex
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Response<Object> notLoginExceptionHandler(NotLoginException ex) {
        log.error(ex.getMessage());
        return Response.failure(HttpStatus.UNAUTHORIZED,"继续使用，请重新登录");
    }

    /**
     * 权限异常
     * @param ex
     * @return
     */
    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    public Response<Object> notPermissionExceptionHandler(NotPermissionException ex) {
        log.error(ex.getMessage(), ex);
        return Response.failure(HttpStatus.FORBIDDEN,"无此权限：" + ex.getCode());
    }

    /**
     * 业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleServiceException(ServiceException ex) {
        log.error(ex.getMessage());
        return Response.failure(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Assert业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> assertExceptionHandler(IllegalArgumentException ex) {
        String message = StringUtils.isBlank(ex.getMessage()) ? "请求参数异常" : ex.getMessage();
        log.error(ex.getMessage());
        return Response.failure(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handleException(Exception ex) {
        log.error("系统异常", ex);
        return Response.failure(HttpStatus.INTERNAL_SERVER_ERROR, "系统异常",ex.getMessage());
    }
}
