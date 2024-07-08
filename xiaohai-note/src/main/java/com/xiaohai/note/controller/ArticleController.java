package com.xiaohai.note.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.xiaohai.common.annotation.Log;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.dto.ArticleDto;
import com.xiaohai.note.pojo.dto.ArticleDtoAll;
import com.xiaohai.note.pojo.query.ArticleQuery;
import com.xiaohai.note.pojo.vo.ArticleDraftVo;
import com.xiaohai.note.pojo.vo.ArticleReptileVo;
import com.xiaohai.note.pojo.vo.ArticleVo;
import com.xiaohai.note.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文章表Controller
 *
 * @author xiaohai
 * @since 2023-04-04
 */
@Tag(name = "文章", description = "文章")
@RestController
@RequiredArgsConstructor
@RequestMapping("/note/article")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "新增草稿文章", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission(value = {"note:article:add"}, mode = SaMode.OR)
    @Log(title = "新增草稿文章")
    @PostMapping("/add-draft")
    public Response<Integer> addDraft(@Validated @RequestBody ArticleDraftVo vo) {
        return Response.success("新增草稿成功！", articleService.addDraft(vo));
    }

    @Operation(summary = "新增发布文章", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission(value = {"note:article:add"}, mode = SaMode.OR)
    @Log(title = "新增发布文章")
    @PostMapping()
    public Response<Integer> add(@Validated @RequestBody ArticleVo vo) {
        return Response.success("新增发布文章成功！", articleService.add(vo));
    }

    @Operation(summary = "删除文章", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:delete")
    @Log(title = "删除文章")
    @DeleteMapping("{ids}")
    public Response<Integer> delete(@PathVariable Long[] ids) {
        return Response.success("删除文章成功！", articleService.delete(ids));
    }

    @Operation(summary = "更新草稿文章", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission(value = {"note:article:update"}, mode = SaMode.OR)
    @PutMapping("/update-draft")
    public Response<Integer> updateDraft(@Validated @RequestBody ArticleDraftVo vo) {
        return Response.success("更新草稿成功！", articleService.updateDraft(vo));
    }

    @Operation(summary = "更新发布文章", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission(value = {"note:article:update"}, mode = SaMode.OR)
    @Log(title = "更新发布文章")
    @PutMapping()
    public Response<Integer> update(@Validated @RequestBody ArticleVo vo) {
        return Response.success("更新发布文章成功！", articleService.updateData(vo));
    }


    @Operation(summary = "id查询文章表", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("{id}")
    public Response<ArticleDtoAll> findById(@PathVariable Long id) {
        return Response.success("id查询文章成功！", articleService.findById(id, 1));
    }

    @Operation(summary = "查询文章表列表数据", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @Parameter(name = "pageNum", description = "页码", required = true)
    @Parameter(name = "pageSize", description = "每页数量", required = true)
    @SaCheckPermission("note:article:list")
    @GetMapping()
    public Response<ReturnPageData<ArticleDto>> findListByPage(@ParameterObject ArticleQuery query) {
        return Response.success("查询文章列表成功！", articleService.findListByPage(query));
    }

    @Operation(summary = "获取随机图片必应", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/bing-wallpaper")
    public Response<String> wallpaper() {
        return Response.success("获取随机图片成功！", articleService.wallpaper());
    }

    @Operation(summary = "是否顶置", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:top")
    @Log(title = "是否顶置")
    @PutMapping("/top/{id}")
    public Response<Integer> top(@PathVariable Long id) {
        return Response.success("修改成功！", articleService.top(id));
    }

    @Operation(summary = "是否发布", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:push")
    @Log(title = "是否发布")
    @PutMapping("/push/{id}")
    public Response<Integer> push(@PathVariable Long id) {
        return Response.success("调整成功！", articleService.push(id));
    }

    @Operation(summary = "导入markdown压缩文件", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:import")
    @Log(title = "导入markdown压缩文件")
    @PostMapping(value = "/import/markdown", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Integer> uploadCompressedFile(MultipartFile file) {
        articleService.uploadCompressedFile(file);
        return Response.success("解析markdown压缩文件成功！");
    }

    @Operation(summary = "导出markdown压缩文件", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:download")
    @Parameter(name = "status", description = "是否生成 Front Matter，0否 1是")
    @Log(title = "导出markdown压缩文件")
    @PostMapping(value = "/export/markdown/{status}")
    public Response<Integer>  downloadCompressedFile(@PathVariable Long status) {
        articleService.downloadCompressedFile(status);
        return Response.success("导出markdown压缩文件成功！");
    }

    @Operation(summary = "抓取文章爬虫", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @SaCheckPermission("note:article:reptile")
    @Log(title = "抓取文章爬虫")
    @PostMapping(value = "/reptile-article")
    public Response<Integer> reptileArticle(@Validated @RequestBody ArticleReptileVo vo) {
        return Response.success("抓取文章成功！", articleService.reptileArticle(vo));
    }
}
