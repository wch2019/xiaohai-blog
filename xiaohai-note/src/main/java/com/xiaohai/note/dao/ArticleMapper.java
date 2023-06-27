package com.xiaohai.note.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.note.pojo.dto.ArticleDto;
import com.xiaohai.note.pojo.dto.ArticleShowDto;
import com.xiaohai.note.pojo.dto.DateCount;
import com.xiaohai.note.pojo.dto.UserBasicDto;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.query.ArticleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-04-04
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 查询文章表列表数据
     * @param wherePage
     * @param query
     * @return
     */
    IPage<ArticleDto> selectPageArticleQuery(@Param("page")IPage<ArticleDto> wherePage, @Param("param") ArticleQuery query,@Param("userId") Integer userId);

    /**
     * 获取所有文章的阅读量
     * @return
     */
    Long getPageView();

    /**
     * 获取当前年所有文章时间用作统计贡献度
     * @return
     */
    List<DateCount> getCreatedTime();

    /**
     * 查询文章表列表数据
     * @param wherePage
     * @param type
     * @return
     */
    IPage<ArticleShowDto> findShowListByPage(@Param("page")IPage<ArticleShowDto> wherePage,@Param("type")Integer type,@Param("id")Long id);

    /**
     * 查询归档列表数据
     * @param wherePage
     * @return
     */
    IPage<ArticleShowDto> findBackListByPage(@Param("page")IPage<ArticleShowDto> wherePage);

    /**
     * 查询指定用户基础信息 文章相关的
     * @param userId
     * @return
     */
    UserBasicDto findUserBasic(Long userId);

    /**
     * 展示页信息展示
     * @return
     */
    UserBasicDto findShowBasic();


}
