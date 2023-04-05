package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.ArticleTagQuery;
import com.xiaohai.note.pojo.vo.ArticleTagVo;
import com.xiaohai.note.pojo.dto.ArticleTagDto;

/**
 *
 * 文章标签关联 服务类
 *
 *
 * @author xiaohai
 * @since 2023-04-04
 */
public interface ArticleTagService extends IService<ArticleTag> {


    /**
     * 添加文章标签关联
     *
     * @param vo 文章标签关联 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(ArticleTagVo vo);

    /**
     * 删除文章标签关联
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改文章标签关联
     *
     * @param vo 文章标签关联 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(ArticleTagVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   ArticleTag
*/
    ArticleTag findById(Long id);

    /**
    * 查询文章标签关联列表数据
    *
    * @param query 文章标签关联 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<ArticleTagDto> findListByPage(ArticleTagQuery query);
}
