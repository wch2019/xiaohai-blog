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
 * 友情链接表
 * </p>
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("b_friend_link")
@Schema(name = "FriendLink", description = "友情链接表")
public class FriendLink extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
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
