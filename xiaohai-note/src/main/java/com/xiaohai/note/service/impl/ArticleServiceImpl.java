package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.Contribution;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.ContributionUtils;
import com.xiaohai.common.utils.FileUtils;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.note.dao.*;
import com.xiaohai.note.pojo.dto.*;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.entity.ArticleLike;
import com.xiaohai.note.pojo.entity.Category;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.pojo.query.ArticleQuery;
import com.xiaohai.note.pojo.vo.ArticleVo;
import com.xiaohai.note.service.ArticleService;
import com.xiaohai.note.service.ArticleTagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 文章表 服务实现类
 *
 * @author xiaohai
 * @since 2023-04-04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    private final FileConfig fileConfig;

    private final ArticleTagService articleTagService;

    private final ArticleTagMapper articleTagMapper;

    private final CategoryMapper categoryMapper;

    private final TagsMapper tagsMapper;

    private final ArticleLikeMapper articleLikeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(ArticleVo vo) {
        Article article = new Article();
        BeanUtils.copyProperties(vo, article);
        //写入作者
        article.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        //顶置写入时间
        if (article.getIsTop() == 1) {
            var countTop = baseMapper.selectCount(new QueryWrapper<Article>().eq("is_top", 1));
            Assert.isTrue(countTop < 1, "已存在顶置");
            article.setTopTime(LocalDateTime.now());
        }
        article.setCreatedTime(LocalDateTime.now());
        article.setUpdatedTime(LocalDateTime.now());
        var count = baseMapper.insert(article);
        //新增标签
        articleTagService.add(vo.getTags(), article.getId());
        //统计持续创作天数
        ContributionUtils.setContribution();
        return count;
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            //删除标签关联
            articleTagService.delete(Math.toIntExact(id));
            //删除文章
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateData(ArticleVo vo) {
        Article article = new Article();
        BeanUtils.copyProperties(vo, article);
        //顶置写入时间
        if (article.getIsTop() == 1) {
            var countTop = baseMapper.selectCount(new QueryWrapper<Article>().eq("is_top", 1).ne("id", article.getId()));
            Assert.isTrue(countTop < 1, "已存在顶置");
            article.setTopTime(LocalDateTime.now());
        } else {
            article.setTopTime(null);
        }
        //更新标签
        articleTagService.rewriteArticleTag(vo.getTags(), article.getId());
        article.setUpdatedTime(LocalDateTime.now());
        //统计持续创作天数
        ContributionUtils.setContribution();
        return baseMapper.updateById(article);
    }

    @Override
    public ArticleDtoAll findById(Long id,int type) {
        ArticleDtoAll articleDtoAll = new ArticleDtoAll();
        Article article = baseMapper.selectById(id);
        BeanUtils.copyProperties(article, articleDtoAll);
        articleDtoAll.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName());
        articleDtoAll.setTags(articleTagMapper.searchAllByArticleId(id));
        //当前文章点赞数
        articleDtoAll.setLikeCount(articleLikeMapper.selectCount(new QueryWrapper<ArticleLike>().eq("article_id", articleDtoAll.getId())));
        //获取登录用户是否点赞
        if(StpUtil.isLogin()) {
            long count = articleLikeMapper.selectCount(new QueryWrapper<ArticleLike>()
                    .eq("user_id", StpUtil.getLoginId())
                    .eq("article_id", articleDtoAll.getId()));
            if (count == 1) {
                articleDtoAll.setClickLike(1);
            } else {
                articleDtoAll.setClickLike(0);
            }
        }
        //用户详情
        articleDtoAll.setUserBasic(baseMapper.findUserBasic(Long.valueOf(article.getUserId())));
        //更新浏览量
        if(type==0){
            Article articleCount=new Article();
            articleCount.setId(article.getId());
            articleCount.setPageView(article.getPageView()+1);
            baseMapper.updateById(articleCount);
        }
        return articleDtoAll;
    }

    @Override
    public ReturnPageData<ArticleDto> findListByPage(ArticleQuery query) {
        Integer userId=null;
        //判断角色是否是管理员
        if(!StpUtil.hasRole(Constants.ADMIN)){
            //不是管理员只查询当前用户数据
            userId=Integer.valueOf((String)StpUtil.getLoginId());
        }
        IPage<ArticleDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<ArticleDto> iPage = baseMapper.selectPageArticleQuery(wherePage, query,userId);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public String wallpaper() {
        String url = "https://cn.bing.com";
        String name = "";
        try {
            // 创建 httpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=8&mkt=zh-CN"))
                    .timeout(Duration.ofSeconds(10))
                    .build();
            // 创建 httpClient
            HttpClient client = HttpClient.newHttpClient();
            // 由httpClient发送请求，获取响应数据
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            JSONObject parse = JSON.parseObject(body);
            JSONArray jsonArray = JSON.parseArray(parse.get("images").toString());
            //随机获取0-7
            Random random = new Random();
            int randomNum = random.nextInt(8);
            JSONObject result = (JSONObject) jsonArray.get(randomNum);
            url = url + result.get("url");
            name = result.get("startdate").toString();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.isTrue(!"https://cn.bing.com".equals(url), "获取图片失败");
        //指定markdown图片上传目录,根据用户区分文件夹
        String path = fileConfig.getImagePath() + StpUtil.getLoginId() + "/";
        FileUtils.download(url, path, name + ".jpg");
        path = path.replaceAll("\\\\", "/").replace(fileConfig.getProfile(), "");
        return "/" + path + name + ".jpg";
    }

    @Override
    public Integer top(Long id) {
        Article article = baseMapper.selectById(id);
        if (article.getIsTop() == 1) {
            article.setIsTop(0);
            article.setTopTime(null);
        } else {
            var countTop = baseMapper.selectCount(new QueryWrapper<Article>().eq("is_top", 1));
            Assert.isTrue(countTop < 1, "已存在顶置");
            article.setIsTop(1);
            article.setTopTime(LocalDateTime.now());
        }
        return baseMapper.updateById(article);
    }

    @Override
    public Integer push(Long id) {
        Article article = baseMapper.selectById(id);
        if (article.getIsPush() == 1) {
            article.setIsPush(0);
        } else {
            article.setIsPush(1);
        }
        return baseMapper.updateById(article);
    }

    @Override
    public Long getPageView() {
        return baseMapper.getPageView();
    }

    @Override
    public Map<String, Object> contribution() {
        Map<String, Object> data = new HashMap<>();
        List<DateCount> timeValue = baseMapper.getCreatedTime();
        Contribution contribution = ContributionUtils.getContribution();
        //最近一年文章
        data.put("oneYear", timeValue.stream().map(DateCount::getCount).mapToLong(Long::intValue).sum());
        //最长连续创作
        data.put("longest", contribution.getLongest());
        //最近持续持续
        data.put("continuous", contribution.getContinuous());
        //统计表
        data.put("timeValue", timeValue);
        return data;
    }

    @Override
    @Transactional
    public ReturnPageData<ArticleShowDto> findShowListByPage(Integer type,Long id) {
        IPage<ArticleShowDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        //1:最新文章,2:最热文章,3:原创文章,4:转载文章,5:标签id,6:分类id
        IPage<ArticleShowDto> iPage = baseMapper.findShowListByPage(wherePage,type,id);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        //标签
        if(type==5){
            Tags tag=new Tags();
            tag.setId(Math.toIntExact(id));
            tag.setClick(tagsMapper.selectById(Math.toIntExact(id)).getClick()+1);
            tagsMapper.updateById(tag);
        }
        //分类
        if(type==6){
            Category category=new Category();
            category.setId(Math.toIntExact(id));
            category.setClick(categoryMapper.selectById(Math.toIntExact(id)).getClick()+1);
            categoryMapper.updateById(category);
        }
        // 登录用户获取是否点赞
        if(StpUtil.isLogin()){
            for (ArticleShowDto showDto : iPage.getRecords()) {
                long count=articleLikeMapper.selectCount(new QueryWrapper<ArticleLike>().eq("user_id",StpUtil.getLoginId()).eq("article_id",showDto.getId()));
                if(count==1){
                    showDto.setClickLike(1);
                }else {
                    showDto.setClickLike(0);
                }
            }
        }
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public ReturnPageData<ArticleShowDto> findBackListByPage() {
        IPage<ArticleShowDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<ArticleShowDto> iPage = baseMapper.findBackListByPage(wherePage);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, iPage.getRecords());
    }

    @Override
    public List<ArticleSearchDto> searchArticle(String keywords) {
        // 搜索文章
        List<Article> articles = baseMapper.selectList(new QueryWrapper<Article>()
                .eq("is_push",1)
                .like("title",keywords)
                .or().like("summary",keywords).
                orderByDesc("is_top").
                orderByDesc("top_time").
                orderByDesc("created_time"));
        List<ArticleSearchDto> list=new ArrayList<>();
        for (Article article:articles){
            ArticleSearchDto articleSearchDto=new ArticleSearchDto();
            // 文章标题高亮
            String articleTitle = article.getTitle().replaceAll(keywords, Constants.PRE_TAG + keywords + Constants.POST_TAG);
            // 文章简介高亮
            String summary = article.getSummary().replaceAll(keywords, Constants.PRE_TAG + keywords + Constants.POST_TAG);
            articleSearchDto.setTitle(articleTitle);
            articleSearchDto.setSummary(summary);
            articleSearchDto.setId(article.getId());
            list.add(articleSearchDto);
        }
        return list;
    }

    @Override
    public UserBasicDto findShowBasic() {
        return baseMapper.findShowBasic();
    }

}
