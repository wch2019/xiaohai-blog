package com.xiaohai.note.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xiaohai
 * @description: 本地文章上传
 * @date 2024-08-29 11:47
 **/
@Getter
@Setter
@Schema(name = "LocalArticleVo", description = "本地文章上传")
public class LocalArticleVo {
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "文章标题")
    @NotNull(message = "文章标题为空")
    private String title;

    @Schema(description = "分类")
    private String categories;

    @Schema(description = "标签列表")
    private List<String> tags;

    @Schema(description = "是否原创")
    private String original;

    @Schema(description = "创建时间")
    @NotNull(message = "创建时间为空")
    private String date;

    @Schema(description = "更新时间")
    @NotNull(message = "更新时间为空")
    private String updated;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "内容")
    @NotNull(message = "内容为空")
    private String content;

    @Schema(description = "文章简介")
    private String summary;

    @Schema(description = "是否发布(0否，1是)")
    @NotNull(message = "是否发布为空")
    private Integer isPush;



}
