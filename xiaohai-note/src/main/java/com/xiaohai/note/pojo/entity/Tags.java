package com.xiaohai.note.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohai.common.daomain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author xiaohai
 * @since 2023-03-31
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("b_tags")
@Schema(name = "Tags", description = "标签表")
public class Tags extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "标签名称")
    private String name;

    @Schema(description = "点击次数")
    private Integer click;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
