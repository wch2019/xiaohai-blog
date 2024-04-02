package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.confing.MailSenderConfig;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.EmailUtils;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.note.dao.CommentMapper;
import com.xiaohai.note.dao.FriendLinkMapper;
import com.xiaohai.note.dao.NotificationsMapper;
import com.xiaohai.note.pojo.dto.*;
import com.xiaohai.note.pojo.entity.FriendLink;
import com.xiaohai.note.pojo.entity.Notifications;
import com.xiaohai.note.pojo.query.NotificationsQuery;
import com.xiaohai.note.pojo.vo.NotificationsVo;
import com.xiaohai.note.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统通知 服务实现类
 *
 * @author xiaohai
 * @since 2024-03-09
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notifications> implements NotificationsService {

    private final Map<Integer, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    // 心跳间隔时间（单位：毫秒）
    private static final long HEARTBEAT_INTERVAL = 5000;

    private final CommentMapper commentMapper;

    private final FriendLinkMapper friendLinkMapper;

    private final MailSenderConfig mailSenderConfig;


    /**
     * 定时任务，定期发送心跳消息给客户端
     */
    @Scheduled(fixedRate = HEARTBEAT_INTERVAL)
    public void sendHeartbeat() {
        for (Map.Entry<Integer, SseEmitter> entry : sseEmitterMap.entrySet()) {
            SseEmitter emitter = entry.getValue();
            try {
                emitter.send("heartbeat");
            } catch (Exception e) {
                sseEmitterMap.remove(entry.getKey());
                emitter.complete();
                log.info("用户：{}，SSE连接断开！", entry.getKey());
            }
        }
    }

    @Override
    public SseEmitter getSseEmitter() {
        SseEmitter sseEmitter = new SseEmitter(0L);

        Integer userId = Integer.valueOf(String.valueOf(StpUtil.getLoginId()));
        long count = baseMapper.selectCount(new LambdaQueryWrapper<Notifications>()
                .eq(Notifications::getUserId, userId)
                .eq(Notifications::getIsRead, 0));
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(userId.toString())
                    .data(count)
                    .reconnectTime(3000));
        } catch (IOException e) {
            log.error("获取SSE连接失败！");
            return null;
        }

        // 监听连接完成事件，当客户端断开连接时移除对应的userId
        sseEmitter.onCompletion(() -> {
            sseEmitterMap.remove(userId);
            sseEmitter.complete();
        });

        // 监听出错事件，当发送消息失败时也移除对应的userId
        sseEmitter.onError((error) -> {
            sseEmitterMap.remove(userId);
            sseEmitter.completeWithError(error);
        });
        // 将userId与sseEmitter关联起来
        sseEmitterMap.put(userId, sseEmitter);
        log.info("用户：{}，获取SSE连接成功！", userId);
        return sseEmitter;
    }

    @Override
    @Async("syncExecutorPool")
    public void add(NotificationsVo vo) {
        var userId = vo.getUserId();

        Notifications notifications = new Notifications();
        BeanUtils.copyProperties(vo, notifications);
        notifications.setCreatedTime(LocalDateTime.now());
        baseMapper.insert(notifications);
        sseInform(userId);
        // TODO 邮箱推送
        var emailNoticeStatus = baseMapper.selectEmailNoticeStatus();
        if(emailNoticeStatus==0){
            var email = baseMapper.selectEmailByUserId(userId);
            if (notifications.getType().equals("1")) {
                NotificationsLikeDto likeDto=baseMapper.selectFindLike(notifications.getLikeId());
                var likeEmail=EmailUtils.likeEmail(notifications.getArticleId(),likeDto.getTitle());
                EmailUtils.send(mailSenderConfig.getSender(), email, likeEmail, "点赞通知");
            }
            if (notifications.getType().equals("2")) {
                CommentDto commentDto=commentMapper.findCommentId(notifications.getCommentId());
                var commentEmail=EmailUtils.commentEmail(commentDto.getContent());
                EmailUtils.send(mailSenderConfig.getSender(), email, commentEmail, "评论通知");
            }
            if (notifications.getType().equals("3")) {
                String emailTemplate="";
                // 友链
                if (notifications.getLinkId() != null) {
                    FriendLink friendLink = friendLinkMapper.selectById(notifications.getLinkId());
                    // 已通过
                    if (friendLink.getStatus().equals("1")) {
                        emailTemplate=EmailUtils.friendPass(friendLink.getUrl(),friendLink.getName());
                    }
                    // 未通过
                    if (friendLink.getStatus().equals("2")) {
                        emailTemplate=EmailUtils.friendFailed(friendLink.getUrl(),friendLink.getName(),friendLink.getReason());
                    }
                }
                // 反馈
                if (notifications.getFeedbackId() != null) {
                    NotificationsFeedbackDto feedbackDto =baseMapper.selectFeedbackById(notifications.getFeedbackId());
                }
                EmailUtils.send(mailSenderConfig.getSender(), email, emailTemplate, "系统通知");
            }
        }

    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(Long[] ids) {
        for (Long id : ids) {
            Notifications notifications = new Notifications();
            notifications.setId(Math.toIntExact(id));
            notifications.setIsRead(1);
            baseMapper.updateById(notifications);
        }
        Integer userId = Integer.valueOf(String.valueOf(StpUtil.getLoginId()));
        sseInform(userId);
        return ids.length;
    }

    /**
     * 通知客户端
     *
     * @param userId
     */
    public void sseInform(Integer userId) {
        long count = baseMapper.selectCount(new LambdaQueryWrapper<Notifications>()
                .eq(Notifications::getUserId, userId)
                .eq(Notifications::getIsRead, 0));
        SseEmitter sseEmitter = sseEmitterMap.get(userId);
        try {
            sseEmitter.send(SseEmitter.event()
                    .data(count)
                    .reconnectTime(3000));
        } catch (Exception e) {
            sseEmitterMap.remove(userId);
            if(sseEmitter!=null){
                sseEmitter.complete();
            }
            log.info("用户：{}，SSE连接断开！", userId);
        }
    }

    @Override
    public List<NotificationsDto> findList(NotificationsQuery query) {
        Integer userId = Integer.valueOf(String.valueOf(StpUtil.getLoginId()));
        query.setIsRead(0);
        IPage<NotificationsDto> wherePage = new Page<>(1, 5);
        IPage<NotificationsDto> iPage = baseMapper.selectFindList(wherePage, query, userId);
        List<NotificationsDto> list = iPage.getRecords();
        for (NotificationsDto dto : list) {
            if (dto.getLikeId() != null) {
                dto.setLikeDto(baseMapper.selectFindLike(dto.getLikeId()));
            }
            if (dto.getCommentId() != null) {
                dto.setCommentDto(commentMapper.findCommentId(dto.getCommentId()));
            }
            if (dto.getLinkId() != null) {
                FriendLink friendLink = friendLinkMapper.selectById(dto.getLinkId());
                FriendLinkDto friendLinkDto = new FriendLinkDto();
                BeanUtils.copyProperties(friendLink, friendLinkDto);
                dto.setLinkDto(friendLinkDto);
            }
            if (dto.getFeedbackId() != null) {
                dto.setFeedbackDto(baseMapper.selectFeedbackById(dto.getFeedbackId()));
            }
        }
        return list;
    }

    @Override
    public ReturnPageData<NotificationsDto> findListByPage(NotificationsQuery query) {
        IPage<NotificationsDto> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        Integer userId = Integer.valueOf(String.valueOf(StpUtil.getLoginId()));
        IPage<NotificationsDto> iPage = baseMapper.selectFindList(wherePage, query, userId);
        List<NotificationsDto> list = iPage.getRecords();
        for (NotificationsDto dto : list) {
            if (dto.getLikeId() != null) {
                dto.setLikeDto(baseMapper.selectFindLike(dto.getLikeId()));
            }
            if (dto.getCommentId() != null) {
                dto.setCommentDto(commentMapper.findCommentId(dto.getCommentId()));
            }
            if (dto.getLinkId() != null) {
                FriendLink friendLink = friendLinkMapper.selectById(dto.getLinkId());
                FriendLinkDto friendLinkDto = new FriendLinkDto();
                BeanUtils.copyProperties(friendLink, friendLinkDto);
                dto.setLinkDto(friendLinkDto);
            }
            if (dto.getFeedbackId() != null) {
                dto.setFeedbackDto(baseMapper.selectFeedbackById(dto.getFeedbackId()));
            }
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }
}
