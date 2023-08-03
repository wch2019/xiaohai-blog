package com.xiaohai.system.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.system.pojo.dto.FeedbackDto;
import com.xiaohai.system.pojo.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaohai.system.pojo.query.FeedbackQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户反馈 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2023-08-03
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {
    /**
     * 查询用户反馈列表数据
     *
     * @param wherePage
     * @return
     */
    IPage<FeedbackDto> findFeedbackListByPage(@Param("page") IPage<FeedbackDto> wherePage, @Param("query") FeedbackQuery query);

}
