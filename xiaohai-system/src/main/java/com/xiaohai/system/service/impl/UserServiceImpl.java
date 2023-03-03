package com.xiaohai.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.daomain.PageData;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.utils.PageUtils;
import com.xiaohai.system.dao.RoleMapper;
import com.xiaohai.system.dao.UserMapper;
import com.xiaohai.system.pojo.dto.UserDto;
import com.xiaohai.system.pojo.entity.User;
import com.xiaohai.system.pojo.query.UserQuery;
import com.xiaohai.system.pojo.vo.UserVo;
import com.xiaohai.system.service.UserRoleService;
import com.xiaohai.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户表 服务实现类
 *
 * @author xiaohai
 * @since 2023-01-29
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RoleMapper roleMapper;
    private final UserRoleService userRoleService;

    @Override
    public User findByInfo() {
        return (User) StpUtil.getSession().get(Constants.CURRENT_USER);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(UserVo vo) {
        Long countEmail = baseMapper.selectCount(new QueryWrapper<User>().eq("email", vo.getEmail()));
        Assert.isTrue(countEmail == 0, "新增邮箱：" + vo.getEmail() + "失败，邮箱已存在");
        Long countUser = baseMapper.selectCount(new QueryWrapper<User>().eq("username", vo.getUsername()));
        Assert.isTrue(countUser == 0, "新增用户：" + vo.getUsername() + "失败，账号已存在");
        User user = new User();
        BeanUtils.copyProperties(vo, user);
        var count = baseMapper.insert(user);
        //新增角色
        userRoleService.add(vo.getRoleIds(), user.getId());
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(Long[] ids) {
        // 当前操作用户
        Object userId = StpUtil.getLoginId();
        for (Long id : ids) {
            Assert.isTrue(Objects.equals(userId, id), "不可删除当前登录用户");
            //删除角色
            userRoleService.delete(Math.toIntExact(id));
            //删除用户
            baseMapper.deleteById(id);
        }
        return ids.length;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateData(UserVo vo) {
        //清空密码，此处不更新密码
        vo.setPassword(null);
        //清空邮箱，此处不更新邮箱
        vo.setEmail(null);
        // 当前操作用户
        User nowUser = (User) StpUtil.getSession().get(Constants.CURRENT_USER);
        if (nowUser.getUsername().equals(vo.getUsername()) && nowUser.getId().equals(vo.getId())) {
            User user = new User();
            BeanUtils.copyProperties(vo, user);
            //更新角色
            userRoleService.rewriteUserRole(vo.getRoleIds(), user.getId());
            return baseMapper.updateById(user);
        } else {
            Long count = baseMapper.selectCount(new QueryWrapper<User>().eq("username", vo.getUsername()).ne("id", vo.getId()));
            Assert.isTrue(count == 0, "更新用户：" + vo.getUsername() + "失败，账号已存在");
            User user = new User();
            BeanUtils.copyProperties(vo, user);
            //更新角色
            userRoleService.rewriteUserRole(vo.getRoleIds(), user.getId());
            return baseMapper.updateById(user);
        }
    }

    @Override
    public UserDto findById(Long id) {
        User user = baseMapper.selectById(id);
        user.setPassword(null);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setRoleIds(roleMapper.listByRoleIds(id));
        return userDto;
    }

    @Override
    public ReturnPageData<UserDto> findListByPage(UserQuery query) {
        User user = new User();
        BeanUtils.copyProperties(query, user);
        IPage<User> wherePage = new Page<>(PageUtils.getPageNo(), PageUtils.getPageSize());
        IPage<User> iPage = baseMapper.selectPage(wherePage, Wrappers.query(user));
        List<UserDto> list = new ArrayList<>();
        for (User users : iPage.getRecords()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(users, userDto);
            userDto.setRoleIds(roleMapper.listByRoleIds(users.getId()));
            list.add(userDto);
        }
        PageData pageData = new PageData();
        BeanUtils.copyProperties(iPage, pageData);
        return ReturnPageData.fillingData(pageData, list);
    }
}
