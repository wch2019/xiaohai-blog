package com.xiaohai.spider.article;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.utils.MarkdownUtils;
import com.xiaohai.spider.pojo.ArticleAcquire;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author wangchenghai
 * @date 2024/02/27 17:47:50
 */
@Slf4j
public class Acquire {

    /**
     * CSDN文章获取
     *
     * @param url
     * @return com.xiaohai.spider.pojo.ArticleAcquire
     * @author wangchenghai
     * @date 2024/02/28 16:16:44
     */
    public static ArticleAcquire csdn(String url) {
        ArticleAcquire articleAcquire = new ArticleAcquire();
        articleAcquire.setOriginalUrl(url);
        try {
            log.info("CSDN爬取地址：{}", url);
            Document doc = Jsoup.connect(url).get();
            // 获取文章标题
            Element articleContent = doc.selectFirst("#articleContentId");
            if (articleContent != null) {
                log.info("文章标题：{}", articleContent.text());
                articleAcquire.setTitle(articleContent.text());
            }
            // 获取文章内容
            Element contentElement = doc.selectFirst("#content_views");
            if (contentElement != null) {
                MutableDataSet options = new MutableDataSet();
                FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder(options).build();
                var result = converter.convert(contentElement.html()
                        .replaceAll("prism language-", "")
                        .replaceAll("language-", ""));
                articleAcquire.setText(result);
                articleAcquire.setSummary(MarkdownUtils.truncateText(result, 100));
            }
        } catch (Exception e) {
            log.error("CSDN爬虫出现意外", e);
            throw new ServiceException("CSDN爬虫出现意外", e);
        }
        return articleAcquire;
    }

    /**
     * 掘金文章获取
     *
     * @param url
     * @return com.xiaohai.spider.pojo.ArticleAcquire
     * @author wangchenghai
     * @date 2024/02/28 16:17:25
     */
    public static ArticleAcquire juejin(String url) {
        ArticleAcquire articleAcquire = new ArticleAcquire();
        articleAcquire.setOriginalUrl(url);
        try {
            log.info("掘金爬取地址：{}", url);
            Document doc = Jsoup.connect(url).get();
            // 获取文章标题
            Elements articleContent = doc.getElementsByClass("article-title");
            log.info("文章标题：{}", articleContent.text());
            articleAcquire.setTitle(articleContent.text());
            // 获取文章内容
            Element contentElement = doc.selectFirst("#article-root");
            for (Element codeElement : doc.select("code")) {
                codeElement.attr("class", "prism");
            }
            FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder().build();
            var result = converter.convert(contentElement);
            articleAcquire.setText(result);
            articleAcquire.setSummary(MarkdownUtils.truncateText(result, 100));
        } catch (Exception e) {
            log.error("掘金爬虫出现意外", e);
            throw new ServiceException("掘金爬虫出现意外", e);
        }
        return articleAcquire;
    }


    /**
     * 简书文章获取
     *
     * @param url
     * @return com.xiaohai.spider.pojo.ArticleAcquire
     * @author wangchenghai
     * @date 2024/02/28 16:17:06
     */
    public static ArticleAcquire jianshu(String url) {
        ArticleAcquire articleAcquire = new ArticleAcquire();
        articleAcquire.setOriginalUrl(url);
        try {
            log.info("简书爬取地址：{}", url);
            Document doc = Jsoup.connect(url).get();
            // 获取文章标题
            Elements articleContent = doc.getElementsByClass("_1RuRku");
            log.info("文章标题：{}", articleContent.text());
            articleAcquire.setTitle(articleContent.text());
            // 获取文章内容
            Elements contentElement = doc.getElementsByClass("_2rhmJa");
            MutableDataSet options = new MutableDataSet();
            FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder(options).build();
            var result = converter.convert(contentElement.get(0).html());
            articleAcquire.setText(result);
            articleAcquire.setSummary(MarkdownUtils.truncateText(result, 100));
        } catch (Exception e) {
            log.error("简书爬虫出现意外", e);
            throw new ServiceException("简书爬虫出现意外", e);
        }
        return articleAcquire;
    }

    /**
     * 博客园
     *
     * @param url
     * @return com.xiaohai.spider.pojo.ArticleAcquire
     * @author wangchenghai
     * @date 2024/02/29 11:11:07
     */
    public static ArticleAcquire bokeyuan(String url) {
        ArticleAcquire articleAcquire = new ArticleAcquire();
        articleAcquire.setOriginalUrl(url);
        try {
            log.info("博客园爬取地址：{}", url);
            Document doc = Jsoup.connect(url).get();
            // 获取文章标题
            Element articleContent = doc.selectFirst("#cb_post_title_url");
            log.info("文章标题：{}", articleContent.text());
            articleAcquire.setTitle(articleContent.text());
            // 获取文章内容
            Element contentElement = doc.selectFirst("#cnblogs_post_body");
            MutableDataSet options = new MutableDataSet();
            FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder(options).build();
            var result = converter.convert(contentElement);
            articleAcquire.setText(result);
            articleAcquire.setSummary(MarkdownUtils.truncateText(result, 100));
        } catch (Exception e) {
            log.error("博客园爬虫出现意外", e);
            throw new ServiceException("简书爬虫出现意外", e);
        }
        return articleAcquire;
    }

    /**
     * 知乎
     * @author wangchenghai
     * @date  2024/03/01 15:36:36
     * @param url
     * @return com.xiaohai.spider.pojo.ArticleAcquire
     */
    public static ArticleAcquire zhihu(String url) {
        ArticleAcquire articleAcquire = new ArticleAcquire();
        articleAcquire.setOriginalUrl(url);
        try {
            log.info("知乎爬取地址：{}", url);
            Document doc = Jsoup.connect(url).get();
            // 获取文章标题
            Elements articleContent = doc.getElementsByClass("Post-Title");
            log.info("文章标题：{}", articleContent.text());
            articleAcquire.setTitle(articleContent.text());
            // 获取文章内容
            Elements contentElement = doc.getElementsByClass("css-376mun");
            MutableDataSet options = new MutableDataSet();
            FlexmarkHtmlConverter converter = FlexmarkHtmlConverter.builder(options).build();
            var result = converter.convert(contentElement.get(0).html());
            articleAcquire.setText(result);
            articleAcquire.setSummary(MarkdownUtils.truncateText(result, 100));
        } catch (Exception e) {
            log.error("知乎爬虫出现意外", e);
            throw new ServiceException("知乎爬虫出现意外", e);
        }
        return articleAcquire;
    }

}