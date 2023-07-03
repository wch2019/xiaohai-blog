package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.ArticleLike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.ArticleLikeQuery;
import com.xiaohai.note.pojo.vo.ArticleLikeVo;
import com.xiaohai.note.pojo.dto.ArticleLikeDto;

/**
 *
 * 用户文章点赞表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-07-01
 */
public interface ArticleLikeService extends IService<ArticleLike> {


    /**
     * 添加用户文章点赞表
     *
     * @param vo 用户文章点赞表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(ArticleLikeVo vo);

    /**
     * 删除用户文章点赞表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改用户文章点赞表
     *
     * @param vo 用户文章点赞表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(ArticleLikeVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   ArticleLike
*/
    ArticleLike findById(Long id);

    /**
    * 查询用户文章点赞表列表数据
    *
    * @param query 用户文章点赞表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<ArticleLikeDto> findListByPage(ArticleLikeQuery query);
}
