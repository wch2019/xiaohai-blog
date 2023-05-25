package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.CommentQuery;
import com.xiaohai.note.pojo.vo.CommentVo;
import com.xiaohai.note.pojo.dto.CommentDto;

/**
 *
 * 评论表 服务类
 *
 *
 * @author xiaohai
 * @since 2023-05-24
 */
public interface CommentService extends IService<Comment> {


    /**
     * 添加评论表
     *
     * @param vo 评论表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(CommentVo vo);

    /**
     * 删除评论表
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改评论表
     *
     * @param vo 评论表 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(CommentVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Comment
*/
    Comment findById(Long id);

    /**
    * 查询评论表列表数据
    *
    * @param query 评论表 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<CommentDto> findListByPage(CommentQuery query);
}
