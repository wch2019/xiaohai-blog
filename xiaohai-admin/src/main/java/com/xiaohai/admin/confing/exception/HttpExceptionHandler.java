package com.xiaohai.admin.confing.exception;

import com.xiaohai.common.daomain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author xiaohai
 * @date 2021/12/20 18:04
 */
@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + error.getDefaultMessage()).collect(Collectors.joining(";"));
        e.printStackTrace();
        return Response.failure(HttpStatus.BAD_REQUEST,message,null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handleRequestExceptions(Exception e) {
        if (e instanceof BindException) {
            StringBuilder msg = new StringBuilder();
            for (FieldError error : ((BindException) e).getFieldErrors()) {
                msg.append("字段[").append(error.getField()).append("]的值[").append(error.getRejectedValue()).append("]无效;");
            }
            return Response.failure(HttpStatus.BAD_REQUEST,msg.toString(),null);
        }
        String message = StringUtils.hasText(e.getMessage()) ? e.getMessage() : "请求参数异常";
        e.printStackTrace();
        return Response.failure(HttpStatus.BAD_REQUEST,message,null);
    }

}
