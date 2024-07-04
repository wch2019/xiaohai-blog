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
import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.daomain.Contribution;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.utils.*;
import com.xiaohai.file.pojo.vo.UploadVo;
import com.xiaohai.file.service.FileService;
import com.xiaohai.note.dao.*;
import com.xiaohai.note.pojo.dto.*;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.entity.ArticleLike;
import com.xiaohai.note.pojo.entity.Category;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.pojo.query.ArticleQuery;
import com.xiaohai.note.pojo.vo.ArticleDraftVo;
import com.xiaohai.note.pojo.vo.ArticleReptileVo;
import com.xiaohai.note.pojo.vo.ArticleVo;
import com.xiaohai.note.service.ArticleService;
import com.xiaohai.note.service.ArticleTagService;
import com.xiaohai.spider.article.Acquire;
import com.xiaohai.spider.pojo.ArticleAcquire;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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

    private final FileService fileService;

    @Override
    public Integer addDraft(ArticleDraftVo vo) {
        Article article = new Article();
        BeanUtils.copyProperties(vo, article);
        //写入作者
        article.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        article.setCreatedTime(LocalDateTime.now());
        article.setUpdatedTime(LocalDateTime.now());
        baseMapper.insert(article);
        //统计持续创作天数
        ContributionUtils.setContribution();
        return article.getId();
    }

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
        //改为发布
        article.setIsPush(1);
        article.setCreatedTime(LocalDateTime.now());
        article.setUpdatedTime(LocalDateTime.now());
        baseMapper.insert(article);
        //新增标签
        articleTagService.add(vo.getTags(), article.getId());
        //统计持续创作天数
        ContributionUtils.setContribution();
        return article.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            Article oldArticle = baseMapper.selectById(id);
            if (!Objects.equals(oldArticle.getUserId(), Integer.valueOf((String) StpUtil.getLoginId())) && !StpUtil.hasRole(Constants.ADMIN)) {
                throw new ServiceException("非当前用户数据无法删除");
            }
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
        Article oldArticle = baseMapper.selectById(vo.getId());
        if (!Objects.equals(oldArticle.getUserId(), Integer.valueOf((String) StpUtil.getLoginId())) && !StpUtil.hasRole(Constants.ADMIN)) {
            throw new ServiceException("非当前用户数据无法更新");
        }
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
        //改为发布
        article.setIsPush(1);
        article.setUpdatedTime(LocalDateTime.now());
        //统计持续创作天数
        ContributionUtils.setContribution();
        return baseMapper.updateById(article);
    }

    @Override
    public Integer updateDraft(ArticleDraftVo vo) {
        Article oldArticle = baseMapper.selectById(vo.getId());
        if (!Objects.equals(oldArticle.getUserId(), Integer.valueOf((String) StpUtil.getLoginId())) && !StpUtil.hasRole(Constants.ADMIN)) {
            throw new ServiceException("非当前用户数据无法更新");
        }
        Article article = new Article();
        BeanUtils.copyProperties(vo, article);
        article.setUpdatedTime(LocalDateTime.now());
        //改为草稿
        article.setIsPush(0);
        //统计持续创作天数
        ContributionUtils.setContribution();
        return baseMapper.updateById(article);
    }

    @Override
    public ArticleDtoAll findById(Long id, int type) {

        ArticleDtoAll articleDtoAll = new ArticleDtoAll();
        Article article = baseMapper.selectById(id);
        if (article == null) {
            return articleDtoAll;
        }
        Integer userId = StpUtil.getLoginIdDefaultNull() == null ? null : Integer.valueOf((String) StpUtil.getLoginId());
        // 没有发布的文章只能自己查看
        if (article.getIsPush() == 0 && !article.getUserId().equals(userId) && !StpUtil.hasRole(Constants.ADMIN)) {
            return articleDtoAll;
        }
        BeanUtils.copyProperties(article, articleDtoAll);
        if (articleDtoAll.getCategoryId() != null) {
            articleDtoAll.setCategoryName(categoryMapper.selectById(articleDtoAll.getCategoryId()).getName());
        }
        articleDtoAll.setTags(articleTagMapper.searchAllByArticleId(id));
        //当前文章点赞数
        articleDtoAll.setLikeCount(articleLikeMapper.selectCount(new QueryWrapper<ArticleLike>().eq("article_id", articleDtoAll.getId())));
        //获取登录用户是否点赞
        if (StpUtil.isLogin()) {
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
        if (type == 0) {
            Article articleCount = new Article();
            articleCount.setId(article.getId());
            articleCount.setPageView(article.getPageView() + 1);
            baseMapper.updateById(articleCount);
        }
        return articleDtoAll;
    }

    @Override
    public ReturnPageData<ArticleDto> findListByPage(ArticleQuery query) {
        Integer userId = null;
        //判断角色是否是管理员和demo
        if (RoleUtils.checkRole()) {
            //不是管理员、demo只查询当前用户数据
            userId = Integer.valueOf((String) StpUtil.getLoginId());
        }
        IPage<ArticleDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<ArticleDto> iPage = baseMapper.selectPageArticleQuery(wherePage, query, userId);
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
        String fileName = name + ".jpg";
        MultipartFile multipartFile = FileUtil.getFileFromUrl(url, fileName);
        //指定公共markdown图片上传目录
        String path = fileConfig.getProfile() + FileConstants.IMAGE_FILE + File.separator + FileConstants.BING_FILE;
        //        fileService.uploadBing(multipartFile, path, fileName);
        return fileService.uploadImage(multipartFile);
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
        Assert.isTrue(article.getCategoryId() != null, "数据不完整无法发布");
        if (article.getIsPush() == 1) {
            article.setIsPush(0);
        } else {
            article.setIsPush(1);
        }
        return baseMapper.updateById(article);
    }

    @Override
    public Long getPageView() {
        Integer userId = null;
        if (RoleUtils.checkRole()) {
            userId = Integer.valueOf((String) StpUtil.getLoginId());
        }
        return baseMapper.getPageView(userId);
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
    public ReturnPageData<ArticleShowDto> findShowListByPage(Integer type, Long id) {
        IPage<ArticleShowDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        //1:最新文章,2:最热文章,3:原创文章,4:转载文章,5:标签id,6:分类id
        IPage<ArticleShowDto> iPage = baseMapper.findShowListByPage(wherePage, type, id);
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        //标签
        if (type == 5) {
            Tags tag = new Tags();
            tag.setId(Math.toIntExact(id));
            tag.setClick(tagsMapper.selectById(Math.toIntExact(id)).getClick() + 1);
            tagsMapper.updateById(tag);
        }
        //分类
        if (type == 6) {
            Category category = new Category();
            category.setId(Math.toIntExact(id));
            category.setClick(categoryMapper.selectById(Math.toIntExact(id)).getClick() + 1);
            categoryMapper.updateById(category);
        }
        // 登录用户获取是否点赞
        if (StpUtil.isLogin()) {
            for (ArticleShowDto showDto : iPage.getRecords()) {
                long count = articleLikeMapper.selectCount(new QueryWrapper<ArticleLike>().eq("user_id", StpUtil.getLoginId()).eq("article_id", showDto.getId()));
                if (count == 1) {
                    showDto.setClickLike(1);
                } else {
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
                .eq("is_push", 1)
                .like("title", keywords)
                .or().like("summary", keywords).
                orderByDesc("is_top").
                orderByDesc("top_time").
                orderByDesc("created_time"));
        List<ArticleSearchDto> list = new ArrayList<>();
        for (Article article : articles) {
            ArticleSearchDto articleSearchDto = new ArticleSearchDto();
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadCompressedFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取上传文件的原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // 获取文件后缀名
        String fileExtension = FileUtil.getFileExtension(originalFilename);

        // 判断文件后缀名是否为压缩类型
        if (!FileUtil.isCompressExtension(fileExtension)) {
            throw new ServiceException("请查看压缩类型是否正确" + Arrays.toString(FileConstants.COMPRESS_EXTENSION));
        }

        String tempFile = StringUtil.generateUUIDWithoutHyphens();

        //压缩文件临时路径
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.TEMPORARY_FILE + File.separator + FileConstants.IMPORT_FILE;
        fileService.createFolderIfNotExists(path);
        path = path + File.separator + tempFile;
        FileUtil.directory(path);
        // 保存文件并返回文件路径
        String filePath = FileUtil.saveFile(path, file.getOriginalFilename(), file);
        try {
            // 解压缩文件到指定文件夹
            MarkdownUtils.unZip(new File(filePath), path);

            //获取解压后的markdown文件列表
            List<String> list = MarkdownUtils.fileList(path + File.separator + FileConstants.NOTE_FILE);
            if (list.isEmpty()) {
                throw new ServiceException("没有可用markdown文件");
            }
            //图片文件新存放处
            String newPath = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE;

            for (String mdFilePath : list) {
                //获取markdown解析文件
                Map<String, Object> postData = MarkdownUtils.parseHexoPost(mdFilePath);
                Article article = new Article();
                List<String> tags = new ArrayList<>();
                // 打印博文数据
                for (Map.Entry<String, Object> entry : postData.entrySet()) {
                    if (entry.getKey().equals("title")) {
                        //标题
                        article.setTitle(entry.getValue().toString());
                    }
                    if (entry.getKey().equals("cover")) {
                        if (StringUtil.isNotBlank(entry.getValue().toString())) {
                            if (entry.getValue().toString().startsWith("http")) {
                                //封面
                                article.setCover(entry.getValue().toString());
                            } else {
                                String sourcePath = path + entry.getValue().toString().replace("..", "");
                                //封面
                                article.setCover(fileService.getCopyImage(sourcePath, newPath));
                            }
                        }
                    }
                    if (entry.getKey().equals("categories")) {
                        if (StringUtil.isNotBlank(entry.getValue().toString())) {
                            //分类
                            Category category = categoryMapper.selectOne(new QueryWrapper<Category>().eq("name", entry.getValue().toString()));
                            if (category == null) {
                                //不存在就创建
                                category = new Category();
                                category.setName(entry.getValue().toString());
                                categoryMapper.insert(category);
                            }
                            article.setCategoryId(category.getId());
                        }
                    }
                    if (entry.getKey().equals("date") && StringUtils.isNotBlank(entry.getValue().toString())) {
                        //创建时间
                        article.setCreatedTime(DateUtils.getLocalDateTimeToString(entry.getValue().toString()));
                    }else{
                        article.setCreatedTime(LocalDateTime.now());
                    }
                    if (entry.getKey().equals("updated") && StringUtils.isNotBlank(entry.getValue().toString())) {
                        //更新时间
                        article.setUpdatedTime(DateUtils.getLocalDateTimeToString(entry.getValue().toString()));
                    }
                    if (entry.getKey().equals("original") && StringUtils.isNotBlank(entry.getValue().toString())) {
                        //转载地址
                        article.setOriginalUrl(entry.getValue().toString());
                        article.setIsOriginal(1);
                    }

                    if (entry.getKey().equals("content")) {
                        if (StringUtil.isNotBlank(entry.getValue().toString())) {
                            //文章内容
                            article.setText(entry.getValue().toString());
                            List<String> photoList = MarkdownUtils.photoList(article.getText());
                            for (String fileName : photoList) {
                                String sourcePath = path + fileName.replace("..", "");
                                //新图片位置
                                String newPhotoPath = ".." + fileService.getCopyImage(sourcePath, newPath);
                                article.setText(article.getText().replaceAll(fileName, newPhotoPath));
                            }
                            article.setSummary(MarkdownUtils.truncateText(article.getText(), 100));
                        }
                    }
                    if (entry.getKey().equals("tags")) {
                        //标签
                        tags = (List<String>) entry.getValue();
                    }
                }
                //封面
                if (StringUtil.isBlank(article.getCover())) {
                    //为空手动添加一个封面
                    article.setCover(wallpaper());
                }

                //写入更新时间
                article.setUpdatedTime(LocalDateTime.now());
                //写入作者
                article.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
                //写入文章
                baseMapper.insert(article);
                //新增标签
                articleTagService.addTagName(tags, article.getId());
                //统计持续创作天数
                ContributionUtils.setContribution();
            }

        } finally {
            //都要执行删除临时文件
            FileUtil.deleteFiles(new File(path));
            //删除当前目录
            FileUtil.deleteFile(path);
        }
    }

    @Override
    public void downloadCompressedFile() {
        List<Article> articles = baseMapper.selectList(new QueryWrapper<Article>().
                eq("user_id", StpUtil.getLoginId()).
                orderByDesc("is_top").
                orderByDesc("top_time").
                orderByDesc("created_time"));
        String tempFile = StringUtil.generateUUIDWithoutHyphens();
        //压缩文件临时路径
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.TEMPORARY_FILE + File.separator + FileConstants.EXPORT_FILE + File.separator + tempFile + File.separator;
        try {
            for (Article article : articles) {
                //图片路径
                String image = fileConfig.getProfile() + article.getCover();
                String newImage = path + FileConstants.IMAGE_FILE + File.separator;
                //创建目录
                FileUtil.directory(newImage);
                //新图片位置
                String newPhotoPath = FileUtil.copyFile(image, newImage, true);
                //封面图片
                String cover = ".." + newPhotoPath.replace(path, "/").replace("\\", "/");
                //获取分类名称
                Category category = categoryMapper.selectById(article.getCategoryId());
                //获取标签名称列表
                List<String> tags = tagsMapper.searchAllByArticleId(Long.valueOf(article.getId()));
                //获取转载地址
                String original = StringUtils.isBlank(article.getOriginalUrl()) ? "" : article.getOriginalUrl();
                //组装Front-matter头
                String matter = MarkdownUtils.buildMarkdownHeader(article.getTitle(), article.getCreatedTime(), article.getUpdatedTime(), tags, category.getName(), cover, original);
                //将文章里面的图片获取并存到临时位置，并替换路径
                List<String> photoList = MarkdownUtils.photoList(article.getText());
                for (String fileName : photoList) {
                    //新图片位置
                    String notePhotoPath = FileUtil.copyFile(fileConfig.getProfile() + fileName.replace("..", ""), newImage, true);
                    //去掉前缀
                    notePhotoPath = ".." + notePhotoPath.replace(path, File.separator);
                    article.setText(article.getText().replaceAll(fileName, notePhotoPath.replace("\\", "/")));
                }
                String text = matter + article.getText();
                //md文件路径
                String note = path + FileConstants.NOTE_FILE + File.separator;
                //创建目录
                FileUtil.directory(note);
                note = note + FileUtil.sanitizeFileName(article.getTitle()) + "." + FileConstants.MARKDOWN_EXTENSION;
                // 替换文件名中的不支持字符
                //本地创建md文件
                MarkdownUtils.createMarkdownFile(note, text);
            }
            String zipFile = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.TEMPORARY_FILE + File.separator + FileConstants.EXPORT_FILE;
            var vo = new UploadVo();
            vo.setFile(ZipUtils.zipDirectoryToMultipartFile(path, DateUtils.getCurrentTime()));
            vo.setPath(zipFile.replace(fileConfig.getProfile(), File.separator));
            fileService.upload(vo);
        } finally {
            //执行删除临时文件
            FileUtil.deleteFiles(new File(path));
            //删除当前目录
            FileUtil.deleteFile(path);
        }
    }

    @Override
    public Integer reptileArticle(ArticleReptileVo vo) {
        ArticleAcquire articleAcquire = new ArticleAcquire();
        if (vo.getType().equals("CSDN")) {
            articleAcquire = Acquire.csdn(vo.getUrl());
        }
        if (vo.getType().equals("juejin")) {
            articleAcquire = Acquire.juejin(vo.getUrl());
        }
        if (vo.getType().equals("jianshu")) {
            articleAcquire = Acquire.jianshu(vo.getUrl());
        }
        if (vo.getType().equals("bokeyuan")) {
            articleAcquire = Acquire.bokeyuan(vo.getUrl());
        }
        if (vo.getType().equals("zhihu")) {
            articleAcquire = Acquire.zhihu(vo.getUrl());
        }
        if (StringUtil.isBlank(articleAcquire.getTitle())) {
            throw new ServiceException("没有获取到数据请检查链接");
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleAcquire, article);
        //添加随机封面
        article.setCover(wallpaper());
        //转载
        article.setIsOriginal(1);
        //写入作者
        article.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        article.setCreatedTime(LocalDateTime.now());
        article.setUpdatedTime(LocalDateTime.now());
        baseMapper.insert(article);
        //统计持续创作天数
        ContributionUtils.setContribution();
        return article.getId();
    }
}
