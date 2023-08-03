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
* 用户反馈 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-08-03
*/
@Getter
@Setter
@Schema(name = "FeedbackQuery", description = "用户反馈 Query 数据查询对象")
@ParameterObject
public class FeedbackQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "用户id")
    private Integer userId;

    @Parameter(description = "标题")
    private String title;

    @Parameter(description = "反馈内容")
    private String content;

    @Parameter(description = "回复")
    private String reason;

    @Parameter(description = "状态（0-待审核,1-已通过,2-未通过）")
    private String status;
}
