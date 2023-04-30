package com.xiaohai.note.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
* <p>
* 标签表 DTO 数据传输对象
* </p>
*
* @author xiaohai
* @since 2023-03-31
*/
@Getter
@Setter
@Schema(name = "TagsDto", description = "标签表 DTO 数据传输对象")
public class TagsDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "标签名称")
    private String name;

    @Schema(description = "点击次数")
    private Integer click;

    @Schema(description = "标签对应的文章数")
    private long count;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（0正常 1停用）")
    private String status;
}
