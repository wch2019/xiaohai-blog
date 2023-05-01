package com.xiaohai.note.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.dto.ArticleDto;
import com.xiaohai.note.pojo.dto.ArticleDtoAll;
import com.xiaohai.note.pojo.dto.ArticleShowDto;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.query.ArticleQuery;
import com.xiaohai.note.pojo.vo.ArticleVo;

import java.util.Map;

/**
 *
 * 文章表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-04-04
 */
public interface ArticleService extends IService<Article> {


    /**
     * 添加文章表
     *
     * @param vo 文章表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(ArticleVo vo);

    /**
     * 删除文章表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改文章表
     *
     * @param vo 文章表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(ArticleVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Article
*/
    ArticleDtoAll findById(Long id);

    /**
    * 查询文章表列表数据
    *
    * @param query 文章表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<ArticleDto> findListByPage(ArticleQuery query);

    /**
     * 获取随机图片必应
     * @return
     */
    String wallpaper();

    /**
     * 是否顶置
     * @param id
     * @return
     */
    Integer top(Long id);

    /**
     * 是否发布
     * @param id
     * @return
     */
    Integer push(Long id);

    /**
     * 获取所有文章的阅读量
     * @return
     */
    Long getPageView();

    /**
     * 近一年个章贡献度
     * @return
     */
    Map<String, Object> contribution();

    /**
     * 查询展示文章表列表数据
     * @param type
     * @return
     */
    ReturnPageData<ArticleShowDto> findShowListByPage(Integer type);
}
