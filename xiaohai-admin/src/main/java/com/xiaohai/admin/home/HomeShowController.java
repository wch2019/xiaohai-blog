package com.xiaohai.admin.home;

import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.dto.ArticleDtoAll;
import com.xiaohai.note.pojo.dto.ArticleShowDto;
import com.xiaohai.note.pojo.dto.CategoryDto;
import com.xiaohai.note.pojo.dto.TagsDto;
import com.xiaohai.note.service.ArticleService;
import com.xiaohai.note.service.CategoryService;
import com.xiaohai.note.service.TagsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 展示页接口
 * @author: xiaohai
 * @date: 2023-04-29 21:52
 **/
@Tag(name = "展示页接口", description = "展示页接口 ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/show")
public class HomeShowController {
    private final TagsService tagsService;
    private final CategoryService categoryService;
    private final ArticleService articleService;

    @Operation(summary = "标签")
    @GetMapping("/tags")
    public Response<List<TagsDto>> getTagsArticleCount() {
        return Response.success("获取标签成功！", tagsService.getTagsArticleCount());
    }
    @Operation(summary = "分类")
    @GetMapping("/category")
    public Response<List<CategoryDto>> getCategoryArticleCount() {
        return Response.success("获取分类成功！", categoryService.getCategoryArticleCount());
    }
    @Operation(summary = "查询展示文章表列表数据")
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @Parameter(name = "type", description = "1:最新文章,2:最热文章,3:原创文章,4:转载文章", required = true)
    @GetMapping("/articles")
    public Response<ReturnPageData<ArticleShowDto>> findShowListByPage(Integer type) {
        return Response.success("查询展示文章表列表成功！", articleService.findShowListByPage(type));
    }

    @Operation(summary = "id查询文章详情")
    @GetMapping("/article/{id}")
    public Response<ArticleDtoAll> findById(@PathVariable Long id) {
        return Response.success("id查询文章详情成功！", articleService.findById(id,0));
    }
}
