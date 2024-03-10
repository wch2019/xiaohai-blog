package com.xiaohai.note.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.note.pojo.dto.NotificationsDto;
import com.xiaohai.note.pojo.entity.Notifications;
import com.xiaohai.note.pojo.query.NotificationsQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统通知 Mapper 接口
 * </p>
 *
 * @author xiaohai
 * @since 2024-03-09
 */
public interface NotificationsMapper extends BaseMapper<Notifications> {

    /**
     * 查询通知消息记录
     * @param wherePage
     * @param query
     * @param userId
     * @return
     */
    IPage<NotificationsDto> selectFindList(@Param("page") IPage<NotificationsDto> wherePage, @Param("param") NotificationsQuery query, @Param("userId") Integer userId);
}
