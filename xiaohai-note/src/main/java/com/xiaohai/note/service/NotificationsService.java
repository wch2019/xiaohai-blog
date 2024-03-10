package com.xiaohai.note.service;

import com.xiaohai.note.pojo.entity.Notifications;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.note.pojo.query.NotificationsQuery;
import com.xiaohai.note.pojo.vo.NotificationsVo;
import com.xiaohai.note.pojo.dto.NotificationsDto;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 *
 * 系统通知 服务类
 *
 *
 * @author xiaohai
 * @since 2024-03-09
 */
public interface NotificationsService extends IService<Notifications> {

    /**
     * 通过sse连接通知给前端消息
     * @return
     */
    SseEmitter getSseEmitter();

    /**
     * 添加系统通知
     *
     * @param vo 系统通知 VO（View Object）：显示层对象
     * @return Integer
     */
    void add(NotificationsVo vo);

    /**
     * 删除系统通知
     *
     * @param ids 主键
     * @return Integer
     */
    Integer delete(Long[] ids);

    /**
     * 修改系统通知
     *
     * @param  ids 主键
     * @return Integer
     */
    Integer updateData(Long[] ids);

    /**
     * 查询未读系统通知
     *
     * @return   Notifications
     */
    List<NotificationsDto> findList();

    /**
    * 查询系统通知列表数据
    *
    * @param query 系统通知 Query 数据查询对象
    * @return Response
    */
    ReturnPageData<NotificationsDto> findListByPage(NotificationsQuery query);
}
