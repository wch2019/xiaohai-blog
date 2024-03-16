package com.xiaohai.note.pojo.query;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

/**
* <p>
* 系统通知 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2024-03-09
*/
@Getter
@Setter
@Schema(name = "NotificationsQuery", description = "系统通知 Query 数据查询对象")
@ParameterObject
public class NotificationsQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Parameter(description = "通知类型")
    private String type;

    @Parameter(description = "是否已读(0否，1是)")
    private Integer isRead;
}
