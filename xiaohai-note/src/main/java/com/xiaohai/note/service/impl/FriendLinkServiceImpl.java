package com.xiaohai.note.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.utils.RoleUtils;
import com.xiaohai.note.pojo.entity.Article;
import com.xiaohai.note.pojo.entity.FriendLink;
import com.xiaohai.note.dao.FriendLinkMapper;
import com.xiaohai.note.pojo.entity.Tags;
import com.xiaohai.note.pojo.vo.NotificationsVo;
import com.xiaohai.note.service.FriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.daomain.ReturnPageData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaohai.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohai.note.service.NotificationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.xiaohai.note.pojo.query.FriendLinkQuery;
import com.xiaohai.note.pojo.vo.FriendLinkVo;
import com.xiaohai.note.pojo.dto.FriendLinkDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 友情链接表 服务实现类
 *
 * @author xiaohai
 * @since 2023-07-01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    private final NotificationsService notificationsService;

    @Override
    public Integer add(FriendLinkVo vo) {
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        //普通用户不可动参数
        //判断角色是否是管理员
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            vo.setSort(0);
            vo.setReason("");
            vo.setStatus("0");
        }
        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(vo, friendLink);
        friendLink.setUserId(userId);
        Integer count = baseMapper.insert(friendLink);
        // 友链消息推送
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            NotificationsVo notificationsVo = new NotificationsVo();
            notificationsVo.setUserId(1);
            notificationsVo.setType("3");
            notificationsVo.setLinkId(friendLink.getId());
            notificationsVo.setRemark("有新的友链申请加入");
            notificationsService.add(notificationsVo);
        }
        return count;
    }

    @Override
    public Integer delete(Long[] ids) {
        for (Long id : ids) {
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    public Integer updateData(FriendLinkVo vo) {
        //普通用户不可动参数
        //判断角色是否是管理员
        if (!StpUtil.hasRole(Constants.ADMIN)) {
            vo.setSort(null);
            vo.setReason(null);
            vo.setStatus(null);
        }
        FriendLink friend = baseMapper.selectById(vo.getId());
        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(vo, friendLink);
        Integer count = baseMapper.updateById(friendLink);
        //管理员修改状态推送
        if (StpUtil.hasRole(Constants.ADMIN)) {
            if (!friend.getStatus().equals(friendLink.getStatus())&&!friendLink.getStatus().equals("0")) {
                NotificationsVo notificationsVo = new NotificationsVo();
                notificationsVo.setUserId(friend.getUserId());
                notificationsVo.setType("3");
                notificationsVo.setLinkId(friendLink.getId());
                if (friendLink.getStatus().equals("1")) {
                    notificationsVo.setRemark("友链申请已通过");
                }
                if (friendLink.getStatus().equals("2")) {
                    notificationsVo.setRemark("友链申请未通过");
                }
                notificationsService.add(notificationsVo);
            }
        }
        return count;
    }

    @Override
    public FriendLink findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public ReturnPageData<FriendLinkDto> findListByPage(FriendLinkQuery query) {
        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(query, friendLink);
        IPage<FriendLink> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        //不是管理员、demo查询用户自己的
        if (RoleUtils.checkRole()) {
            friendLink.setUserId(Integer.valueOf((String) StpUtil.getLoginId()));
        }
        IPage<FriendLink> iPage = baseMapper.selectPage(wherePage, new LambdaQueryWrapper<FriendLink>()
                .eq(StringUtils.isNotBlank(query.getName()), FriendLink::getName, query.getName())
                .eq(StringUtils.isNotBlank(query.getStatus()), FriendLink::getStatus, query.getStatus()));
        List<FriendLinkDto> list = new ArrayList<>();
        for (FriendLink friendLinks : iPage.getRecords()) {
            FriendLinkDto friendLinkDto = new FriendLinkDto();
            BeanUtils.copyProperties(friendLinks, friendLinkDto);
            list.add(friendLinkDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }

    @Override
    public List<FriendLinkDto> findList() {
        List<FriendLink> list = baseMapper.selectList(new QueryWrapper<FriendLink>().eq("status", 1).orderByDesc("sort"));
        List<FriendLinkDto> dtoList = new ArrayList<>(list.size());
        for (FriendLink friendLink : list) {
            FriendLinkDto friendLinkDto = new FriendLinkDto();
            BeanUtils.copyProperties(friendLink, friendLinkDto);
            dtoList.add(friendLinkDto);
        }
        return dtoList;
    }
}
