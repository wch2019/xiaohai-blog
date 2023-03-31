package com.xiaohai.note.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 分类表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-03-31
*/
@Getter
@Setter
@Schema(name = "ClassVo", description = "分类表 VO（View Object）：显示层对象")
public class ClassVo implements Serializable {
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
}
