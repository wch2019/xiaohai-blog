package com.xiaohai.system.pojo.query;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 系统日志 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-03-30
*/
@Getter
@Setter
@Schema(name = "LogQuery", description = "系统日志 Query 数据查询对象")
@ParameterObject
public class LogQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "模块名称")
    private String title;

    @Parameter(description = "方法名称")
    private String method;

    @Parameter(description = "请求方式")
    private String requestMethod;

    @Parameter(description = "请求url")
    private String operUrl;

    @Parameter(description = "主机地址")
    private String operIp;

    @Parameter(description = "请求参数")
    private String operParam;

    @Parameter(description = "返回参数")
    private String jsonResult;

    @Parameter(description = "错误消息")
    private String errorMsg;

    @Parameter(description = "状态（0正常 1异常）")
    private String status;
}
