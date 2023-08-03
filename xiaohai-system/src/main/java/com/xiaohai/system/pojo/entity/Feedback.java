package com.xiaohai.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohai.common.daomain.BaseEntity;
import java.io.Serializable;
import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户反馈
 * </p>
 *
 * @author xiaohai
 * @since 2023-08-03
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("sys_feedback")
@Schema(name = "Feedback", description = "用户反馈")
public class Feedback extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "回复")
    private String reason;

    @Schema(description = "状态（0-待审核,1-已通过,2-未通过）")
    private String status;
}
