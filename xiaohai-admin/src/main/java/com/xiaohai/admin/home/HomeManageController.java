package com.xiaohai.admin.home;

import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.OnLineUser;
import com.xiaohai.common.daomain.Response;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.OnLineUtils;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.service.ArticleService;
import com.xiaohai.note.service.CategoryService;
import com.xiaohai.note.service.CommentService;
import com.xiaohai.note.service.TagsService;
import com.xiaohai.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 管理后台首页
 * @author: xiaohai
 * @date: 2023-04-16 15:46
 **/
@Tag(name = "管理后台首页", description = "管理后台首页 ")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/manage")
public class HomeManageController {

    private final UserService userService;
    private final ArticleService articleService;
    private final TagsService tagsService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @Operation(summary = "文章数,用户数,评论数，浏览量", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/count")
    public Response<Map<String, Long>> getCount() {
        Map<String, Long> map = new HashMap<>();
        //文章数
        map.put("article", articleService.count());
        //评论数
        map.put("message", commentService.count());
        //用户数
        map.put("user", userService.count());
        //阅读量
        map.put("views", articleService.getPageView());
        return Response.success("获取文章数,用户数,评论数，浏览量成功！", map);
    }

    @Operation(summary = "近一年文章贡献度", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/contribution")
    public Response<Map<String, Object>> getNote() {
        return Response.success("获取近一年文章贡献度成功！", articleService.contribution());
    }

    @Operation(summary = "分类，标签，文章阅读量排行", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/rank")
    public Response<Map<String, Object>> getRank() {
        Map<String, Object> map = new HashMap<>();
        //分类
        map.put("category", categoryService.pieChart());
        //标签
        map.put("tags", tagsService.optionSelect());
        //文章阅读量排行
        map.put("rank", articleService.list(new LambdaQueryWrapper<Article>()
                .select(Article::getPageView,Article::getTitle,Article::getId)
                .eq(Article::getIsPush, 1)
                .orderByDesc(Article::getPageView).last("limit 6"))
        );
        return Response.success("获取分类，标签，文章阅读量排行成功！", map);
    }

    @Operation(summary = "一周访问量", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/pv")
    public Response<ReturnPageData<OnLineUser>> getPv() {
        return Response.success("获取一周访问量成功！", OnLineUtils.getOnLineUserList());
    }

    @Operation(summary = "随机输出毒鸡汤", security = {@SecurityRequirement(name = Constants.SESSION_ID)})
    @GetMapping("/word")
    public Response<String> getWord() {
        return Response.success("随机输出毒鸡汤", HttpUtil.get("https://api.btstu.cn/yan/api.php?charset=utf-8"));
    }
}
