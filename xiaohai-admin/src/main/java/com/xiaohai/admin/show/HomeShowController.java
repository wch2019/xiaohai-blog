package com.xiaohai.admin.show;

import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.dto.*;
import com.xiaohai.note.service.*;
import com.xiaohai.system.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final CommentService commentService;
    private final FriendLinkService friendLinkService;
    private final ConfigService configService;

    @Operation(summary = "标签")
    @Log(title = "标签")
    @GetMapping("/tags")
    public Response<List<TagsDto>> getTagsArticleCount() {
        return Response.success("获取标签成功！", tagsService.getTagsArticleCount());
    }
    @Operation(summary = "分类")
    @Log(title = "分类")
    @GetMapping("/category")
    public Response<List<CategoryDto>> getCategoryArticleCount() {
        return Response.success("获取分类成功！", categoryService.getCategoryArticleCount());
    }
    @Operation(summary = "查询展示文章表列表数据")
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @Parameter(name = "type", description = "1:最新文章,2:最热文章,3:原创文章,4:转载文章,5:标签id,6:分类id", required = true)
    @Parameter(name = "id", description = "分类id或者标签id", required = false)
    @Log(title = "展示文章表列表")
    @GetMapping("/articles")
    public Response<ReturnPageData<ArticleShowDto>> findShowListByPage(Integer type,Long id) {
        return Response.success("查询展示文章表列表成功！", articleService.findShowListByPage(type,id));
    }

    @Operation(summary = "id查询文章详情")
    @Log(title = "文章详情")
    @GetMapping("/article/{id}")
    public Response<ArticleDtoAll> findById(@PathVariable Long id) {
        return Response.success("id查询文章详情成功！", articleService.findById(id,0));
    }
    @Operation(summary = "查询归档列表数据")
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @Log(title = "归档列表")
    @GetMapping("/back")
    public Response<ReturnPageData<ArticleShowDto>> findBackListByPage() {
        return Response.success("查询归档列表数据成功！", articleService.findBackListByPage());
    }
    @Operation(summary = "文章搜索")
    @Parameter(name = "keywords", description = "关键词", required = true)
    @Log(title = "文章搜索")
    @GetMapping("/search")
    public Response<List<ArticleSearchDto>> searchArticle(String keywords) {
        return Response.success("文章搜索成功！", articleService.searchArticle(keywords));
    }

    @Operation(summary = "文章id查询评论")
    @Parameter(name = "id", description = "文章id", required = true)
    @Log(title = "指定文章评论")
    @GetMapping("article/comment/{id}")
    public Response<CommentCountDto> findByArticleId(@PathVariable Long id) {
        return Response.success("文章id查询评论成功！", commentService.findByArticleId(id));
    }

    @Operation(summary = "站点信息展示")
    @Log(title = "站点信息展示")
    @GetMapping("/findShowBasic")
    public Response<Map<String,Object>> findShowBasic() {
        Map<String,Object> map=new HashMap<>();
        map.put("basic",articleService.findShowBasic());
        map.put("website",configService.findByShowOne());
        return Response.success("站点信息展示成功！", map);
    }
    @Operation(summary = "友链信息展示")
    @Log(title = "友链信息展示")
    @GetMapping("/friendLink")
    public Response<List<FriendLinkDto>> friendLink() {
        return Response.success("友链信息展示成功！", friendLinkService.findList());
    }
}
