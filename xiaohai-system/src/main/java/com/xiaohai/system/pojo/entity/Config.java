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
 * 系统配置
 * </p>
 *
 * @author xiaohai
 * @since 2023-02-01
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
@Schema(name = "Config", description = "系统配置")
public class Config extends BaseEntity  implements Serializable  {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "邮箱地址")
    private String maillHost;

    @Schema(description = "邮箱发件人")
    private String maillUsername;

    @Schema(description = "邮箱授权码")
    private String maillPassword;

    @Schema(description = "邮箱发送端口")
    private String emailPort;

    @Schema(description = "本地文件地址")
    private String filePath;
}
