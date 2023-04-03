package com.xiaohai.note.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 标签表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-03-31
*/
@Getter
@Setter
@Schema(name = "TagsVo", description = "标签表 VO（View Object）：显示层对象")
public class TagsVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
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
