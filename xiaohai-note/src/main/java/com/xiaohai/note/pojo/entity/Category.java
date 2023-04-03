package com.xiaohai.note.pojo.entity;

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
 * 分类表
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-03
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("b_category")
@Schema(name = "Category", description = "分类表")
public class Category extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "点击次数")
    private Integer click;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
