package com.xiaohai.system.service;

import com.xiaohai.system.pojo.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.system.pojo.query.FeedbackQuery;
import com.xiaohai.system.pojo.vo.FeedbackVo;
import com.xiaohai.system.pojo.dto.FeedbackDto;

/**
 *
 * 用户反馈 服务类
 *
 *
 * @author xiaohai
 * @since 2023-08-03
 */
public interface FeedbackService extends IService<Feedback> {


    /**
     * 添加用户反馈
     *
     * @param vo 用户反馈 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer add(FeedbackVo vo);

    /**
     * 删除用户反馈
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改用户反馈
     *
     * @param vo 用户反馈 VO（View Object）：显示层对象
     * @return Integer
     */
    Integer updateData(FeedbackVo vo);

    /**
     * id查询数据
     *
     * @param id id
     * @return   Feedback
*/
    Feedback findById(Long id);

    /**
    * 查询用户反馈列表数据
    *
    * @param query 用户反馈 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<FeedbackDto> findListByPage(FeedbackQuery query);
}
