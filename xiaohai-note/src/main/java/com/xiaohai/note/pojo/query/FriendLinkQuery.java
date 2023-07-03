package com.xiaohai.note.pojo.query;

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
* 友情链接表 Query 数据查询对象
* </p>
*
* @author xiaohai
* @since 2023-07-01
*/
@Getter
@Setter
@Schema(name = "FriendLinkQuery", description = "友情链接表 Query 数据查询对象")
@ParameterObject
public class FriendLinkQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "id")
    private Integer id;

    @Parameter(description = "用户id")
    private Integer userId;

    @Parameter(description = "网站名称")
    private String name;

    @Schema(description = "站长邮箱")
    private String email;

    @Parameter(description = "网站地址")
    private String url;

    @Parameter(description = "描述")
    private String info;

    @Parameter(description = "排序")
    private Integer sort;

    @Parameter(description = "下架原因")
    private String reason;

    @Parameter(description = "审核状态（0-待审核,1-通过,2-未通过）")
    private String status;
}
