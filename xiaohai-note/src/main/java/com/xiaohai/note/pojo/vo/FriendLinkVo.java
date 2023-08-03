package com.xiaohai.note.pojo.vo;

import java.io.Serializable;
import java.io.Serial;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
* <p>
* 友情链接表 VO（View Object）：显示层对象
* </p>
*
* @author xiaohai
* @since 2023-07-01
*/
@Getter
@Setter
@Schema(name = "FriendLinkVo", description = "友情链接表 VO（View Object）：显示层对象")
public class FriendLinkVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "网站名称")
    private String name;

    @Schema(description = "站长邮箱")
    private String email;

    @Schema(description = "网站地址")
    private String url;

    @Schema(description = "描述")
    private String info;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "审核回复")
    private String reason;

    @Schema(description = "审核状态（0-待审核,1-已通过,2-未通过）")
    private String status;
}
